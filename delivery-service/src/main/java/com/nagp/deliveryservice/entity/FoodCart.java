package com.nagp.deliveryservice.entity;
import com.nagp.deliveryservice.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodCart {

    @Id
    private String cartId;

    private String userName;

    @Enumerated(EnumType.ORDINAL)
    private OrderStatus orderStatus;

    private String itemId;

    private Integer quantity;

    private Double price;

    public FoodCart(String  userName,String itemId,Integer quantity,OrderStatus status,Double price){
        this.cartId= UUID.randomUUID().toString();
        this.userName=userName;
        this.orderStatus=status;
        this.itemId=itemId;
        this.quantity=quantity;
        this.price=price;
    }

}
