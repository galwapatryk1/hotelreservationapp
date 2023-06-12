package com.galwap.hotelreservationapp.hotelroom;

import com.galwap.hotelreservationapp.exception.ObjectNotExistException;
import com.galwap.hotelreservationapp.exception.UnauthorisedException;
import com.galwap.hotelreservationapp.exception.WrongRoleException;
import com.galwap.hotelreservationapp.validation.RoleValidation;
import com.galwap.hotelreservationapp.validation.TokenValidation;
import com.galwap.hotelreservationapp.validation.UserDetails;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.galwap.hotelreservationapp.validation.TokenValidation.TOKEN_KEY;

@RestController
@RequestMapping("/hotelRoom")
@RequiredArgsConstructor
public class HotelRoomController {

     private final HotelRoomOperations hotelRoomOperations;
     private final TokenValidation tokenValidation;
     private final RoleValidation roleValidation;

    @GetMapping
    public List<HotelRoomDto> getHotelRooms(@RequestBody(required = false) HotelRoomDto hotelRoom,
                                            HttpServletRequest httpServletRequest) throws UnauthorisedException {
        String header = httpServletRequest.getHeader(TOKEN_KEY);
        tokenValidation.validateToken(header);
        return hotelRoomOperations.findAll(hotelRoom);
    }

    @PostMapping
    public void addHotelRoom(@RequestBody HotelRoomDto hotelRoom,
                             HttpServletRequest httpServletRequest) throws UnauthorisedException, WrongRoleException {
        String header = httpServletRequest.getHeader(TOKEN_KEY);
        UserDetails userDetails = tokenValidation.validateToken(header);
        roleValidation.validateHotelRoomRole(userDetails);
        hotelRoomOperations.add(hotelRoom);
    }

    @DeleteMapping("/{id}")
    public void removeHotelRoom(@PathVariable("id") Integer id,
                                HttpServletRequest httpServletRequest) throws ObjectNotExistException, UnauthorisedException, WrongRoleException {
        String header = httpServletRequest.getHeader(TOKEN_KEY);
        UserDetails userDetails = tokenValidation.validateToken(header);
        roleValidation.validateHotelRoomRole(userDetails);
        hotelRoomOperations.delete(id);
    }

    @PutMapping
    public HotelRoomDto updateHotelRoom(@RequestBody HotelRoomDto hotelRoom,
                                        HttpServletRequest httpServletRequest) throws ObjectNotExistException, UnauthorisedException, WrongRoleException {
        String header = httpServletRequest.getHeader(TOKEN_KEY);
        UserDetails userDetails = tokenValidation.validateToken(header);
        roleValidation.validateHotelRoomRole(userDetails);
        return hotelRoomOperations.modify(hotelRoom);
    }
}
