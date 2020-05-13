package com.sjsu.gofcar.model.vehicle;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

@Data @ToString
@Builder(toBuilder = true)
public class VehicleFeedback {
  String feedback;
  String vehicleCondition;
}
