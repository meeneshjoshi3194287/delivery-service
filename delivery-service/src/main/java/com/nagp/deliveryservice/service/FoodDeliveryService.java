package com.nagp.deliveryservice.service;

import com.nagp.deliveryservice.exception.CustomException;
import com.nagp.deliveryservice.model.AddFoodItem;
import com.nagp.deliveryservice.model.ConfirmOrder;
import com.nagp.deliveryservice.response.CartViewResponse;
import com.nagp.deliveryservice.response.OrderResponse;

public interface FoodDeliveryService {


    String addItem(AddFoodItem addFoodItem,String username) throws CustomException;

    String removeItem(String itemId, String username);

    CartViewResponse viewCart(String username);

    OrderResponse confirmOrder(ConfirmOrder confirmOrder,String username);

    OrderResponse cancelOrder(String orderId);
}
