package com.nagp.deliveryservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeliveryNotification {

    private String username;
    private Restaurant restaurant;
    private FoodItem foodItem;
    private Integer quantity;

}
