package com.nagp.deliveryservice.repository;

import com.nagp.deliveryservice.entity.FoodCart;
import com.nagp.deliveryservice.enums.OrderStatus;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FoodCartRepository extends CrudRepository<FoodCart,String> {


    Optional<FoodCart> findByUserNameAndItemIdAndOrderStatus(String username,String itemId,OrderStatus status);

    @Modifying
    @Query(value = "update food_cart set quantity = quantity+?3 where item_id=?2 and user_name=?1",nativeQuery = true)
    Integer updateCart(String userName,String itemId,Integer quantity);

    @Modifying
    @Query(value = "update food_cart set order_status=2 where cart_id=?1",nativeQuery = true)
    Integer updateCartStatus(String cartId);

    @Modifying
    @Query(value = "update food_cart set order_status=3 where item_id=?1",nativeQuery = true)
    Integer removeFromCart(String itemId);

    @Query
    Optional<List<FoodCart>> findByUserNameAndOrderStatus(String username, OrderStatus orderStatus);
}
