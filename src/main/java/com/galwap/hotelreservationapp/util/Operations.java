package com.galwap.hotelreservationapp.util;

import com.galwap.hotelreservationapp.exception.ObjectExistsException;
import com.galwap.hotelreservationapp.exception.ObjectNotExistException;
import org.springframework.data.domain.ExampleMatcher;

import java.util.List;

public interface Operations<I> {

    ExampleMatcher EXAMPLE_MATCHER = ExampleMatcher.matchingAll().withIgnoreCase();

    I find(int id) throws ObjectNotExistException;

    List<I> findAll(I object);

    void add(I object) throws ObjectExistsException;

    void delete(int id) throws ObjectNotExistException;

    I modify(I object) throws ObjectNotExistException;
}
