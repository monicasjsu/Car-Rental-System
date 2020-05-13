package com.sjsu.gofcar.respository;

import com.sjsu.gofcar.model.reservation.Reservation;
import com.sjsu.gofcar.model.reservation.ReservationStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReservationRepository extends MongoRepository<Reservation, String> {
	List<Reservation> findAllByReservationStatusAndVehicleId(ReservationStatus reservationStatus, int vehicleId);

	List<Reservation> findAllByUserId(String email);

	List<Reservation> findAllByUserIdAndReservationStatus(String email, ReservationStatus status);
}
