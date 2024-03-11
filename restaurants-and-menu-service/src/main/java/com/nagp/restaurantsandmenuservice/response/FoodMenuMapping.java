package com.nagp.restaurantsandmenuservice.response;

import com.nagp.restaurantsandmenuservice.dto.FoodItemDTO;
import com.nagp.restaurantsandmenuservice.entity.FoodItem;
import com.nagp.restaurantsandmenuservice.entity.Restaurant;
import com.nagp.restaurantsandmenuservice.enums.CategoryType;

public class FoodMenuMapping {
    private final String restaurantName;
    private final FoodItemDTO foodItem;
    private final CategoryType category;

    public FoodMenuMapping(CategoryType category, String restaurant, FoodItemDTO foodItem) {
        this.restaurantName = restaurant;
        this.foodItem = foodItem;
        this.category = foodItem.getCategory();
    }

    public FoodItemDTO getFoodItem() {
        return foodItem;
    }

    public CategoryType getCategory() {
        return category;
    }

    public String getRestaurant() {
        return restaurantName;
    }
}