package com.galwap.hotelreservationapp.hotelroom;

import com.galwap.hotelreservationapp.exception.ObjectNotExistException;
import com.galwap.hotelreservationapp.util.Operations;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public final class HotelRoomOperations implements Operations<HotelRoomDto> {

    private final HotelRoomRepository hotelRoomRepository;

    @Override
    public HotelRoomDto find(int id) throws ObjectNotExistException {
        Optional<HotelRoom> hotelRoom = hotelRoomRepository.findById(id);
        if (hotelRoom.isEmpty()) {
            throw new ObjectNotExistException();
        }
        return HotelRoomDto.from(hotelRoom.get());
    }

    @Override
    public List<HotelRoomDto> findAll(HotelRoomDto object) {
        HotelRoom hotelRoom = HotelRoom.from(object);
        return hotelRoomRepository.findAll(Example.of(hotelRoom, EXAMPLE_MATCHER))
                .stream()
                .map(HotelRoomDto::from)
                .toList();
    }

    @Override
    public void add(HotelRoomDto object) {
        hotelRoomRepository.save(HotelRoom.from(object));
    }

    @Override
    public void delete(int id) throws ObjectNotExistException {
        Optional<HotelRoom> hotelRoom = hotelRoomRepository.findById(id);
        if (hotelRoom.isPresent()) {
            throw new ObjectNotExistException();
        }
        hotelRoomRepository.deleteById(id);
    }

    @Override
    public HotelRoomDto modify(HotelRoomDto object) throws ObjectNotExistException {
        Optional<HotelRoom> hotelRoom = hotelRoomRepository.findById(object.getId());
        if (hotelRoom.isEmpty()) {
            throw new ObjectNotExistException();
        }
        HotelRoom mappedHotelRoom = HotelRoom.from(object);
        HotelRoom save = hotelRoomRepository.save(mappedHotelRoom);
        return HotelRoomDto.from(save);
    }
}
