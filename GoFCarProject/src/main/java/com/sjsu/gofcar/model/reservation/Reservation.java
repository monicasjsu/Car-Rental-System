package com.sjsu.gofcar.model.reservation;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.NonNull;

@Data
@Builder(toBuilder=true)
@Document(collection="Reservation")
public class Reservation {
	@Id @NonNull
	private String reservationId;
	private int vehicleId;
	private int durationHours;
	private long timeStamp;
	private long reserveFrom;
	private String userId;
	private ReservationStatus reservationStatus;
	private double estimatedCharges;
	private double finalCharges;
	private double lateFee;
	private String feedback;
}
