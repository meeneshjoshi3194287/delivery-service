package com.nagp.restaurantsandmenuservice.repository;

import com.nagp.restaurantsandmenuservice.entity.FoodMenu;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FoodMenuRepository extends CrudRepository<FoodMenu, String> {

    @Query("SELECT fm FROM FoodMenu fm WHERE fm.restaurant.id = :restaurantId")
    Optional<FoodMenu> getMenuByRestaurantId(String restaurantId);

}
