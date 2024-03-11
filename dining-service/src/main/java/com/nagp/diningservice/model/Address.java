package com.nagp.diningservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Address {

    private String streetAddress;
    private String city;
    private String areaCode;
    private Integer diningCapacity;
    private LocalTime openTime;
    private LocalTime closeTime;

}