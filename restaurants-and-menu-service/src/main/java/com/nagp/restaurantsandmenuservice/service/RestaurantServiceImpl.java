package com.nagp.restaurantsandmenuservice.service;

import com.nagp.restaurantsandmenuservice.dto.RestaurantDTO;
import com.nagp.restaurantsandmenuservice.entity.Address;
import com.nagp.restaurantsandmenuservice.entity.FavouriteRestaurants;
import com.nagp.restaurantsandmenuservice.entity.Restaurant;
import com.nagp.restaurantsandmenuservice.exception.CustomException;
import com.nagp.restaurantsandmenuservice.model.DeliveryNotification;
import com.nagp.restaurantsandmenuservice.model.DiningNotification;
import com.nagp.restaurantsandmenuservice.model.RestaurantModel;
import com.nagp.restaurantsandmenuservice.repository.FavouriteRepository;
import com.nagp.restaurantsandmenuservice.repository.RestaurantRepository;
import com.nagp.restaurantsandmenuservice.response.SuccessResponse;
import com.nagp.restaurantsandmenuservice.util.FoodMenuConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private RestaurantRepository restaurantRepository;
    private FavouriteRepository favouriteRepository;

    private static final Logger log = LoggerFactory.getLogger(RestaurantServiceImpl.class);


    @Autowired
    public RestaurantServiceImpl(RestaurantRepository restaurantRepository, FavouriteRepository favouriteRepository) {
        this.restaurantRepository = restaurantRepository;
        this.favouriteRepository = favouriteRepository;
    }

    public Restaurant saveRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    public List<RestaurantDTO> getAllRestaurants() {
        List<Restaurant> restaurantList = (List<Restaurant>) restaurantRepository.findAll();
        return restaurantList.stream().map(FoodMenuConverter::convertRestaurantToDTO).collect(Collectors.toList());
    }

    @Transactional
    public Restaurant addRestaurant(RestaurantModel restaurant) throws CustomException {
        if(restaurant.getCloseTime().isBefore(restaurant.getOpenTime())) throw new CustomException("Open time can't be after close time");
        Restaurant newRestaurant = Restaurant.builder().id(UUID.randomUUID().toString())
                .restaurantName(restaurant.getRestaurantName())
                .address(Address.builder().addressId(UUID.randomUUID().toString())
                        .streetAddress(restaurant.getAddress().getStreetAddress())
                        .city(restaurant.getAddress().getCity())
                        .diningCapacity(restaurant.getCapacity())
                        .openTime(restaurant.getOpenTime())
                        .closeTime(restaurant.getCloseTime())
                        .areaCode(restaurant.getAddress().getAreaCode()).build()).build();
        return restaurantRepository.save(newRestaurant);
    }

    public String deleteRestaurant(String restaurantId) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);
        if (restaurant.isPresent()) {
            restaurantRepository.delete(restaurant.get());
            return "Restaurant successfully deleted with id= " + restaurantId;
        } else return "Restaurant id doesn't exist";
    }


    public RestaurantDTO getRestaurantById(String restaurantId) throws CustomException {
        Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);
        if (!restaurant.isPresent()) throw new CustomException("Please provide a valid restaurant id");
        return FoodMenuConverter.convertRestaurantToDTO(restaurantRepository.findById(restaurantId).get());
    }

    public List<RestaurantDTO> getRestaurantByName(String restaurantName) throws CustomException {
        Optional<List<Restaurant>> restaurantList = restaurantRepository.findByRestaurantName(restaurantName);
        if (!restaurantList.isPresent()) throw new CustomException("Not able to find any restaurants with this name");
        return restaurantList.get().stream().map(FoodMenuConverter::convertRestaurantToDTO)
                .collect(Collectors.toList());
    }

    public List<RestaurantDTO> getRestaurantByAreaCode(String areaCode) throws CustomException {
        List<Restaurant> restaurantList = (List<Restaurant>) restaurantRepository.findAll();
        List<Restaurant> resultList = restaurantList.stream()
                .filter(restaurant -> restaurant.getAddress().getAreaCode().contains(areaCode))
                .collect(Collectors.toList());
        if (resultList.isEmpty()) {
            throw new CustomException("Not able to find any restaurants in this area");
        }
        return resultList.stream().map(FoodMenuConverter::convertRestaurantToDTO).collect(Collectors.toList());
    }

    @Transactional
    public SuccessResponse markFavourite(String restaurantId, String username) throws CustomException {
        Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);
        if (!restaurant.isPresent())
            throw new CustomException("RestaurantId doesn't exist , please add a valid restaurant");
        Optional<List<String>> userFavourites = favouriteRepository.findByUsername(username);
        if (userFavourites.isPresent()) {
            if (userFavourites.get().contains(restaurantId))
                throw new CustomException("This restaurant is already in your favourites list");
        }
        favouriteRepository.save(new FavouriteRestaurants(username, restaurantId));
        return SuccessResponse.builder().status("0").successMessage("Restaurant has been added to your favourites list").build();
    }

    public void sendDeliveryNotification(DeliveryNotification notification){
        log.info("====== User with username : "+notification.getUsername()+" has placed the below order ::");
        log.info("====== RestaurantName : " +notification.getRestaurant().getRestaurantName() + " =========");
        log.info("====== Restaurant Address : " + notification.getRestaurant().getAddress().getStreetAddress() + " =========");
        log.info("======                    : " + notification.getRestaurant().getAddress().getAreaCode() + " =========");
        log.info("======                    : " + notification.getRestaurant().getAddress().getCity() + " =========");
        log.info("====== Item  : " + notification.getFoodItem().getItemName() + " =========");
        log.info("====== Quantity  : " + notification.getQuantity() + " =========");
    }

    public void sendDiningNotification(DiningNotification notification){
        log.info("====== Hi !! User with username : " + notification.getUsername() + " has done the booking with following details : =====");
        log.info("====== Booking Id         :: " + notification.getDineBooking().getBookingId() + " =====");
        log.info("====== Booked for date    :: " + notification.getDineBooking().getDate() + " ======");
        log.info("====== Number of Guests   :: " + notification.getDineBooking().getNoOfGuests() + " ======");
        log.info("====== Booking Slot       :: " + notification.getDineBooking().getStartTime() + "-" + notification.getDineBooking().getEndTime() + " ======");
        log.info("====== Contact No         :: " + notification.getDineBooking().getUserContactNo() + " ======");
        log.info("====== Please find below the restaurant details. ======");
        Restaurant restaurant = restaurantRepository.findById(notification.getDineBooking().getRestaurantId()).get();
        log.info("====== Restaurant Name    :: " + restaurant.getRestaurantName() + " ======");
        log.info("====== Restaurant Address :: " + restaurant.getAddress().getStreetAddress() + " ======");
        log.info("====== City               :: " + restaurant.getAddress().getCity() + " ======");
        log.info("====== Area Code          :: " + restaurant.getAddress().getAreaCode() + " ======");
    }
}
