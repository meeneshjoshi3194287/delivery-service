package com.nagp.diningservice.service;

import com.nagp.diningservice.exception.CustomException;
import com.nagp.diningservice.model.DiningModel;
import com.nagp.diningservice.response.Booking;
import com.nagp.diningservice.response.BookingResponse;
import org.springframework.stereotype.Service;

import java.util.List;


public interface BookingService {
    BookingResponse dineBooking(DiningModel diningModel, String username);

    BookingResponse cancelBooking(String bookingId);

    List<Booking> viewBookings(String username);
}
