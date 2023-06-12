package com.galwap.hotelreservationapp.reservation;

import com.galwap.hotelreservationapp.hotel.Hotel;
import com.galwap.hotelreservationapp.hotelroom.HotelRoom;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private HotelRoom room;
    @ManyToOne
    private Hotel hotel;
    private String userId;
    private LocalDate fromDate;
    private LocalDate toDate;

    public static Reservation from(ReservationDto reservationDto) {
        Reservation reservation = new Reservation();
        reservation.setId(reservationDto.getId());
        reservation.setFromDate(reservationDto.getFromDate());
        reservation.setToDate(reservation.getToDate());
        reservation.setUserId(reservation.getUserId());
        Hotel hotel = new Hotel();
        hotel.setId(reservationDto.getHotelId());
        reservation.setHotel(hotel);
        HotelRoom hotelRoom = new HotelRoom();
        hotel.setId(reservationDto.getRoomId());
        reservation.setRoom(hotelRoom);
        return reservation;
    }
}


