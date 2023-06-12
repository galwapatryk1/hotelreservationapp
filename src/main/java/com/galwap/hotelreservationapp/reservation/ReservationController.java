package com.galwap.hotelreservationapp.reservation;

import com.galwap.hotelreservationapp.exception.*;
import com.galwap.hotelreservationapp.validation.RoleValidation;
import com.galwap.hotelreservationapp.validation.TokenValidation;
import com.galwap.hotelreservationapp.validation.UserDetails;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.galwap.hotelreservationapp.validation.TokenValidation.TOKEN_KEY;

@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationOperations reservationOperations;
    private final TokenValidation tokenValidation;
    private final RoleValidation roleValidation;

    @GetMapping
    public List<ReservationDto> getReservations(@RequestBody(required = false) ReservationDto reservation,
                                                HttpServletRequest httpServletRequest) throws UnauthorisedException, WrongRoleException {
        String header = httpServletRequest.getHeader(TOKEN_KEY);
        UserDetails userDetails = tokenValidation.validateToken(header);
        roleValidation.validateReservationRole(userDetails);
        if (roleValidation.isClientRole(userDetails.getUserRole())) {
            reservation.setUserId(userDetails.getUserId());
        }
        return reservationOperations.findAll(reservation);
    }

    @PostMapping
    public void addReservation(@RequestBody ReservationDto reservation,
                               HttpServletRequest httpServletRequest) throws ObjectExistsException, UnauthorisedException, WrongRoleException {
        String header = httpServletRequest.getHeader(TOKEN_KEY);
        UserDetails userDetails = tokenValidation.validateToken(header);
        roleValidation.validateReservationRole(userDetails);
        if (roleValidation.isClientRole(userDetails.getUserRole())) {
            reservationOperations.add(reservation, userDetails.getUserId());
        } else {
            reservationOperations.add(reservation);
        }
    }

    @DeleteMapping("/{id}")
    public void removeReservation(@PathVariable("id") Integer id,
                                  HttpServletRequest httpServletRequest) throws ObjectNotExistException, UnauthorisedException, WrongRoleException, ReservationNotAssociatedWithAccountException {
        String header = httpServletRequest.getHeader(TOKEN_KEY);
        UserDetails userDetails = tokenValidation.validateToken(header);
        roleValidation.validateReservationRole(userDetails);
        if (roleValidation.isClientRole(userDetails.getUserRole())) {
            reservationOperations.delete(id, userDetails.getUserId());
        } else {
            reservationOperations.delete(id);
        }
    }

    @PutMapping
    public ReservationDto updateReservation(@RequestBody ReservationDto reservation,
                                            HttpServletRequest httpServletRequest) throws ObjectNotExistException, UnauthorisedException, WrongRoleException, ReservationNotAssociatedWithAccountException {
        String header = httpServletRequest.getHeader(TOKEN_KEY);
        UserDetails userDetails = tokenValidation.validateToken(header);
        roleValidation.validateReservationRole(userDetails);
        if (roleValidation.isClientRole(userDetails.getUserRole())) {
            return reservationOperations.modify(reservation, userDetails.getUserId());
        }
        return reservationOperations.modify(reservation);
    }
}
