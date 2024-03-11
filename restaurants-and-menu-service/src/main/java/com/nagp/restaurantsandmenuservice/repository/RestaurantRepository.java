package com.nagp.restaurantsandmenuservice.repository;

import com.nagp.restaurantsandmenuservice.entity.Restaurant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends CrudRepository<Restaurant, String> {

    Optional<List<Restaurant>> findByRestaurantName(String restaurantName);


}
