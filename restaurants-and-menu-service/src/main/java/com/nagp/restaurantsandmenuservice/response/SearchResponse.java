package com.nagp.restaurantsandmenuservice.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nagp.restaurantsandmenuservice.dto.FoodItemDTO;
import com.nagp.restaurantsandmenuservice.dto.FoodMenuDTO;
import com.nagp.restaurantsandmenuservice.dto.RestaurantDTO;
import com.nagp.restaurantsandmenuservice.entity.FoodItem;
import com.nagp.restaurantsandmenuservice.entity.FoodMenu;
import com.nagp.restaurantsandmenuservice.entity.Restaurant;
import com.nagp.restaurantsandmenuservice.enums.CategoryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SearchResponse {

    private List<RestaurantDTO> favourites;
    private List<FoodMenuDTO> foodMenuByRestaurants;
    private Map<CategoryType, Map<String, List<FoodItemDTO>>> categorizedFoodMenu;

}
