package com.nagp.restaurantsandmenuservice.entity;

import com.nagp.restaurantsandmenuservice.enums.CategoryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FoodItem implements Serializable {

    @Id
    private String itemId;

    private String itemName;

    private CategoryType category;

    private double price;

    private String description;

    private Integer quantity;

    public FoodItem(String itemName, CategoryType category, String description, Integer quantity, double price) {
        this.itemId = UUID.randomUUID().toString();
        this.itemName = itemName;
        this.category = category;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
    }
}
