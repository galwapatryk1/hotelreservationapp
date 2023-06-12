package com.galwap.hotelreservationapp.reservation;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    Optional<Reservation> findByRoomIdAndHotelIdAndFromDateAfterAndToDateBefore(Integer roomId, Integer hotelId, LocalDate from, LocalDate to);
}
