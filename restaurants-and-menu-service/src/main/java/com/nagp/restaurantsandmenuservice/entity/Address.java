package com.nagp.restaurantsandmenuservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Address {

    @Id
    private String addressId;

    private String streetAddress;

    private String city;

    private String areaCode;

    private Integer diningCapacity;

    private LocalTime openTime;

    private LocalTime closeTime;

    public Address(String streetAddress, String city, String areaCode, Integer capacity, LocalTime openTime, LocalTime closeTime) {
        this.addressId = UUID.randomUUID().toString();
        this.streetAddress = streetAddress;
        this.city = city;
        this.areaCode = areaCode;
        this.diningCapacity = capacity;
        this.openTime = openTime;
        this.closeTime = closeTime;
    }

}
