package com.nagp.restaurantsandmenuservice.controller;

import com.nagp.restaurantsandmenuservice.dto.FoodMenuDTO;
import com.nagp.restaurantsandmenuservice.entity.FoodItem;
import com.nagp.restaurantsandmenuservice.entity.FoodMenu;
import com.nagp.restaurantsandmenuservice.entity.Restaurant;
import com.nagp.restaurantsandmenuservice.exception.CustomException;
import com.nagp.restaurantsandmenuservice.model.AddItem;
import com.nagp.restaurantsandmenuservice.model.UpdateItemQuantity;
import com.nagp.restaurantsandmenuservice.response.SearchResponse;
import com.nagp.restaurantsandmenuservice.response.SuccessResponse;
import com.nagp.restaurantsandmenuservice.service.FoodItemService;
import com.nagp.restaurantsandmenuservice.service.FoodMenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Validated
@RestController
@RequestMapping("/food-menu")
public class FoodMenuController {

    private final FoodMenuService foodMenuService;
    private final FoodItemService foodItemService;

    @Value("${server.port}")
    private int port;

    private final static Logger log = LoggerFactory.getLogger(FoodMenuController.class);

    @Autowired
    public FoodMenuController(FoodMenuService foodMenuService,FoodItemService foodItemService) {
        this.foodMenuService = foodMenuService;
        this.foodItemService=foodItemService;
    }


    @GetMapping(path = "/userSearch", produces = "application/json")
    public SearchResponse searchByUser(@RequestParam @NotNull String areaCode, @RequestParam @NotNull String foodItem, @RequestParam @NotNull String restaurant, @RequestHeader("X-Authenticated-User") String username) {
        log.info("Working from port " + port + " of restaurants-and-menu-service");
        return foodMenuService.searchByUser(areaCode, foodItem, restaurant, username);
    }

    @GetMapping(path = "/getRestaurantMenu", produces = "application/json")
    public FoodMenuDTO getMenuByRestaurantId(@RequestParam @NotNull @NotEmpty String restaurantId) throws CustomException {
        log.info("Working from port " + port + " of restaurants-and-menu-service");
        return foodMenuService.getMenuByRestaurantId(restaurantId);
    }

    @PostMapping(path = "/addItemsToRestaurantMenu", produces = "application/json")
    public SuccessResponse addItemsToRestaurantMenu(@RequestBody @Valid AddItem addItem, @RequestParam @NotEmpty @NotNull String restaurantId) throws CustomException {
        log.info("Working from port " + port + " of restaurants-and-menu-service");
        return foodMenuService.addItemsToRestaurantMenu(addItem,restaurantId);
    }

    @GetMapping(path = "/getFoodItemDetails", produces = "application/json")
    public FoodItem getFoodItemId(@RequestParam @NotNull @NotEmpty String itemId) throws CustomException {
        log.info("Working from port " + port + " of restaurants-and-menu-service");
        return foodItemService.getFoodItemById(itemId);
    }

    @PostMapping(path = "/updateItemQuantity", produces = "application/json")
    public String updateItemQuantity(@RequestBody @Valid UpdateItemQuantity updateItemQuantity) throws CustomException {
        log.info("Working from port " + port + " of restaurants-and-menu-service");
        return foodItemService.updateItemQuantity(updateItemQuantity);
    }

    @GetMapping(path = "/getRestaurantByItemId", produces = "application/json")
    public Restaurant getRestaurantByItemId(@RequestParam @NotNull @NotEmpty String itemId) throws CustomException {
        return foodItemService.getRestaurantByItemId(itemId);
    }

}
