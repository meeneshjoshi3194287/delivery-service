package com.nagp.restaurantsandmenuservice.dto;

import com.nagp.restaurantsandmenuservice.entity.FoodItem;
import com.nagp.restaurantsandmenuservice.entity.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FoodMenuDTO {

    private RestaurantDTO restaurant;
    private List<FoodItemDTO> foodItems;

}
