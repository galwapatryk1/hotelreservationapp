package com.galwap.hotelreservationapp.reservation;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReservationDto {

    private Integer id;
    private Integer roomId;
    private Integer hotelId;
    private String userId;
    private LocalDate fromDate;
    private LocalDate toDate;

    public static ReservationDto from(Reservation reservation) {
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setFromDate(reservation.getFromDate());
        reservationDto.setToDate(reservation.getToDate());
        reservationDto.setId(reservation.getId());
        reservationDto.setRoomId(reservation.getRoom().getId());
        reservationDto.setHotelId(reservation.getHotel().getId());
        reservationDto.setUserId(reservation.getUserId());
        return  reservationDto;
    }
}
