package com.sjsu.gofcar.model.vehicle;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.Nullable;

@Data @ToString @Builder(toBuilder = true)
@Document(collection = "vehicletype")
public class VehicleType {

    @Id
    private int vehicleTypeId;
    private String vehicleType;
    private double hourlyPrice;
    private Double hourly6To12HoursPrice;
    private Double hourlyMoreThan12HoursPrice;
    private double lateReturnFee;
}
