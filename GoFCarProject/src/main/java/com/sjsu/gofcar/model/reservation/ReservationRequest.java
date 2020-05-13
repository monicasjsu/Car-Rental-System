package com.sjsu.gofcar.model.reservation;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data @Builder(toBuilder = true)
@ToString
public class ReservationRequest {
	private int vehicleId;
	private long fromTime;
	private int durationHours;
}
