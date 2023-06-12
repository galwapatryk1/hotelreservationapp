package com.galwap.hotelreservationapp.hotelroom;

import com.galwap.hotelreservationapp.hotelroom.HotelRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRoomRepository extends JpaRepository<HotelRoom, Integer> {
}
