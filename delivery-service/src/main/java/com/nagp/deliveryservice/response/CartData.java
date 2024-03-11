package com.nagp.deliveryservice.response;

import com.nagp.deliveryservice.enums.OrderStatus;
import com.nagp.deliveryservice.model.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartData {

    private String itemId;
    private String itemName;
    private Restaurant restaurantDetails;
    private Integer quantity;
    private OrderStatus orderStatus;
    private Double price;

}
