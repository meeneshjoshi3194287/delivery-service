package com.nagp.restaurantsandmenuservice.service;

import com.nagp.restaurantsandmenuservice.dto.FoodMenuDTO;
import com.nagp.restaurantsandmenuservice.entity.FoodMenu;
import com.nagp.restaurantsandmenuservice.exception.CustomException;
import com.nagp.restaurantsandmenuservice.model.AddItem;
import com.nagp.restaurantsandmenuservice.response.SearchResponse;
import com.nagp.restaurantsandmenuservice.response.SuccessResponse;

public interface FoodMenuService {

    FoodMenu saveMenu(FoodMenu foodMenu);

    SearchResponse searchByUser(String areaCode, String foodItem, String restaurant, String username);

    FoodMenuDTO getMenuByRestaurantId(String restaurantId) throws CustomException;

    SuccessResponse addItemsToRestaurantMenu(AddItem addItem,String restaurantId) throws CustomException;
}
