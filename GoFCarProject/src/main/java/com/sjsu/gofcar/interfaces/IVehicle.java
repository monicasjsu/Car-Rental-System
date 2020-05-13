package com.sjsu.gofcar.interfaces;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.sjsu.gofcar.model.vehicle.Vehicle;
import com.sjsu.gofcar.model.vehicle.VehicleStatus;

@JsonDeserialize(as = Vehicle.class)
public interface IVehicle {

	void setVehicleStatus(VehicleStatus vehicleStatus);

	String toString();
}
