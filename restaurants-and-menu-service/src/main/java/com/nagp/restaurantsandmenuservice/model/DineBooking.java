package com.nagp.restaurantsandmenuservice.model;

import com.nagp.restaurantsandmenuservice.enums.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DineBooking {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMMM-yyyy HH:mm:ss");

    private String bookingId;
    private String restaurantId;
    private String username;
    private String userEmail;
    private String userContactNo;
    private String date;
    private LocalTime startTime;
    private LocalTime endTime;
    @Enumerated(EnumType.ORDINAL)
    private BookingStatus bookingStatus;
    private String bookedOn;
    private Integer noOfGuests;

    public DineBooking(String restaurantId, String username, LocalTime startTime, LocalTime endTime, String userEmail, String userContactNo, BookingStatus bookingStatus, String date, Integer noOfGuests) {
        this.bookingId = UUID.randomUUID().toString();
        this.restaurantId = restaurantId;
        this.username = username;
        this.bookedOn = getFormattedDate(new Date());
        this.bookingStatus = bookingStatus;
        this.date = date;
        this.noOfGuests = noOfGuests;
        this.userEmail = userEmail;
        this.userContactNo = userContactNo;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static String getFormattedDate(Date date) {
        return dateFormat.format(date);
    }
}
