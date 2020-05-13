package com.sjsu.gofcar.respository;

/**
 * @author geethu
 */

import com.sjsu.gofcar.model.vehicle.RentalLocation;
import com.sjsu.gofcar.model.vehicle.Vehicle;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface RentalLocationRepository extends MongoRepository<RentalLocation, Integer> {

    @Query("{'locationName' : ?0}")
    public RentalLocation getRentalLocationByName(String locationName);

    @Query("{'zipCode' : ?0}")
    public RentalLocation getRentalLocationByZipCode(int zipCode);

    @Query(value = "{'locationName' : ?0}", exists = true)
    public boolean hasRentalLocation(String locationName);

	@Query(value = "{'locationName' : ?0}")
	List<Vehicle> findVehicleForRentalLocation(String location);
}
