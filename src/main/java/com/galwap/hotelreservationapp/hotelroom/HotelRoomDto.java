package com.galwap.hotelreservationapp.hotelroom;

import lombok.Data;

@Data
public class HotelRoomDto {

    private Integer id;
    private Integer hotelId;
    private String description;
    private Float price;

    public static HotelRoomDto from(HotelRoom hotelRoom) {
        HotelRoomDto hotelRoomDto = new HotelRoomDto();
        hotelRoomDto.setId(hotelRoom.getId());
        hotelRoomDto.setDescription(hotelRoom.getDescription());
        hotelRoomDto.setPrice(hotelRoom.getPrice());
        hotelRoomDto.setHotelId(hotelRoom.getHotel().getId());
        return hotelRoomDto;
    }
}
