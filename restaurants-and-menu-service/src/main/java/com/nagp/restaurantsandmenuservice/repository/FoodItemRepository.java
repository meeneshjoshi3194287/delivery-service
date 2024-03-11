package com.nagp.restaurantsandmenuservice.repository;

import com.nagp.restaurantsandmenuservice.entity.FoodItem;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodItemRepository extends CrudRepository<FoodItem, String> {

    @Modifying
    @Query(value = "update food_item set quantity = quantity + ?2 where item_id=?1",nativeQuery = true)
    Integer addItemQuantity(String itemId,Integer quantity);

    @Modifying
    @Query(value = "update food_item set quantity = quantity - ?2 where item_id=?1",nativeQuery = true)
    Integer removeItemQuantity(String itemId,Integer quantity);

}
