package com.nagp.deliveryservice.model;

import com.nagp.deliveryservice.enums.CategoryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class FoodItem{

    private String itemId;

    private String itemName;

    private CategoryType category;

    private double price;

    private String description;

    private Integer quantity;
}
