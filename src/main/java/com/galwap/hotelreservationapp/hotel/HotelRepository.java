package com.galwap.hotelreservationapp.hotel;

import com.galwap.hotelreservationapp.hotel.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HotelRepository extends JpaRepository<Hotel, Integer> {

    Optional<Hotel> findAllByNameAndCityAndStreetAndStreetNoAndPostCode(String name, String city, String street,
                                                                        String streetNo, String postCode);
}
