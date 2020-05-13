package com.sjsu.gofcar.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

@Data @ToString
@Builder(toBuilder=true)
@Document(collection = "address")
public class Address {
    private String streetName;
    private String city;
    private String state;
    private int zipCode;

}
