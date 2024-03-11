package com.nagp.restaurantsandmenuservice.controller;

import com.nagp.restaurantsandmenuservice.dto.RestaurantDTO;
import com.nagp.restaurantsandmenuservice.entity.Address;
import com.nagp.restaurantsandmenuservice.entity.FoodItem;
import com.nagp.restaurantsandmenuservice.entity.FoodMenu;
import com.nagp.restaurantsandmenuservice.entity.Restaurant;
import com.nagp.restaurantsandmenuservice.enums.CategoryType;
import com.nagp.restaurantsandmenuservice.exception.CustomException;
import com.nagp.restaurantsandmenuservice.model.DeliveryNotification;
import com.nagp.restaurantsandmenuservice.model.DiningNotification;
import com.nagp.restaurantsandmenuservice.model.RestaurantModel;
import com.nagp.restaurantsandmenuservice.response.SuccessResponse;
import com.nagp.restaurantsandmenuservice.service.FoodItemService;
import com.nagp.restaurantsandmenuservice.service.FoodMenuService;
import com.nagp.restaurantsandmenuservice.service.RestaurantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Validated
@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final FoodItemService foodItemService;
    private final FoodMenuService foodMenuService;

    @Value("${server.port}")
    private int port;

    private final static Logger log = LoggerFactory.getLogger(Restaurant.class);

    @Autowired
    public RestaurantController(RestaurantService restaurantService, FoodItemService foodItemService, FoodMenuService foodMenuService) {
        this.restaurantService = restaurantService;
        this.foodItemService = foodItemService;
        this.foodMenuService = foodMenuService;
    }

    @PostConstruct
    public void updateRestaurantDatabase() {
        List<String> restaurantIds = new ArrayList<>();

        Restaurant restaurant = new Restaurant("Chilli's 360");
        Address address = new Address("987 Willow Avenue", "Bangalore", "Sector 31", 5, LocalTime.of(10, 0), LocalTime.of(22, 00));
        restaurant.setAddress(address);
        List<FoodItem> foodItemList = new ArrayList<>();
        foodItemList.add(foodItemService.save(new FoodItem("Butter Chicken", CategoryType.INDIAN_CUISINE, "Tender chicken in a rich, creamy tomato-based curry", 3, 11.99)));
        foodItemList.add(foodItemService.save(new FoodItem("Margherita Pizza", CategoryType.ITALIAN, "Classic pizza with tomato, mozzarella, and basil", 1, 10.99)));
        foodItemList.add(foodItemService.save(new FoodItem("Sushi Platter", CategoryType.JAPANESE, "Assorted sushi rolls and sashimi", 6, 15.99)));
        foodItemList.add(foodItemService.save(new FoodItem("Bibimbap", CategoryType.KOREAN, "Mixed rice with vegetables, meat, and spicy gochujang sauce", 1, 12.49)));
        foodItemList.add(foodItemService.save(new FoodItem("Dal Makhani", CategoryType.INDIAN_CUISINE, "Slow-cooked black lentils in a creamy tomato-based sauce", 1, 9.99)));
        foodItemList.add(foodItemService.save(new FoodItem("Ravioli with Spinach and Ricotta", CategoryType.ITALIAN, "Homemade pasta filled with spinach and ricotta", 1, 13.99)));
        foodItemList.add(foodItemService.save(new FoodItem("Tempura", CategoryType.JAPANESE, "Battered and deep-fried seafood and vegetables", 8, 12.99)));
        foodItemList.add(foodItemService.save(new FoodItem("Kimchi Fried Rice", CategoryType.KOREAN, "Fried rice with kimchi and assorted vegetables", 1, 11.99)));
        FoodMenu foodMenu = new FoodMenu(restaurant, foodItemList);
        foodMenuService.saveMenu(foodMenu);

        restaurant = new Restaurant("Palette Pleasures");
        address = new Address("210 Maple Lane", "Bangalore", "Sector 14", 4, LocalTime.of(9, 0), LocalTime.of(20, 00));
        restaurant.setAddress(address);
        foodItemList = new ArrayList<>();
        foodItemList.add(foodItemService.save(new FoodItem("Butter Chicken", CategoryType.INDIAN_CUISINE, "Tender chicken in a rich, creamy tomato-based curry", 4, 11.99)));
        foodItemList.add(foodItemService.save(new FoodItem("Margherita Pizza", CategoryType.ITALIAN, "Classic pizza with tomato, mozzarella, and basil", 2, 10.99)));
        foodItemList.add(foodItemService.save(new FoodItem("Sushi Platter", CategoryType.JAPANESE, "Assorted sushi rolls and sashimi", 5, 15.99)));
        foodItemList.add(foodItemService.save(new FoodItem("Bibimbap", CategoryType.KOREAN, "Mixed rice with vegetables, meat, and spicy gochujang sauce", 3, 12.49)));
        foodItemList.add(foodItemService.save(new FoodItem("Dal Makhani", CategoryType.INDIAN_CUISINE, "Slow-cooked black lentils in a creamy tomato-based sauce", 3, 9.99)));
        foodItemList.add(foodItemService.save(new FoodItem("Ravioli with Spinach and Ricotta", CategoryType.ITALIAN, "Homemade pasta filled with spinach and ricotta", 1, 13.99)));
        foodItemList.add(foodItemService.save(new FoodItem("Tempura", CategoryType.JAPANESE, "Battered and deep-fried seafood and vegetables", 8, 12.99)));
        foodItemList.add(foodItemService.save(new FoodItem("Kimchi Fried Rice", CategoryType.KOREAN, "Fried rice with kimchi and assorted vegetables", 1, 11.99)));
        foodMenu = new FoodMenu(restaurant, foodItemList);
        foodMenuService.saveMenu(foodMenu);

        restaurant = new Restaurant("Spice Paradise");
        address = new Address("456 Elm Avenue", "Bangalore", "Sector 12", 3, LocalTime.of(10, 0), LocalTime.of(21, 00));
        restaurant.setAddress(address);
        foodItemList = new ArrayList<>();
        foodItemList.add(foodItemService.save(new FoodItem("Butter Chicken", CategoryType.INDIAN_CUISINE, "Tender chicken in a rich, creamy tomato-based curry", 1, 11.99)));
        foodItemList.add(foodItemService.save(new FoodItem("Margherita Pizza", CategoryType.ITALIAN, "Classic pizza with tomato, mozzarella, and basil", 1, 10.99)));
        foodItemList.add(foodItemService.save(new FoodItem("Sushi Platter", CategoryType.JAPANESE, "Assorted sushi rolls and sashimi", 6, 15.99)));
        foodItemList.add(foodItemService.save(new FoodItem("Bibimbap", CategoryType.KOREAN, "Mixed rice with vegetables, meat, and spicy gochujang sauce", 1, 12.49)));
        foodMenu = new FoodMenu(restaurant, foodItemList);
        foodMenuService.saveMenu(foodMenu);

        restaurant = new Restaurant("Spice Paradise");
        address = new Address("789 Oak Lane", "Bangalore", "Sector 31", 7, LocalTime.of(11, 0), LocalTime.of(23, 00));
        restaurant.setAddress(address);
        foodItemList = new ArrayList<>();
        foodItemList.add(foodItemService.save(new FoodItem("Butter Chicken", CategoryType.INDIAN_CUISINE, "Tender chicken in a rich, creamy tomato-based curry", 1, 11.99)));
        foodItemList.add(foodItemService.save(new FoodItem("Margherita Pizza", CategoryType.ITALIAN, "Classic pizza with tomato, mozzarella, and basil", 1, 10.99)));
        foodItemList.add(foodItemService.save(new FoodItem("Sushi Platter", CategoryType.JAPANESE, "Assorted sushi rolls and sashimi", 6, 15.99)));
        foodItemList.add(foodItemService.save(new FoodItem("Bibimbap", CategoryType.KOREAN, "Mixed rice with vegetables, meat, and spicy gochujang sauce", 1, 12.49)));
        foodItemList.add(foodItemService.save(new FoodItem("Sashimi Platter", CategoryType.JAPANESE, "Fresh slices of raw fish served with wasabi and soy sauce", 6, 18.99)));
        foodItemList.add(foodItemService.save(new FoodItem("Japchae", CategoryType.KOREAN, "Stir-fried glass noodles with vegetables", 1, 13.99)));
        foodItemList.add(foodItemService.save(new FoodItem("Chole Bhature", CategoryType.INDIAN_CUISINE, "Chickpea curry served with deep-fried bread", 1, 9.49)));
        foodItemList.add(foodItemService.save(new FoodItem("Tiramisu", CategoryType.ITALIAN, "Classic Italian dessert with coffee-soaked ladyfingers and mascarpone", 1, 6.99)));
        foodMenu = new FoodMenu(restaurant, foodItemList);
        foodMenuService.saveMenu(foodMenu);

        restaurant = new Restaurant("Wholesome Delights");
        address = new Address("321 Pine Road", "Bangalore", "Sector 22", 5, LocalTime.of(10, 0), LocalTime.of(21, 00));
        restaurant.setAddress(address);
        foodItemList = new ArrayList<>();
        foodItemList.add(foodItemService.save(new FoodItem("Butter Chicken", CategoryType.INDIAN_CUISINE, "Tender chicken in a rich, creamy tomato-based curry", 1, 11.99)));
        foodItemList.add(foodItemService.save(new FoodItem("Margherita Pizza", CategoryType.ITALIAN, "Classic pizza with tomato, mozzarella, and basil", 1, 10.99)));
        foodItemList.add(foodItemService.save(new FoodItem("Sushi Platter", CategoryType.JAPANESE, "Assorted sushi rolls and sashimi", 6, 15.99)));
        foodMenu = new FoodMenu(restaurant, foodItemList);
        foodMenuService.saveMenu(foodMenu);

        restaurant = new Restaurant("Gourmet Oasis");
        address = new Address("210 Maple Lane", "Bangalore", "Sector 14", 6, LocalTime.of(8, 0), LocalTime.of(20, 00));
        restaurant.setAddress(address);
        foodItemList = new ArrayList<>();
        foodItemList.add(foodItemService.save(new FoodItem("Bibimbap", CategoryType.KOREAN, "Mixed rice with vegetables, meat, and spicy gochujang sauce", 1, 12.49)));
        foodItemList.add(foodItemService.save(new FoodItem("Chicken Curry", CategoryType.INDIAN_CUISINE, "Spicy chicken curry with aromatic spices", 1, 10.99)));
        foodItemList.add(foodItemService.save(new FoodItem("Caprese Salad", CategoryType.ITALIAN, "Fresh mozzarella, tomatoes, and basil drizzled with balsamic glaze", 1, 8.99)));
        foodItemList.add(foodItemService.save(new FoodItem("Miso Soup", CategoryType.JAPANESE, "Traditional Japanese soup with tofu, seaweed, and green onions", 1, 6.99)));

        foodMenu = new FoodMenu(restaurant, foodItemList);
        foodMenuService.saveMenu(foodMenu);

        restaurant = new Restaurant("Mango Bistro");
        address = new Address("654 Cedar Street", "Bangalore", "Sector 22", 3, LocalTime.of(10, 0), LocalTime.of(22, 00));
        restaurant.setAddress(address);
        foodItemList = new ArrayList<>();
        foodItemList.add(foodItemService.save(new FoodItem("Butter Chicken", CategoryType.INDIAN_CUISINE, "Tender chicken in a rich, creamy tomato-based curry", 1, 11.99)));
        foodItemList.add(foodItemService.save(new FoodItem("Margherita Pizza", CategoryType.ITALIAN, "Classic pizza with tomato, mozzarella, and basil", 1, 10.99)));
        foodItemList.add(foodItemService.save(new FoodItem("Sushi Platter", CategoryType.JAPANESE, "Assorted sushi rolls and sashimi", 6, 15.99)));
        foodItemList.add(foodItemService.save(new FoodItem("Bibimbap", CategoryType.KOREAN, "Mixed rice with vegetables, meat, and spicy gochujang sauce", 1, 12.49)));
        foodItemList.add(foodItemService.save(new FoodItem("Chicken Curry", CategoryType.INDIAN_CUISINE, "Spicy chicken curry with aromatic spices", 1, 10.99)));
        foodItemList.add(foodItemService.save(new FoodItem("Caprese Salad", CategoryType.ITALIAN, "Fresh mozzarella, tomatoes, and basil drizzled with balsamic glaze", 1, 8.99)));
        foodItemList.add(foodItemService.save(new FoodItem("Miso Soup", CategoryType.JAPANESE, "Traditional Japanese soup with tofu, seaweed, and green onions", 1, 6.99)));
        foodItemList.add(foodItemService.save(new FoodItem("Bulgogi", CategoryType.KOREAN, "Marinated and grilled slices of beef", 1, 14.49)));
        foodItemList.add(foodItemService.save(new FoodItem("Paneer Tikka", CategoryType.INDIAN_CUISINE, "Spiced and grilled paneer cubes", 6, 11.99)));
        foodItemList.add(foodItemService.save(new FoodItem("Fettuccine Alfredo", CategoryType.ITALIAN, "Creamy pasta with fettuccine and Alfredo sauce", 1, 12.99)));
        foodItemList.add(foodItemService.save(new FoodItem("Sashimi Platter", CategoryType.JAPANESE, "Fresh slices of raw fish served with wasabi and soy sauce", 6, 18.99)));
        foodItemList.add(foodItemService.save(new FoodItem("Japchae", CategoryType.KOREAN, "Stir-fried glass noodles with vegetables", 1, 13.99)));
        foodItemList.add(foodItemService.save(new FoodItem("Chole Bhature", CategoryType.INDIAN_CUISINE, "Chickpea curry served with deep-fried bread", 1, 9.49)));
        foodItemList.add(foodItemService.save(new FoodItem("Tiramisu", CategoryType.ITALIAN, "Classic Italian dessert with coffee-soaked ladyfingers and mascarpone", 1, 6.99)));
        foodMenu = new FoodMenu(restaurant, foodItemList);
        foodMenuService.saveMenu(foodMenu);

        restaurant = new Restaurant("Sizzler's Delight");
        address = new Address("130 Oak Lane", "Bangalore", "Sector 14", 2, LocalTime.of(8, 30), LocalTime.of(19, 30));
        restaurant.setAddress(address);
        foodItemList = new ArrayList<>();
        foodItemList.add(foodItemService.save(new FoodItem("Fettuccine Alfredo", CategoryType.ITALIAN, "Creamy pasta with fettuccine and Alfredo sauce", 1, 12.99)));
        foodItemList.add(foodItemService.save(new FoodItem("Sashimi Platter", CategoryType.JAPANESE, "Fresh slices of raw fish served with wasabi and soy sauce", 6, 18.99)));
        foodItemList.add(foodItemService.save(new FoodItem("Japchae", CategoryType.KOREAN, "Stir-fried glass noodles with vegetables", 1, 13.99)));
        foodItemList.add(foodItemService.save(new FoodItem("Chole Bhature", CategoryType.INDIAN_CUISINE, "Chickpea curry served with deep-fried bread", 1, 9.49)));
        foodItemList.add(foodItemService.save(new FoodItem("Tiramisu", CategoryType.ITALIAN, "Classic Italian dessert with coffee-soaked ladyfingers and mascarpone", 1, 6.99)));
        foodMenu = new FoodMenu(restaurant, foodItemList);
        foodMenuService.saveMenu(foodMenu);

        restaurant = new Restaurant("Cafe Fusion");
        address = new Address("321 Pine Road", "Bangalore", "Sector 22", 8, LocalTime.of(10, 0), LocalTime.of(14, 0));
        restaurant.setAddress(address);
        foodItemList = new ArrayList<>();
        foodItemList.add(foodItemService.save(new FoodItem("Fettuccine Alfredo", CategoryType.ITALIAN, "Creamy pasta with fettuccine and Alfredo sauce", 1, 12.99)));
        foodItemList.add(foodItemService.save(new FoodItem("Sashimi Platter", CategoryType.JAPANESE, "Fresh slices of raw fish served with wasabi and soy sauce", 6, 18.99)));
        foodItemList.add(foodItemService.save(new FoodItem("Japchae", CategoryType.KOREAN, "Stir-fried glass noodles with vegetables", 1, 13.99)));
        foodItemList.add(foodItemService.save(new FoodItem("Chole Bhature", CategoryType.INDIAN_CUISINE, "Chickpea curry served with deep-fried bread", 1, 9.49)));
        foodItemList.add(foodItemService.save(new FoodItem("Tiramisu", CategoryType.ITALIAN, "Classic Italian dessert with coffee-soaked ladyfingers and mascarpone", 1, 6.99)));
        foodMenu = new FoodMenu(restaurant, foodItemList);
        foodMenuService.saveMenu(foodMenu);
    }

    @GetMapping(path = "/getAllRestaurants", produces = "application/json")
    public List<RestaurantDTO> getAllRestaurants() {
        log.info("Working from port " + port + " of restaurants-and-menu-service");
        return restaurantService.getAllRestaurants();
    }


    @GetMapping(path = "/getRestaurantById", produces = "application/json")
    public RestaurantDTO getRestaurantById(@RequestParam @NotNull @NotEmpty  String restaurantId) throws CustomException {
        log.info("Working from port " + port + " of restaurants-and-menu-service");
        return restaurantService.getRestaurantById(restaurantId);
    }

    @GetMapping(path = "/getRestaurantByName", produces = "application/json")
    public List<RestaurantDTO> getRestaurantByName(@RequestParam @NotNull @NotEmpty String restaurantName) throws CustomException {
        log.info("Working from port " + port + " of restaurants-and-menu-service");
        return restaurantService.getRestaurantByName(restaurantName);
    }

    @GetMapping(path = "/getRestaurantByAreaCode", produces = "application/json")
    public List<RestaurantDTO> getRestaurantByAreaCode(@RequestParam @NotNull @NotEmpty String areaCode) throws CustomException {
        log.info("Working from port " + port + " of restaurants-and-menu-service");
        return restaurantService.getRestaurantByAreaCode(areaCode);
    }

    @PostMapping(path = "/addRestaurant", produces = "application/json")
    public String addRestaurant(@RequestBody @Valid RestaurantModel restaurant) throws CustomException {
        log.info("Working from port " + port + " of restaurants-and-menu-service");
        return "Restaurant added successfully with id =" + restaurantService.addRestaurant(restaurant).getAddress().getAddressId();
    }

    @PostMapping(path = "/markFavourite", produces = "application/json")
    public SuccessResponse markRestaurantAsFavorite(@RequestParam @NotNull @NotEmpty String restaurantId,@RequestHeader("X-Authenticated-User") String username) throws CustomException {
        log.info("Working from port " + port + " of restaurants-and-menu-service");
        return restaurantService.markFavourite(restaurantId, username);
    }

    @PostMapping(path = "/delivery-order-notification", produces = "application/json")
    public void sendDeliveryNotification(@RequestBody DeliveryNotification notification) throws CustomException {
        log.info("Working from port " + port + " of restaurants-and-menu-service");
        restaurantService.sendDeliveryNotification(notification);
    }

    @PostMapping(path = "/dining-order-notification", produces = "application/json")
    public void sendDiningNotification(@RequestBody DiningNotification notification) throws CustomException {
        log.info("Working from port " + port + " of restaurants-and-menu-service");
        restaurantService.sendDiningNotification(notification);
    }


}
