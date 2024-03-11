package com.nagp.restaurantsandmenuservice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Restaurant {

    @Id
    private String id;

    private String restaurantName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "restaurant_id")
    @JsonFormat
    private Address address;

    public Restaurant(String restaurantName) {
        this.id = UUID.randomUUID().toString();
        this.restaurantName = restaurantName;
    }

    public Restaurant(String restaurantName, Address address) {
        this.id = UUID.randomUUID().toString();
        this.restaurantName = restaurantName;
        this.address = address;
    }

}