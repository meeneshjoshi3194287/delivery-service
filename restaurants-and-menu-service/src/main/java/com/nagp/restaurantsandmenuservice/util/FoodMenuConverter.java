package com.nagp.restaurantsandmenuservice.util;

import com.nagp.restaurantsandmenuservice.dto.AddressDTO;
import com.nagp.restaurantsandmenuservice.dto.FoodItemDTO;
import com.nagp.restaurantsandmenuservice.dto.FoodMenuDTO;
import com.nagp.restaurantsandmenuservice.dto.RestaurantDTO;
import com.nagp.restaurantsandmenuservice.entity.Address;
import com.nagp.restaurantsandmenuservice.entity.FoodItem;
import com.nagp.restaurantsandmenuservice.entity.FoodMenu;
import com.nagp.restaurantsandmenuservice.entity.Restaurant;

import java.util.List;
import java.util.stream.Collectors;

public class FoodMenuConverter {

    public static List<FoodMenuDTO> convertToDTOList(List<FoodMenu> foodMenus) {
        return foodMenus.stream()
                .map(FoodMenuConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    public static FoodMenuDTO convertToDTO(FoodMenu foodMenu) {
        FoodMenuDTO foodMenuDTO = new FoodMenuDTO();
        foodMenuDTO.setRestaurant(convertRestaurantToDTO(foodMenu.getRestaurant()));
        foodMenuDTO.setFoodItems(convertFoodItemsToDTO(foodMenu.getFoodItems()));
        return foodMenuDTO;
    }

    public static RestaurantDTO convertRestaurantToDTO(Restaurant restaurant) {
        RestaurantDTO restaurantDTO = new RestaurantDTO();
        restaurantDTO.setRestaurantId(restaurant.getId());
        restaurantDTO.setRestaurantName(restaurant.getRestaurantName());
        restaurantDTO.setAddress(convertAddressToDTO(restaurant.getAddress()));
        return restaurantDTO;
    }

    public static AddressDTO convertAddressToDTO(Address address) {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setStreetAddress(address.getStreetAddress());
        addressDTO.setCity(address.getCity());
        addressDTO.setAreaCode(address.getAreaCode());
        addressDTO.setOpenTime(address.getOpenTime());
        addressDTO.setCloseTime(address.getCloseTime());
        addressDTO.setDiningCapacity(address.getDiningCapacity());
        return addressDTO;
    }

    public static List<FoodItemDTO> convertFoodItemsToDTO(List<FoodItem> foodItems) {
        return foodItems.stream()
                .map(FoodMenuConverter::convertFoodItemToDTO)
                .collect(Collectors.toList());
    }

    public static FoodItemDTO convertFoodItemToDTO(FoodItem foodItem) {
        FoodItemDTO foodItemDTO = new FoodItemDTO();
        foodItemDTO.setItemId(foodItem.getItemId());
        foodItemDTO.setItemName(foodItem.getItemName());
        foodItemDTO.setCategory(foodItem.getCategory());
        foodItemDTO.setDescription(foodItem.getDescription());
        foodItemDTO.setPrice(foodItem.getPrice());
        foodItemDTO.setQuantity(foodItem.getQuantity());
        return foodItemDTO;
    }
}