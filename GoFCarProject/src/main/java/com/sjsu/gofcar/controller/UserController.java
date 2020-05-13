package com.sjsu.gofcar.controller;

import com.sjsu.gofcar.interfaces.IUserRepository;
import com.sjsu.gofcar.model.reservation.Reservation;
import com.sjsu.gofcar.model.reservation.ReservationRequest;
import com.sjsu.gofcar.model.reservation.ReservationStatus;
import com.sjsu.gofcar.model.user.UserDetails;
import com.sjsu.gofcar.model.vehicle.RentalLocation;
import com.sjsu.gofcar.model.vehicle.Vehicle;
import com.sjsu.gofcar.model.vehicle.VehicleFeedback;
import com.sjsu.gofcar.model.vehicle.VehicleType;
import com.sjsu.gofcar.respository.RentalLocationRepository;
import com.sjsu.gofcar.respository.ReservationRepository;
import com.sjsu.gofcar.respository.UserDetailsRepository;
import com.sjsu.gofcar.respository.VehicleRepository;
import com.sjsu.gofcar.respository.VehicleTypeRepository;
import io.jsonwebtoken.Claims;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/user")
@CrossOrigin(origins = "*")
public class UserController implements IUserRepository {
    Logger logger = Logger.getLogger("UserController");
    long oneHourInMs = 1000 * 60 * 60;

    @Autowired
    UserDetailsRepository userDetailsRepository;

    @Autowired
    VehicleTypeRepository vehicleTypeRepository;

    @Autowired
    RentalLocationRepository rentalLocationRepository;

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /* Vehicle APIs*/

    @GetMapping("/getVehicleTypes")
    @Override
    public @ResponseBody List<VehicleType> getVehicleType() {
        return vehicleTypeRepository.findAll();
    }

    @GetMapping("/getRentalLocations")
    @Override
    public @ResponseBody List<RentalLocation> getRentalLocation() {
        return rentalLocationRepository.findAll();
    }

    @GetMapping("/getVehicles")
    public @ResponseBody List<Vehicle> getVehicles(@RequestParam(required = false) Integer location,
        @RequestParam(required = false) Integer vehicleType)  {
        if (location != null && vehicleType != null) {
            return vehicleRepository.findAll().stream()
                .filter(vehicle -> vehicle.getType().getVehicleTypeId() == vehicleType)
                .filter(vehicle -> vehicle.getRentalLocationId() == location)
                .collect(Collectors.toList());
        } else if (location != null) {
            return vehicleRepository.findAllByRentalLocationId(location);
        } else if (vehicleType != null) {
            return vehicleRepository.findAll().stream()
                .filter(vehicle -> vehicle.getType().getVehicleTypeId() == vehicleType)
                .collect(Collectors.toList());
        } else {
            return vehicleRepository.findAll();
        }
    }

