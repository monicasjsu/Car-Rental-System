package com.sjsu.gofcar.interfaces;

import com.sjsu.gofcar.model.reservation.Reservation;
import com.sjsu.gofcar.model.reservation.ReservationRequest;
import com.sjsu.gofcar.model.reservation.ReservationStatus;
import com.sjsu.gofcar.model.user.UserDetails;
import com.sjsu.gofcar.model.vehicle.RentalLocation;
import com.sjsu.gofcar.model.vehicle.Vehicle;
import com.sjsu.gofcar.model.vehicle.VehicleFeedback;
import com.sjsu.gofcar.model.vehicle.VehicleType;
import io.jsonwebtoken.Claims;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

public interface IUserRepository {
  /* Vehicle APIs*/
  List<VehicleType> getVehicleType();
  List<RentalLocation> getRentalLocation();
  List<Vehicle> getVehicles(Integer location, Integer vehicleTypeId);
  Reservation returnVehicle(String id, VehicleFeedback feedback);

  /* User APIs*/
  UserDetails getUserDetails(Claims claims);
  UserDetails modify(Claims claims, UserDetails requestUserDetails);
  boolean deactivate(Claims claims);

  /* Reservation APIs */
  List<List<String>> getVehicleAvailabilityByDateTime(int vehicleId);
  List<List<Long>> getVehicleAvailabilityByEpoch(int vehicleId);
  List<List<Long>> getVehicleActiveReservationsByEpoch(int vehicleId);
  List<Reservation> getUserReservations(Claims claims, ReservationStatus status);
  Reservation reserveVehicle(Claims claims, ReservationRequest reservationRequest);
  Reservation cancelReservation(String id);

}
