package com.nagp.restaurantsandmenuservice.model;

import com.nagp.restaurantsandmenuservice.enums.CategoryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddItem {

    @NotBlank(message = "Item name must not be blank")
    @Size(min = 1, max = 255, message = "Item name length must be between 1 and 255 characters")
    private String itemName;

    @NotNull(message = "Category must not be null")
    private CategoryType category;

    @NotNull(message = "Price must not be null")
    @Positive(message = "Price must be a positive number")
    private Double price;

    @Size(max = 1000, message = "Description length must be less than or equal to 1000 characters")
    private String description;

    @NotNull(message = "Quantity must not be null")
    @Positive(message = "Quantity must be a positive number")
    private Integer quantity;

}
