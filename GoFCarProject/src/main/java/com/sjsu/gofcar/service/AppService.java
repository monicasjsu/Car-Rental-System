package com.sjsu.gofcar.service;

import com.sjsu.gofcar.model.vehicle.RentalLocation;
import com.sjsu.gofcar.model.vehicle.Vehicle;
import com.sjsu.gofcar.model.vehicle.VehicleType;
import com.sjsu.gofcar.respository.RentalLocationRepository;
import com.sjsu.gofcar.respository.VehicleRepository;
import com.sjsu.gofcar.respository.VehicleTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

/**
 * @author geethu
 */
@Service
public class AppService {

    @Autowired
    MongoOperations mongoOperations;

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    VehicleTypeRepository vehicleTypeRepository;

    @Autowired
    RentalLocationRepository rentalLocationRepository;

    public boolean saveVehicleTypeInDB(VehicleType vehicleType) {
        int id = 10 + new Random().nextInt(900000);
        vehicleType.setVehicleTypeId(id);
        return null != vehicleTypeRepository.save(vehicleType) ? true : false;
    }

    public boolean saveVehicleInDB(Vehicle vehicle) {
        boolean returnValue = true;
        VehicleType type;
        Optional<RentalLocation> location;
        RentalLocation loc;
        int id = 1000 + new Random().nextInt(900000);
        vehicle.setVehicleId(id);

        // Check if vehicleType exists in DB. If not create new entry in DB.
        System.out.println("v type"+ vehicle.getType().getVehicleType());
        if(!vehicleTypeRepository.hasVehicleType(vehicle.getType().getVehicleType())) {
            System.out.println("vehicletype not present");
            saveVehicleTypeInDB(vehicle.getType());
        }
        type = vehicleTypeRepository.getVehicleTypeByTypeName(vehicle.getType().getVehicleType());
        vehicle.setType(type);

        System.out.println("v location"+ vehicle.getRentalLocationId());
        location = rentalLocationRepository.findById(vehicle.getRentalLocationId());
        if(location.isPresent()) {
            loc = location.get();
            if (null == loc.getVehicles()) {
                System.out.println("creating arraylist");
                loc.setVehicles(new ArrayList<>());
                loc.getVehicles().add(vehicle.getVehicleId());
            }
            else
                loc.getVehicles().add(vehicle.getVehicleId());
            updateVehiclesListInRentalLocation(loc);
            return null != vehicleRepository.save(vehicle) ? true : false;
        } else {
            returnValue = false;
        }
        return returnValue;
    }

    private void updateVehiclesListInRentalLocation(RentalLocation rentalLocation){
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(rentalLocation.getRentalLocationId()));
        RentalLocation r = (RentalLocation) mongoOperations.findOne(query, RentalLocation.class);
        Update update = new Update();
        System.out.println(rentalLocation.getVehicles());
        update.set("vehicles", rentalLocation.getVehicles());
        mongoOperations.findAndModify(query, update, RentalLocation.class);
    }

    public boolean updateRentalLocation(RentalLocation rentalLocation) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(rentalLocation.getRentalLocationId()));
        RentalLocation r = (RentalLocation) mongoOperations.findOne(query, RentalLocation.class);
        System.out.println("output "+ r.toString());
        Update update = new Update();
        System.out.println(rentalLocation.getVehicleCapacity());
        if(rentalLocation.getLocationName() != null)
            update.set("locationName", rentalLocation.getLocationName());
        if(rentalLocation.getVehicleCapacity() != 0 )
            update.set("vehicleCapacity", rentalLocation.getVehicleCapacity());
        if(null != rentalLocation.getAddress())
            update.set("address", rentalLocation.getAddress());
        if(null != rentalLocation.getVehicles() && rentalLocation.getVehicles().size() > 0)
            update.set("vehicles", rentalLocation.getVehicles());
        return null !=  mongoOperations.findAndModify(query, update, RentalLocation.class);
    }


    public boolean updateVehicle(Vehicle vehicle){
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(vehicle.getVehicleId()));
        Update update = new Update();
        //  update.set("vehicle", vehicle);
        if(null != vehicle.getRegistrationTag())
            update.set("registrationTag", vehicle.getRegistrationTag());
        if(vehicle.getCurrentMileage() != 0)
            update.set("currentMileage", vehicle.getCurrentMileage());
        if(null != vehicle.getVehicleCondition())
            update.set("vehicleCondition", vehicle.getVehicleCondition());
        if(null != vehicle.getVehicleStatus())
            update.set("vehicleStatus", vehicle.getVehicleStatus());
        if(vehicle.getRentalLocationId() != 0)
            update.set("rentalLocationId", vehicle.getRentalLocationId());
        if(vehicle.getType() != null){
            VehicleType vehicleType = vehicleTypeRepository.getVehicleTypeByTypeName(vehicle.getType().getVehicleType());
            if (vehicleType != null) {
                update.set("type", vehicleType);
            }
        }
        if(vehicle.getMake() != null)
            update.set("make", vehicle.getMake());
        if(vehicle.getModel() != null)
            update.set("model", vehicle.getModel());
        if(vehicle.getYear() != 0)
            update.set("year", vehicle.getYear());

        return null != mongoOperations.findAndModify(query, update, Vehicle.class);
    }

    public boolean updateVehicleType(VehicleType vehicleType) {
        Query query = new Query();
        System.out.println(vehicleType);
        query.addCriteria(Criteria.where("_id").is(vehicleType.getVehicleTypeId()));
        VehicleType v = (VehicleType) mongoOperations.findOne(query, VehicleType.class);
        System.out.println("output "+ v);
        Update update = new Update();
        if(null != vehicleType.getVehicleType())
            update.set("vehicleType", vehicleType.getVehicleType());
        if(vehicleType.getHourlyPrice() != 0)
            update.set("hourlyPrice", vehicleType.getHourlyPrice());
        if(vehicleType.getHourly6To12HoursPrice() != null)
            update.set("hourly6To12HoursPrice", vehicleType.getHourly6To12HoursPrice());
        if(vehicleType.getHourlyMoreThan12HoursPrice() != null)
            update.set("hourlyMoreThan12HoursPrice", vehicleType.getHourlyMoreThan12HoursPrice());
        if(vehicleType.getLateReturnFee() != 0)
            update.set("lateReturnFee", vehicleType.getLateReturnFee());
        return null != mongoOperations.findAndModify(query, update, VehicleType.class);
    }
}