    @PostMapping("/returnVehicle")
    @Override
    public @ResponseBody Reservation returnVehicle(@RequestParam String reservationId, @RequestBody(required = false)
        VehicleFeedback feedback) {
        Optional<Reservation> reservationOpt = reservationRepository.findById(reservationId);
        if(!reservationOpt.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        Reservation reservation = reservationOpt.get();
        ReservationStatus status = reservation.getReservationStatus();
        if(status == ReservationStatus.RESERVED) {
            long reservationStartTime = reservation.getReserveFrom();
            long reservationDuration = reservation.getDurationHours();

            Optional<Vehicle> vehicleOpt = vehicleRepository.findById(reservation.getVehicleId());
            if (!vehicleOpt.isPresent()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle not found");
            }
            Vehicle vehicle = vehicleOpt.get();
            VehicleType vehicleType = vehicle.getType();
            double hourlyPrice = vehicleType.getHourlyPrice();
            DateTime reservationEndTime = new DateTime(reservationStartTime).plusHours((int)reservationDuration);

            long currentTime = DateTime.now().getMillis();
            long extraTime = currentTime - reservationEndTime.getMillis();
            double lateFeeCharge = 0.0;
            if(extraTime > 0) {
                double extraHours = Math.ceil(extraTime/(1000 * 60.0 * 60.0));
                double lateFeePerHour = vehicleType.getLateReturnFee();
                lateFeeCharge = extraHours * lateFeePerHour;
            }
            double totalFee = reservationDuration * hourlyPrice;
            reservation.setFinalCharges(totalFee + lateFeeCharge);
            reservation.setLateFee(lateFeeCharge);
            reservation.setReservationStatus(ReservationStatus.COMPLETED);
            reservation.setFeedback(feedback.getFeedback());
            vehicle.setVehicleCondition(feedback.getVehicleCondition());
            vehicleRepository.save(vehicle);
            return reservationRepository.save(reservation);
        } else {
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid status present for reservation");
        }
    }

    /* User APIs*/
    @Override
    @GetMapping("/getUser")
    public @ResponseBody UserDetails getUserDetails(@RequestAttribute("claims") Claims claims) {
        return userDetailsRepository.findById(claims.getSubject()).get();
    }

    @PostMapping("/modify")
    @Override
    public @ResponseBody UserDetails modify(@RequestAttribute("claims") Claims claims,
        @RequestBody UserDetails requestUserDetails) {
        String email = claims.getSubject();
        if (requestUserDetails == null || email == null) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Invalid Request");
        }
        Optional<UserDetails> userDetailsOpt = userDetailsRepository.findById(email);
        if (!userDetailsOpt.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        UserDetails userDetails = userDetailsOpt.get();
        if (requestUserDetails.getCard() != null) {
            userDetails.setCard(requestUserDetails.getCard());
        }
        if (requestUserDetails.getLicense() != null) {
            userDetails.setLicense(requestUserDetails.getLicense());
        }
        if (requestUserDetails.getAddress() != null) {
            userDetails.setAddress(requestUserDetails.getAddress());
        }
        if (!StringUtils.isEmpty(requestUserDetails.getPassword())) {
            userDetails.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        }

        return userDetailsRepository.save(userDetails);
    }

    @GetMapping("/deactivate")
    @Override
    public @ResponseBody boolean deactivate(@RequestAttribute("claims") Claims claims) {
        String email = claims.getSubject();
        Optional<UserDetails> userDetailsOpt = userDetailsRepository.findById(email);
        if (!userDetailsOpt.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        UserDetails userDetails = userDetailsOpt.get();
        userDetails.setIsActive(false);
        userDetailsRepository.save(userDetails);
        return true;
    }

    /* Reservation APIs */

    @GetMapping("/checkAvailability")
    @Override
    public @ResponseBody List<List<Long>> getVehicleAvailabilityByEpoch(@RequestParam("vehicleId") int vehicleId) {
        List<Reservation> reservations = reservationRepository.findAllByReservationStatusAndVehicleId(
            ReservationStatus.RESERVED, vehicleId);
        List<List<Long>> retVal = new LinkedList<>();
        long startTimeStamp = DateTime.now().getMillis() + 1_000 * 60 * 15; //15 min from now
        long endOff7DaysPeriod = startTimeStamp + oneHourInMs * 24 * 7; // 7 days from now
        if (reservations != null && reservations.size() > 0) {
            long currentStart = reservations.get(0).getReserveFrom();
            long currentEnd = currentStart + reservations.get(0).getDurationHours() * oneHourInMs;
            for (int i = 1; i < reservations.size(); i++) {
                if (currentEnd + oneHourInMs < reservations.get(i).getReserveFrom()) {
                    retVal.add(Arrays.asList(currentEnd + 1, reservations.get(i).getReserveFrom() - 1));
                }
                currentEnd = reservations.get(i).getReserveFrom() +
                    reservations.get(i).getDurationHours() * oneHourInMs;
            }

            if (startTimeStamp + oneHourInMs < currentStart) {
                retVal.add(0, Arrays.asList(startTimeStamp, currentStart - 1));
            }
            // 7Day period;
            if (currentEnd + oneHourInMs <  endOff7DaysPeriod) {
                retVal.add(Arrays.asList(Math.max(currentEnd + 1, startTimeStamp), endOff7DaysPeriod));
            }
        } else {
            retVal.add(Arrays.asList(startTimeStamp, endOff7DaysPeriod));
        }
        return retVal;
    }

    @GetMapping("/getReservedSlots")
    @Override
    public List<List<Long>> getVehicleActiveReservationsByEpoch(@RequestParam("vehicleId") int vehicleId) {
        List<Reservation> reservations = reservationRepository.findAllByReservationStatusAndVehicleId(
                ReservationStatus.RESERVED, vehicleId);
        List<List<Long>> retVal = new LinkedList<>();
        if (reservations != null && reservations.size() > 0) {
            for (Reservation reservation : reservations) {
                retVal.add(Arrays.asList(reservation.getReserveFrom(),
                        reservation.getReserveFrom() + reservation.getDurationHours() * oneHourInMs));
            }
        }
        return retVal;
    }

    @GetMapping("/checkAvailabilityDateTime")
    @Override
    public @ResponseBody List<List<String>> getVehicleAvailabilityByDateTime(@RequestParam("vehicleId") int vehicleId) {
        List<List<Long>> intervals = getVehicleActiveReservationsByEpoch(vehicleId);
        return intervals.stream().map(interval -> {
            List<String> dateTimes = interval.stream().map(this::getDateTime).collect(Collectors.toList());
            return dateTimes;
        }).collect(Collectors.toList());
    }

    private final String getDateTime(long epoch) {
        return new DateTime(epoch).toString();
    }

    @GetMapping("/getReservations")
    @Override
    public @ResponseBody List<Reservation> getUserReservations(@RequestAttribute("claims") Claims claims,
        @RequestParam(required = false) ReservationStatus status) {
        if (status == null) {
            return reservationRepository.findAllByUserId(claims.getSubject());
        } else {
            return reservationRepository.findAllByUserIdAndReservationStatus(claims.getSubject(), status);
        }
    }

    protected boolean hasOverlap(List<List<Long>> intervals, List<Long> interval) {
        if (intervals == null || interval.size() == 0) {
            return true;
        }
        Collections.sort(intervals, Comparator.comparingLong(a -> a.get(0)));
        int index = 0;
        while (index < intervals.size() && intervals.get(index).get(1) < interval.get(0)) {
            index++;
        }
        if (index == intervals.size()) {
            return false;
        }
        if (interval.get(1) < intervals.get(index).get(0)) {
            return false;
        }
        return true;
    }

    @PostMapping("/reserveVehicle")
    @Override
    public @ResponseBody Reservation reserveVehicle(@RequestAttribute("claims") Claims claims,
        @RequestBody ReservationRequest reservationRequest) {
        if (reservationRequest.getFromTime() < DateTime.now().getMillis()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid reservation time");
        }

        Vehicle currVehicle = vehicleRepository.findById(reservationRequest.getVehicleId()).get();
        List<Reservation> reservations = reservationRepository.findAllByReservationStatusAndVehicleId(ReservationStatus.RESERVED, currVehicle.getVehicleId());
        List<List<Long>> intervals = reservations.stream().map(reservation -> {
            List<Long> interval = new ArrayList<>();
            interval.add(reservation.getReserveFrom());
            interval.add(reservation.getReserveFrom() + reservation.getDurationHours() * oneHourInMs);
            return interval;
        }).collect(Collectors.toList());
        List<Long> requestInterval = new ArrayList<>();
        requestInterval.add(reservationRequest.getFromTime());
        requestInterval.add(reservationRequest.getFromTime() + reservationRequest.getDurationHours() * oneHourInMs);
        if (hasOverlap(intervals, requestInterval)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Requested reservation time not available");
        }

        VehicleType vehicleType = currVehicle.getType();
        double estimatedCharges = 0.0;
        if (reservationRequest.getDurationHours() <= 6) {
            estimatedCharges = vehicleType.getHourlyPrice() * reservationRequest.getDurationHours();
        } else if (reservationRequest.getDurationHours() <= 12) {
            estimatedCharges = vehicleType.getHourly6To12HoursPrice() * reservationRequest.getDurationHours();
        } else {
            estimatedCharges = vehicleType.getHourlyMoreThan12HoursPrice() * reservationRequest.getDurationHours();
        }

        final Reservation reservation = Reservation.builder()
            .reservationId(UUID.randomUUID().toString())
            .userId(claims.getSubject())
            .reservationStatus(ReservationStatus.RESERVED)
            .timeStamp(DateTime.now().getMillis())
            .reserveFrom(reservationRequest.getFromTime())
            .durationHours(reservationRequest.getDurationHours())
            .vehicleId(reservationRequest.getVehicleId())
            .estimatedCharges(estimatedCharges)
            .build();
        return reservationRepository.save(reservation);
    }

    @GetMapping("/cancelReservation")
    @Override
    public @ResponseBody Reservation cancelReservation(@RequestParam String id) {
        Optional<Reservation> reservationOpt = reservationRepository.findById(id);
        if(!reservationOpt.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation not found");
        }
        Reservation reservation = reservationOpt.get();
        ReservationStatus status = reservation.getReservationStatus();

        if(status == ReservationStatus.RESERVED) {
            long reservationTime = reservation.getReserveFrom();
            long currentTime = DateTime.now().getMillis();
            DateTime lateTime = new DateTime(reservationTime).minusHours(1);
            if(currentTime > lateTime.getMillis()) {
                Optional<Vehicle> vehicleOpt = vehicleRepository.findById(reservation.getVehicleId());
                if (!vehicleOpt.isPresent()) {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle not found");
                }
                Vehicle vehicle = vehicleOpt.get();
                VehicleType vehicleType = vehicle.getType();
                double hourlyPrice = vehicleType.getHourlyPrice();
                reservation.setFinalCharges(hourlyPrice);
            }
            reservation.setReservationStatus(ReservationStatus.CANCELLED);
            return reservationRepository.save(reservation);
        } else {
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid status present for reservation");
        }
    }
}
