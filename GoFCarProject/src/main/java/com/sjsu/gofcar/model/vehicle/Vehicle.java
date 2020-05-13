package com.sjsu.gofcar.model.vehicle;

import com.sjsu.gofcar.interfaces.IVehicle;
import java.util.Date;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data @ToString @Builder(toBuilder = true)
@Document(collection = "vehicle")
public class Vehicle implements IVehicle {
    @Id
    private int vehicleId;
    @DBRef
    private VehicleType type;
    private String make;
    private String model;
    private int year;
    private String registrationTag;
    private double currentMileage;
    private String vehicleCondition;
    private VehicleStatus vehicleStatus;
    private int rentalLocationId;
    private Date lastService;
    private String rentalLocationName;
}
