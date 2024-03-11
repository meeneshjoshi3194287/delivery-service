package com.nagp.diningservice.response;

import com.nagp.diningservice.enums.BookingStatus;
import com.nagp.diningservice.model.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Booking {
    private String bookingId;
    private Restaurant restaurant;
    private String date;
    private LocalTime startTime;
    private LocalTime endTime;
    private BookingStatus bookingStatus;
    private String bookedOn;
    private Integer noOfGuests;
}
