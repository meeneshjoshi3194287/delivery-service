package com.nagp.restaurantsandmenuservice.dto;

import com.nagp.restaurantsandmenuservice.enums.CategoryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FoodItemDTO {
    private String itemId;
    private String itemName;
    private CategoryType category;
    private double price;
    private String description;
    private Integer quantity;
}
