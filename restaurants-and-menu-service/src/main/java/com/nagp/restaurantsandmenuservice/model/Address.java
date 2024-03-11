package com.nagp.restaurantsandmenuservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Address {

    private String addressId;

    private String streetAddress;

    private String city;

    private String areaCode;

    public Address(String streetAddress, String city, String areaCode, Integer capacity, LocalTime openTime, LocalTime closeTime) {
        this.addressId = UUID.randomUUID().toString();
        this.streetAddress = streetAddress;
        this.city = city;
        this.areaCode = areaCode;
    }

}
