package com.nagp.restaurantsandmenuservice.service;

import com.nagp.restaurantsandmenuservice.entity.FoodItem;
import com.nagp.restaurantsandmenuservice.entity.Restaurant;
import com.nagp.restaurantsandmenuservice.exception.CustomException;
import com.nagp.restaurantsandmenuservice.model.UpdateItemQuantity;

public interface FoodItemService {

    FoodItem save(FoodItem foodItem);

    FoodItem getFoodItemById(String itemId) throws CustomException;

    String updateItemQuantity(UpdateItemQuantity updateItemQuantity) throws CustomException;

    Restaurant getRestaurantByItemId(String itemId);
}
