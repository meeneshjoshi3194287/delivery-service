package com.nagp.restaurantsandmenuservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalTime;

@Data
@AllArgsConstructor
public class RestaurantModel {

    @NotBlank(message = "Restaurant name must not be blank")
    private String restaurantName;

    @Valid
    @NotNull
    private AddressModel address;

    @Min(value = 1, message = "Capacity must be at least 1")
    @Positive(message = "Capacity must be a positive number")
    private Integer capacity;

    @NotNull(message = "Open time must not be null")
    private LocalTime openTime;

    @NotNull(message = "Close time must not be null")
    private LocalTime closeTime;

}