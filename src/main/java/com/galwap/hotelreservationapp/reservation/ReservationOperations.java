package com.galwap.hotelreservationapp.reservation;

import com.galwap.hotelreservationapp.exception.ObjectExistsException;
import com.galwap.hotelreservationapp.exception.ObjectNotExistException;
import com.galwap.hotelreservationapp.exception.ReservationNotAssociatedWithAccountException;
import com.galwap.hotelreservationapp.util.Operations;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public final class ReservationOperations implements Operations<ReservationDto> {

    private final ReservationRepository reservationRepository;

    @Override
    public ReservationDto find(int id) throws ObjectNotExistException {
        Optional<Reservation> reservation = reservationRepository.findById(id);
        if (reservation.isEmpty()) {
            throw new ObjectNotExistException();
        }
        return ReservationDto.from(reservation.get());
    }

    @Override
    public List<ReservationDto> findAll(ReservationDto reservation) {
        Reservation from = Reservation.from(reservation);
        return reservationRepository.findAll(Example.of(from, EXAMPLE_MATCHER))
                .stream()
                .map(ReservationDto::from)
                .toList();
    }

    @Override
    public void add(ReservationDto object) throws ObjectExistsException {
        Optional<Reservation> reservation = reservationRepository.findByRoomIdAndHotelIdAndFromDateAfterAndToDateBefore(
                object.getHotelId(), object.getRoomId(), object.getFromDate(), object.getToDate());
        if (reservation.isEmpty()) {
            throw new ObjectExistsException();
        }
        Reservation from = Reservation.from(object);
        reservationRepository.save(from);
    }

    public void add(ReservationDto object, String userId) throws ObjectExistsException {
        Optional<Reservation> reservation = reservationRepository.findByRoomIdAndHotelIdAndFromDateAfterAndToDateBefore(
                object.getHotelId(), object.getRoomId(), object.getFromDate(), object.getToDate());
        if (reservation.isEmpty()) {
            throw new ObjectExistsException();
        }
        Reservation from = Reservation.from(object);
        from.setUserId(userId);
        reservationRepository.save(from);
    }

    @Override
    public void delete(int id) throws ObjectNotExistException {
        Optional<Reservation> reservation = reservationRepository.findById(id);
        if (reservation.isPresent()) {
            throw new ObjectNotExistException();
        }
        reservationRepository.deleteById(id);
    }

    public void delete(int id, String userId) throws ObjectNotExistException, ReservationNotAssociatedWithAccountException {
        Optional<Reservation> reservation = reservationRepository.findById(id);
        if (reservation.isEmpty()) {
            throw new ObjectNotExistException();
        }
        if (reservation.get().getUserId().equals(userId)) {
            reservationRepository.deleteById(id);
        } else {
            throw new ReservationNotAssociatedWithAccountException();
        }
    }

    @Override
    public ReservationDto modify(ReservationDto object) throws ObjectNotExistException {
        Optional<Reservation> reservation = reservationRepository.findById(object.getId());
        if (reservation.isEmpty()) {
            throw new ObjectNotExistException();
        }
        Reservation from = Reservation.from(object);
        Reservation save = reservationRepository.save(from);
        return ReservationDto.from(save);
    }

    public ReservationDto modify(ReservationDto object, String userId) throws ObjectNotExistException, ReservationNotAssociatedWithAccountException {
        Optional<Reservation> reservation = reservationRepository.findById(object.getId());
        if (reservation.isEmpty()) {
            throw new ObjectNotExistException();
        }
        if (!reservation.get().getUserId().equals(userId)) {
            throw new ReservationNotAssociatedWithAccountException();
        }
        Reservation from = Reservation.from(object);
        Reservation save = reservationRepository.save(from);
        return ReservationDto.from(save);
    }
}
