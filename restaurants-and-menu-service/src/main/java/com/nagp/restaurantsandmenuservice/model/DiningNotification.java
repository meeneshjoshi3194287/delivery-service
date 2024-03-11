package com.nagp.restaurantsandmenuservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class DiningNotification {

    private String username;
    private Restaurant restaurant;
    private DineBooking dineBooking;

}
