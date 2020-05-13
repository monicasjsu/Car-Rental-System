package com.sjsu.gofcar.model.vehicle;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sjsu.gofcar.interfaces.IVehicle;
import com.sjsu.gofcar.model.Address;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data @ToString
@Document(collection = "rental_location")
public class RentalLocation {

    @Id
    private int rentalLocationId;
    private String locationName;
    private Address address;
    private int vehicleCapacity;
    private List<Integer> vehicles;
}
