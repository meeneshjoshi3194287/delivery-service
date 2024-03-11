package com.nagp.restaurantsandmenuservice.dto;

import com.nagp.restaurantsandmenuservice.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestaurantDTO {

    private String restaurantId;
    private String restaurantName;
    private AddressDTO address;

}
