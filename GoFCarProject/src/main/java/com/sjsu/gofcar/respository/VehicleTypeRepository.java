package com.sjsu.gofcar.respository;

/**
 * @author geethu
 */

import com.sjsu.gofcar.model.vehicle.VehicleType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface VehicleTypeRepository extends MongoRepository<VehicleType, Integer> {

    @Query("{'vehicleType' : ?0}")
    public VehicleType getVehicleTypeByTypeName(String vehicleType);

    @Query(value = "{'vehicleType' : ?0}", exists = true)
    public boolean hasVehicleType(String vehicleType);
}
