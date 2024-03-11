package com.nagp.deliveryservice.repository;

import com.nagp.deliveryservice.entity.Orders;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Orders,String> {

    @Modifying
    @Query(value = "update orders set order_status=2 where order_id=?1",nativeQuery = true)
    Integer updateOrderStatus(String orderId);

}
