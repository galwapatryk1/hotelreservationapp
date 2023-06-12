package com.galwap.hotelreservationapp.validation;

import com.galwap.hotelreservationapp.exception.WrongRoleException;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@NoArgsConstructor
public class RoleValidation {

    private final List<String> hotelRoomRoles = List.of("PREZES", "MANAGER");
    private final List<String> reservationRoles = List.of("MANAGER", "PERSONEL", "KLIENT");

    public void validateHotelRole(UserDetails userDetails) throws WrongRoleException {
        if (!userDetails.getUserRole().equals("PREZES")) {
            throw new WrongRoleException();
        }
    }

    public void validateHotelRoomRole(UserDetails userDetails) throws WrongRoleException {
        if (!hotelRoomRoles.contains(userDetails.getUserRole())) {
            throw new WrongRoleException();
        }
    }

    public void validateReservationRole(UserDetails userDetails) throws WrongRoleException {
        if (!reservationRoles.contains(userDetails.getUserRole())) {
            throw new WrongRoleException();
        }
    }

    public boolean isClientRole(String role) {
        return role.equals("KLIENT");
    }
}
