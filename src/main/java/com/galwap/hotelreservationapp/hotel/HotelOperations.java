package com.galwap.hotelreservationapp.hotel;

import com.galwap.hotelreservationapp.exception.ObjectExistsException;
import com.galwap.hotelreservationapp.exception.ObjectNotExistException;
import com.galwap.hotelreservationapp.util.Operations;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public final class HotelOperations implements Operations<HotelDto> {

    private final HotelRepository hotelRepository;

    @Override
    public HotelDto find(int id) throws ObjectNotExistException {
        Optional<Hotel> hotel = hotelRepository.findById(id);
        if (hotel.isEmpty()) {
            throw new ObjectNotExistException();
        }
        return HotelDto.from(hotel.get());
    }

    @Override
    public List<HotelDto> findAll(HotelDto hotelRecord) {
        return hotelRepository.findAll(Example.of(Hotel.from(hotelRecord), EXAMPLE_MATCHER))
                .stream()
                .map(HotelDto::from)
                .toList();
    }

    @Override
    public void add(HotelDto object) throws ObjectExistsException {
        Optional<Hotel> hotel = hotelRepository.findAllByNameAndCityAndStreetAndStreetNoAndPostCode(
                object.getName(), object.getCity(), object.getStreet(), object.getStreetNo(), object.getPostCode());
        if (hotel.isPresent()) {
            throw new ObjectExistsException();
        }
        Hotel from = Hotel.from(object);
        hotelRepository.save(from);
    }

    @Override
    public void delete(int id) throws ObjectNotExistException {
        Optional<Hotel> hotel = hotelRepository.findById(id);
        if (hotel.isEmpty()) {
            throw new ObjectNotExistException();
        }
        hotelRepository.deleteById(id);
    }

    @Override
    public HotelDto modify(HotelDto object) throws ObjectNotExistException {
        Optional<Hotel> hotel = hotelRepository.findById(object.getId());
        if (hotel.isEmpty()) {
            throw new ObjectNotExistException();
        }
        Hotel from = Hotel.from(object);
        Hotel save = hotelRepository.save(from);
        return HotelDto.from(save);
    }
}
