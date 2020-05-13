package com.sjsu.gofcar.interfaces;


import com.sjsu.gofcar.model.vehicle.RentalLocation;
import com.sjsu.gofcar.model.vehicle.Vehicle;
import com.sjsu.gofcar.model.vehicle.VehicleType;
import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;


public interface IRentalCarService {

	 boolean saveVehicleType(VehicleType vehicleType);

	 List<VehicleType> getAllVehicleType();

	 VehicleType getVehicleTypeById(String id);

	 boolean editVehicleType(VehicleType vehicleType);

	 boolean saveRentalLocation(RentalLocation rentalLocation);

	 void deleteVehicleType(int vehicleTypeId);

	 List<RentalLocation> getAllRentalLocation();

	 boolean editRentalLocation(RentalLocation rentalLocation);

	 void deleteRentalLocationId(int rentalLocationId);

	 boolean saveVehicle(Vehicle vehicle);

	 List<Vehicle> getAllVehicles();

	 boolean editVehicle(Vehicle vehicle);

	boolean approveUser(String email);

	boolean terminateUser(String email);

	void deleteVehicleById(int vehicleId);

}
