package com.galwap.hotelreservationapp.hotel;

import com.galwap.hotelreservationapp.exception.ObjectExistsException;
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
@RequestMapping("/hotel")
@RequiredArgsConstructor
public class HotelController {

    private final HotelOperations hotelOperations;
    private final TokenValidation tokenValidation;
    private final RoleValidation roleValidation;

    @GetMapping
    public List<HotelDto> getHotels(@RequestBody(required = false) HotelDto hotel,
                                    HttpServletRequest httpServletRequest) throws UnauthorisedException {
        String header = httpServletRequest.getHeader(TOKEN_KEY);
        tokenValidation.validateToken(header);
        return hotelOperations.findAll(hotel);
    }

    @PostMapping
    public void addHotel(@RequestBody HotelDto hotel,
                         HttpServletRequest httpServletRequest) throws ObjectExistsException, UnauthorisedException, WrongRoleException {
        String header = httpServletRequest.getHeader(TOKEN_KEY);
        UserDetails userDetails = tokenValidation.validateToken(header);
        roleValidation.validateHotelRole(userDetails);
        hotelOperations.add(hotel);
    }

    @DeleteMapping("/{id}")
    public void removeHotel(@PathVariable("id") Integer id,
                            HttpServletRequest httpServletRequest) throws ObjectNotExistException, UnauthorisedException, WrongRoleException {
        String header = httpServletRequest.getHeader(TOKEN_KEY);
        UserDetails userDetails = tokenValidation.validateToken(header);
        roleValidation.validateHotelRole(userDetails);
        hotelOperations.delete(id);
    }

    @PutMapping
    public HotelDto updateHotel(@RequestBody HotelDto hotel,
                                HttpServletRequest httpServletRequest) throws ObjectNotExistException, UnauthorisedException, WrongRoleException {
        String header = httpServletRequest.getHeader(TOKEN_KEY);
        UserDetails userDetails = tokenValidation.validateToken(header);
        roleValidation.validateHotelRole(userDetails);
        return hotelOperations.modify(hotel);
    }
}
