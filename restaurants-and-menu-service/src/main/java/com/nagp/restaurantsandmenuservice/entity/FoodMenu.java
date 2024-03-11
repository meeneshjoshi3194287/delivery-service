package com.nagp.restaurantsandmenuservice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

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
public class FoodMenu {

    @Id
    private String menuId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "restaurant_id")
    @JsonFormat
    private Restaurant restaurant;


    @ElementCollection(targetClass = FoodItem.class)
    @CollectionTable(name = "menu_food_items", joinColumns = @JoinColumn(name = "menu_id"))
    @Column(name = "item_id")
    @JsonFormat
    private List<FoodItem> foodItems;

    public FoodMenu(Restaurant restaurant, List<FoodItem> foodItemList) {
        this.menuId = UUID.randomUUID().toString();
        this.restaurant = restaurant;
        this.foodItems = foodItemList;
    }
}