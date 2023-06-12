package com.galwap.hotelreservationapp.hotelroom;

import com.galwap.hotelreservationapp.hotel.Hotel;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table
public class HotelRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private Hotel hotel;
    private String description;
    private Float price;

    public static HotelRoom from(HotelRoomDto hotelRoomDto) {
        HotelRoom hotelRoom = new HotelRoom();
        hotelRoom.setId(hotelRoomDto.getId());
        hotelRoom.setDescription(hotelRoomDto.getDescription());
        hotelRoom.setPrice(hotelRoomDto.getPrice());
        Hotel hotel = new Hotel();
        hotel.setId(hotelRoomDto.getHotelId());
        hotelRoom.setHotel(hotel);
        return hotelRoom;
    }
}