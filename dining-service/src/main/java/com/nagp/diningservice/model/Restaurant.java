package com.nagp.diningservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Restaurant {

    private String restaurantId;
    private String restaurantName;
    private Address address;

}
