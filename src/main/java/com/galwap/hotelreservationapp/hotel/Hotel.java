package com.galwap.hotelreservationapp.hotel;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    private String city;
    private String street;
    private String streetNo;
    private String postCode;

    public static Hotel from(HotelDto hotelDto) {
        Hotel hotel = new Hotel();
        hotel.setId(hotelDto.getId());
        hotel.setCity(hotelDto.getCity());
        hotel.setName(hotelDto.getName());
        hotel.setStreet(hotelDto.getStreet());
        hotel.setDescription(hotelDto.getDescription());
        hotel.setPostCode(hotelDto.getPostCode());
        hotel.setStreetNo(hotelDto.getStreetNo());
        return hotel;
    }
}
