package com.nagp.deliveryservice.entity;

import com.nagp.deliveryservice.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Orders {

    @Id
    private String orderId;

    @ElementCollection(targetClass = FoodCart.class)
    @CollectionTable(name = "order_cart", joinColumns = @JoinColumn(name = "order_id"))
    @Column(name = "cart_id")
    private List<FoodCart> cartList;

    private String transactionId;

    private Double orderAmount;

    private String userContactNo;

    private String userStreetAddress;

    private String userCity;

    private String userAreaCode;

    @Enumerated(EnumType.ORDINAL)
    private OrderStatus orderStatus;

    public Orders(List<FoodCart> foodCartList,String transactionId,Double orderAmount,String userContactNo,String userStreetAddress,String userAreaCode,String userCity,OrderStatus orderStatus){
        this.orderId= UUID.randomUUID().toString();
        this.cartList=foodCartList;
        this.transactionId=transactionId;
        this.orderAmount=orderAmount;
        this.userContactNo=userContactNo;
        this.userAreaCode=userAreaCode;
        this.userCity=userCity;
        this.userStreetAddress=userStreetAddress;
        this.orderStatus=orderStatus;
    }

}
