package com.sjsu.gofcar.respository;

/**
 * @author geethu
 */

import com.sjsu.gofcar.model.vehicle.Vehicle;
import com.sjsu.gofcar.model.vehicle.VehicleStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface VehicleRepository extends MongoRepository<Vehicle, Integer> {
	@Query(value = "{'vehicleStatus' : ?0}")
	List<Vehicle> findAllByVehicleStatus(final VehicleStatus vehicleStatus);

	List<Vehicle> findAllByRentalLocationId(int location);
}
