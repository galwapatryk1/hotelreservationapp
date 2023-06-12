package com.galwap.hotelreservationapp.hotel;

import lombok.Data;

@Data
public class HotelDto {

    private Integer id;
    private String name;
    private String description;
    private String city;
    private String street;
    private String streetNo;
    private String postCode;

    public static HotelDto from(Hotel hotel) {
        HotelDto hotelDto = new HotelDto();
        hotelDto.setId(hotel.getId());
        hotelDto.setCity(hotel.getCity());
        hotelDto.setName(hotel.getName());
        hotelDto.setStreet(hotel.getStreet());
        hotelDto.setDescription(hotel.getDescription());
        hotelDto.setPostCode(hotel.getPostCode());
        hotelDto.setStreetNo(hotel.getStreetNo());
        return hotelDto;
    }
}
