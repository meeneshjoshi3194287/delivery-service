package com.nagp.diningservice.controller;

import com.nagp.diningservice.model.DiningModel;
import com.nagp.diningservice.response.Booking;
import com.nagp.diningservice.response.BookingResponse;
import com.nagp.diningservice.service.BookingService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@Validated
@RequestMapping
public class DiningController {

    private final BookingService bookingService;

    @Value("${server.port}")
    private int port;

    private static final Logger log = LoggerFactory.getLogger(DiningController.class);

    @Autowired
    public DiningController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping(path = "/bookDining", produces = "application/json")
    @HystrixCommand(fallbackMethod = "getDineBookingFallbackResponse")
    public BookingResponse dineBooking(@RequestBody @Valid DiningModel diningModel, @RequestHeader("X-Authenticated-User") String username) throws IllegalArgumentException {
        log.info("Working from port " + port + " of dining-service");
        return bookingService.dineBooking(diningModel, username);
    }

    @PostMapping(path = "/viewBooking", produces = "application/json")
    public List<Booking> viewBookings(@RequestHeader("X-Authenticated-User") String username) throws IllegalArgumentException {
        log.info("Working from port " + port + " of dining-service");
        return bookingService.viewBookings(username);
    }

    @PostMapping(path = "/cancelBooking", produces = "application/json")
    @HystrixCommand(fallbackMethod = "getCancelBookingFallbackResponse")
    public BookingResponse cancelBooking(@RequestParam @NotBlank(message = "bookingId is required") String bookingId) throws IllegalArgumentException {
        log.info("Working from port " + port + " of dining-service");
        return bookingService.cancelBooking(bookingId);
    }

    public BookingResponse getDineBookingFallbackResponse(@RequestBody @Valid DiningModel diningModel, @RequestHeader("X-Authenticated-User") String username) {
        log.info("Inside fallback method of dine booking ");
        return BookingResponse.builder().status("1").bookingId("").message("Your request has not been processed.Please try again after sometime").build();
    }

    public BookingResponse getCancelBookingFallbackResponse(@RequestBody String bookingId) {
        log.info("Inside fallback method of cancel dine booking ");
        return BookingResponse.builder().status("1").bookingId("").message("Your request has not been processed.").build();
    }

}
