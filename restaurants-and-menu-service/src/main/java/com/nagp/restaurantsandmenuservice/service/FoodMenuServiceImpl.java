package com.nagp.restaurantsandmenuservice.service;


import com.nagp.restaurantsandmenuservice.dto.FoodItemDTO;
import com.nagp.restaurantsandmenuservice.dto.FoodMenuDTO;
import com.nagp.restaurantsandmenuservice.entity.FoodItem;
import com.nagp.restaurantsandmenuservice.entity.FoodMenu;
import com.nagp.restaurantsandmenuservice.entity.Restaurant;
import com.nagp.restaurantsandmenuservice.enums.CategoryType;
import com.nagp.restaurantsandmenuservice.exception.CustomException;
import com.nagp.restaurantsandmenuservice.model.AddItem;
import com.nagp.restaurantsandmenuservice.repository.*;
import com.nagp.restaurantsandmenuservice.response.FoodMenuMapping;
import com.nagp.restaurantsandmenuservice.response.SearchResponse;
import com.nagp.restaurantsandmenuservice.response.SuccessResponse;
import com.nagp.restaurantsandmenuservice.util.FoodMenuConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FoodMenuServiceImpl implements FoodMenuService {

    private FoodMenuRepository foodMenuRepository;

    private AddressRepository addressRepository;

    private FavouriteRepository favouriteRepository;

    private RestaurantRepository restaurantRepository;
    private FoodItemRepository foodItemRepository;

    @Autowired
    public FoodMenuServiceImpl(FoodMenuRepository foodMenuRepository, AddressRepository addressRepository,
                               FavouriteRepository favouriteRepository, RestaurantRepository restaurantRepository,
                               FoodItemRepository foodItemRepository) {
        this.foodMenuRepository = foodMenuRepository;
        this.addressRepository = addressRepository;
        this.favouriteRepository = favouriteRepository;
        this.restaurantRepository = restaurantRepository;
        this.foodItemRepository=foodItemRepository;
    }


    public FoodMenu saveMenu(FoodMenu foodMenu) {
        return foodMenuRepository.save(foodMenu);
    }

    public SearchResponse searchByUser(String areaCode, String foodItem, String restaurant, String username) {
        if (areaCode == null || foodItem == null || restaurant == null) return null;
        List<FoodMenu> resultList = (List<FoodMenu>) foodMenuRepository.findAll();
        boolean addFavouritesList = false;
        if (restaurant.trim().equals("") && foodItem.trim().equals("") && areaCode.trim().equals(""))
            addFavouritesList = true;
        if (!restaurant.trim().equals("")) {
            resultList = getFoodMenuByRestaurantName(restaurant, resultList);
        }
        if (!foodItem.trim().equals("")) {
            resultList = getFoodMenuByFoodItem(foodItem, resultList);
        }
        if (!areaCode.trim().equals("")) {
            resultList = getFoodMenuByAreaCode(areaCode, resultList);
        }
        return mapResultToSearchResponse(resultList, addFavouritesList, username);
    }

    private SearchResponse mapResultToSearchResponse(List<FoodMenu> resultList, boolean addFavouritesList, String username) {
        List<FoodMenuDTO> foodMenuDTOList = FoodMenuConverter.convertToDTOList(resultList);
        if (addFavouritesList) {
            Optional<List<String>> favouriteIds = favouriteRepository.findByUsername(username);
            List<Restaurant> favourites = favouriteIds.get().stream().map(i -> restaurantRepository.findById(i).get()).collect(Collectors.toList());
            return SearchResponse.builder()
                    .favourites(favourites.stream().map(FoodMenuConverter::convertRestaurantToDTO).collect(Collectors.toList()))
                    .foodMenuByRestaurants(foodMenuDTOList)
                    .categorizedFoodMenu(categorizeFoodItems(foodMenuDTOList)).build();
        }
        return SearchResponse.builder().foodMenuByRestaurants(foodMenuDTOList).categorizedFoodMenu(categorizeFoodItems(foodMenuDTOList)).build();
    }


    private Map<CategoryType, Map<String, List<FoodItemDTO>>> categorizeFoodItems(List<FoodMenuDTO> foodMenuList) {
        return foodMenuList.stream()
                .flatMap(foodMenu -> foodMenu.getFoodItems().stream()
                        .map(foodItem -> new FoodMenuMapping(foodItem.getCategory(), foodMenu.getRestaurant().getRestaurantName(), foodItem)))
                .collect(Collectors.groupingBy(
                        FoodMenuMapping::getCategory,
                        Collectors.groupingBy(FoodMenuMapping::getRestaurant, Collectors.mapping(FoodMenuMapping::getFoodItem, Collectors.toList()))
                ));
    }

    private static Map<CategoryType, List<FoodItem>> createInnerMap(String restaurantName, List<FoodItem> foodItems) {
        return foodItems.stream()
                .collect(Collectors.groupingBy(
                        FoodItem::getCategory,
                        Collectors.toList()
                ));
    }


    private List<FoodMenu> getFoodMenuByRestaurantName(String restaurant, List<FoodMenu> resultList) {
        return resultList.stream()
                .filter(i -> i.getRestaurant().getRestaurantName().trim().toLowerCase().contains(restaurant.trim().toLowerCase()))
                .collect(Collectors.toList());
    }

    private List<FoodMenu> getFoodMenuByAreaCode(String areaCode, List<FoodMenu> resultList) {
        return resultList.stream()
                .filter(foodMenu -> foodMenu.getRestaurant().getAddress().getAreaCode().trim().toLowerCase().contains(areaCode.trim().toLowerCase()))
                .collect(Collectors.toList());
    }


    private List<FoodMenu> getFoodMenuByFoodItem(String foodItem, List<FoodMenu> resultList) {
        return resultList.stream()
                .map(foodMenu -> {
                    // Filter the foodItems list to include only the target FoodItem
                    List<FoodItem> filteredFoodItems = foodMenu.getFoodItems().stream()
                            .filter(i -> i.getItemName().trim().toLowerCase().contains(foodItem.trim().toLowerCase()))
                            .collect(Collectors.toList());

                    // Create a new FoodMenu with the filtered foodItems list
                    FoodMenu filteredFoodMenu = new FoodMenu();
                    filteredFoodMenu.setMenuId(foodMenu.getMenuId());
                    filteredFoodMenu.setRestaurant(foodMenu.getRestaurant());
                    filteredFoodMenu.setFoodItems(filteredFoodItems);

                    return filteredFoodMenu;
                })
                .filter(foodMenu -> !foodMenu.getFoodItems().isEmpty()) // Remove FoodMenu with empty foodItems list
                .collect(Collectors.toList());
    }


    public FoodMenuDTO getMenuByRestaurantId(String restaurantId) throws CustomException {
        if (!restaurantRepository.findById(restaurantId).isPresent())
            throw new CustomException("Restaurant id doesn't exist");
        Optional<FoodMenu> foodMenu = foodMenuRepository.getMenuByRestaurantId(restaurantId);
        if (!foodMenu.isPresent()) throw new CustomException("Restaurant has not added anything yet to this menu list");
        return FoodMenuConverter.convertToDTO(foodMenu.get());
    }

    @Transactional
    public SuccessResponse addItemsToRestaurantMenu(AddItem addItem, String restaurantId) throws  CustomException{
        Optional<Restaurant> restaurant=restaurantRepository.findById(restaurantId);
        if(!restaurant.isPresent()) throw new CustomException("Restaurant id doesn't exist");
        if(!(addItem.getCategory().toString().equals("ITALIAN") ||
                addItem.getCategory().toString().equals("INDIAN_CUISINE") ||
                addItem.getCategory().toString().equals("JAPANESE") ||
                addItem.getCategory().toString().equals("KOREAN")) )
            throw new CustomException("Category can only be INDIAN_CUISINE,KOREAN,JAPANESE or ITALIAN");
        Optional<FoodMenu> foodMenu = foodMenuRepository.getMenuByRestaurantId(restaurantId);
        if(foodMenu.get().getFoodItems().stream().map(i->i.getItemName()).collect(Collectors.toList()).contains(addItem.getItemName()))
            throw new CustomException("Item already there in restaurant's menu");
        FoodItem foodItem= new FoodItem(addItem.getItemName(), addItem.getCategory(),addItem.getDescription(), addItem.getQuantity(),addItem.getPrice());
        foodItemRepository.save(foodItem);
        if(!foodMenu.isPresent()){
            List<FoodItem> items= new ArrayList<>();
            items.add(foodItem);
            FoodMenu newMenu= new FoodMenu(restaurant.get(),items);
            foodMenuRepository.save(newMenu);
        }
        else{
            List<FoodItem> foodItems=foodMenu.get().getFoodItems();
            foodItems.add(foodItem);
            foodMenuRepository.save(foodMenu.get());
        }
        return SuccessResponse.builder().status("0").successMessage("Item added with item Id :"+foodItem.getItemId()).build();
    }

}
