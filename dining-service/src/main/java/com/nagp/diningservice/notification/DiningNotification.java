package com.nagp.diningservice.notification;

import com.nagp.diningservice.entity.DineBooking;
import com.nagp.diningservice.model.Restaurant;
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
