package com.sjsu.gofcar.controller;

/**
 * @author geethu
 */

import com.sjsu.gofcar.interfaces.IRentalCarService;
import com.sjsu.gofcar.model.user.UserDetails;
import com.sjsu.gofcar.model.vehicle.RentalLocation;
import com.sjsu.gofcar.model.vehicle.Vehicle;
import com.sjsu.gofcar.model.vehicle.VehicleType;
import com.sjsu.gofcar.respository.RentalLocationRepository;
import com.sjsu.gofcar.respository.UserDetailsRepository;
import com.sjsu.gofcar.respository.VehicleRepository;
import com.sjsu.gofcar.respository.VehicleTypeRepository;
import com.sjsu.gofcar.service.AppService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin(origins = "*")
public class AppController implements IRentalCarService {

    @Autowired
    VehicleTypeRepository vehicleTypeRepository;

    @Autowired
    RentalLocationRepository rentalLocationRepository;

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    UserDetailsRepository userDetailsRepository;

    @Autowired
    AppService appService;

    @GetMapping("/hello")
    public String helloWorld(){
        System.out.println("Inside hello");
        return "Hello World";
    }

    @GetMapping("/admin/getAllUsers")
    public @ResponseBody List<UserDetails> getUserDetails(@RequestParam(required = false) Boolean isActive) {
        if (isActive == null) {
            return userDetailsRepository.findAll().stream()
                .peek(userDetails -> userDetails.setPassword(null))
                .collect(Collectors.toList());
        }
        return userDetailsRepository.findAllByIsActive(isActive).stream()
            .peek(userDetails -> userDetails.setPassword(null))
            .collect(Collectors.toList());
    }

    @Override
    @GetMapping("/admin/approve")
    public @ResponseBody boolean approveUser(@RequestParam String email) {
        Optional<UserDetails> userDetailsOptional = userDetailsRepository.findById(email);
        if (!userDetailsOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        UserDetails userDetails = userDetailsOptional.get();
        userDetails.setIsActive(true);
        DateTime validity = DateTime.now().plusMonths(6);
        userDetails.setValidity(validity.getMillis());
        Long membershipFee = userDetails.getMembershipFee();
        if (membershipFee == null) {
            membershipFee = 0L;
        }
        userDetails.setMembershipFee(membershipFee + 60); // 60 per 6 months
        return userDetailsRepository.save(userDetails) != null;
    }

    @GetMapping("/admin/terminateUser")
    @Override
    public @ResponseBody boolean terminateUser(@RequestParam String email) {
        Optional<UserDetails> userDetailsOpt = userDetailsRepository.findById(email);
        if (!userDetailsOpt.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        UserDetails userDetails = userDetailsOpt.get();
        userDetails.setIsActive(false);
        userDetailsRepository.save(userDetails);
        return true;
    }

    @GetMapping("/user/getAllVehicleType")
    public @ResponseBody List<VehicleType> getAllVehicleType() {
        System.out.println("Getting All VehicleType");
        return vehicleTypeRepository.findAll();
    }

    @Override
    @PostMapping("/admin/saveVehicleType")
    public @ResponseBody boolean saveVehicleType (@RequestBody VehicleType vehicleType) {
        System.out.println("Saved Vehicle Type");
        return appService.saveVehicleTypeInDB(vehicleType);
    }

    @PutMapping("/admin/editVehicleType")
    public @ResponseBody boolean editVehicleType(@RequestBody VehicleType vehicleType){
        return appService.updateVehicleType(vehicleType);
    }

    @Override
    @DeleteMapping("/admin/deleteVehicleType")
    public void deleteVehicleType(@RequestParam int vehicleTypeId) {
        vehicleTypeRepository.deleteById(vehicleTypeId);
    }

    @Override
    public @ResponseBody VehicleType getVehicleTypeById(String id) {
        return null;
    }

    @Override
    @GetMapping("/user/getAllRentalLocation")
    public @ResponseBody List<RentalLocation> getAllRentalLocation() {
        return rentalLocationRepository.findAll();
    }

    @Override
    @PostMapping("/admin/saveRentalLocation")
    public @ResponseBody boolean saveRentalLocation(@RequestBody RentalLocation rentalLocation) {
        int id = 100 + new Random().nextInt(90000);
        rentalLocation.setRentalLocationId(id);
        return null != rentalLocationRepository.save(rentalLocation) ? true : false;
    }

    @Override
    @PutMapping("/admin/editRentalLocation")
    public @ResponseBody boolean editRentalLocation(@RequestBody RentalLocation rentalLocation){
        return appService.updateRentalLocation(rentalLocation);
    }

    @Override
    @DeleteMapping("/admin/deleteRentalLocation")
    public @ResponseBody void deleteRentalLocationId(@RequestParam int rentalLocationId){
        rentalLocationRepository.deleteById(rentalLocationId);
    }

    @Override
    @PostMapping("/admin/saveVehicle")
    public @ResponseBody boolean saveVehicle(@RequestBody Vehicle vehicle) {
        return appService.saveVehicleInDB(vehicle);
    }

    @Override
    @GetMapping("/user/getAllVehicle")
    public @ResponseBody List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    @Override
    @PutMapping("/admin/editVehicle")
    public @ResponseBody boolean editVehicle(@RequestBody Vehicle vehicle){
        return appService.updateVehicle(vehicle);
    }

    @Override
    @DeleteMapping("/admin/deleteVehicle")
    public @ResponseBody void deleteVehicleById(@RequestParam int vehicleId){
        vehicleRepository.deleteById(vehicleId);
    }
}
