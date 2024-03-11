package com.nagp.restaurantsandmenuservice.service;

import com.nagp.restaurantsandmenuservice.dto.RestaurantDTO;
import com.nagp.restaurantsandmenuservice.entity.Restaurant;
import com.nagp.restaurantsandmenuservice.exception.CustomException;
import com.nagp.restaurantsandmenuservice.model.DeliveryNotification;
import com.nagp.restaurantsandmenuservice.model.DiningNotification;
import com.nagp.restaurantsandmenuservice.model.RestaurantModel;
import com.nagp.restaurantsandmenuservice.response.SuccessResponse;

import java.util.List;

public interface RestaurantService {

    Restaurant saveRestaurant(Restaurant restaurant);

    List<RestaurantDTO> getAllRestaurants();

    Restaurant addRestaurant(RestaurantModel restaurant) throws CustomException;

    RestaurantDTO getRestaurantById(String restaurantId) throws CustomException;

    List<RestaurantDTO> getRestaurantByName(String restaurantId) throws CustomException;

    List<RestaurantDTO> getRestaurantByAreaCode(String areaCode) throws CustomException;

    SuccessResponse markFavourite(String restaurantId, String username) throws CustomException;

    void sendDeliveryNotification(DeliveryNotification notification);

    void sendDiningNotification(DiningNotification notification);
}
