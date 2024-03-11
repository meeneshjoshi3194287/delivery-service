package com.nagp.deliveryservice.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;

@Data
@Builder
public class AddFoodItem {

    @NotBlank(message = "Item ID is required")
    private String itemId;

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity should be a positive integer")
    private Integer quantity;

}

