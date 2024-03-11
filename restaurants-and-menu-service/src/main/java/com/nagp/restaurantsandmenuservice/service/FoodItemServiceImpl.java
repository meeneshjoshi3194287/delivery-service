package com.nagp.restaurantsandmenuservice.service;

import com.nagp.restaurantsandmenuservice.entity.FoodItem;
import com.nagp.restaurantsandmenuservice.entity.FoodMenu;
import com.nagp.restaurantsandmenuservice.entity.Restaurant;
import com.nagp.restaurantsandmenuservice.enums.UpdateItem;
import com.nagp.restaurantsandmenuservice.exception.CustomException;
import com.nagp.restaurantsandmenuservice.model.UpdateItemQuantity;
import com.nagp.restaurantsandmenuservice.repository.FoodItemRepository;
import com.nagp.restaurantsandmenuservice.repository.FoodMenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class FoodItemServiceImpl implements FoodItemService {

    @Autowired
    FoodItemRepository foodItemRepository;

    @Autowired
    FoodMenuRepository foodMenuRepository;

    public FoodItem save(FoodItem foodItem) {
        return foodItemRepository.save(foodItem);
    }

    public FoodItem getFoodItemById(String itemId) throws CustomException{
        Optional<FoodItem> foodItem=foodItemRepository.findById(itemId);
        if (!foodItem.isPresent()) throw new CustomException("Invalid item id");
        return foodItem.get();
    }

    @Transactional
    public String updateItemQuantity(UpdateItemQuantity updateItemQuantity) throws CustomException{
        if(!(updateItemQuantity.getUpdate().toString().equalsIgnoreCase("add") ||
                updateItemQuantity.getUpdate().toString().equalsIgnoreCase("remove"))){
            throw new CustomException("Update can only be 'add' or 'remove'");
        }
        Optional<FoodItem> foodItem=foodItemRepository.findById(updateItemQuantity.getItemId());
        if (!foodItem.isPresent()) throw new CustomException("Invalid item id");
        if(updateItemQuantity.getUpdate().equals(UpdateItem.ADD)) {
            foodItemRepository.addItemQuantity(updateItemQuantity.getItemId(), updateItemQuantity.getQuantity());
            return "Item quantity increased by "+updateItemQuantity.getQuantity();
        }
        foodItemRepository.removeItemQuantity(updateItemQuantity.getItemId(), updateItemQuantity.getQuantity());
        return "Item quantity decreased by "+updateItemQuantity.getQuantity();
    }

    public Restaurant getRestaurantByItemId(String itemId) {
        List<FoodMenu> foodMenuList = (List<FoodMenu>)foodMenuRepository.findAll();
        Optional<Restaurant> optionalRestaurant = foodMenuList.stream()
                .filter(foodMenu ->
                        foodMenu.getFoodItems().stream()
                                .anyMatch(foodItem -> foodItem.getItemId().equals(itemId))
                )
                .findFirst()
                .map(FoodMenu::getRestaurant);

        return optionalRestaurant.orElse(null);
    }
}
