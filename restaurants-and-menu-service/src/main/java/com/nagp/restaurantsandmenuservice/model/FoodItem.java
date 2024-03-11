package com.nagp.restaurantsandmenuservice.model;

import com.nagp.restaurantsandmenuservice.enums.CategoryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class FoodItem {

    private String itemId;

    private String itemName;

    private CategoryType category;

    private double price;

    private String description;

    private Integer quantity;
}
