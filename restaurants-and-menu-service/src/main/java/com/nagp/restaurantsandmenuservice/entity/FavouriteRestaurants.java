package com.nagp.restaurantsandmenuservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavouriteRestaurants {

    @Id
    private String fav_id;
    private String username;
    private String restaurantId;

    public FavouriteRestaurants(String username, String restaurantId) {
        this.fav_id = UUID.randomUUID().toString();
        this.username = username;
        this.restaurantId = restaurantId;
    }
}