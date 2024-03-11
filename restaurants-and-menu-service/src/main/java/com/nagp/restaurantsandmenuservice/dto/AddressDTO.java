package com.nagp.restaurantsandmenuservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDTO {

    private String streetAddress;

    private String city;

    private String areaCode;

    private Integer diningCapacity;

    private LocalTime openTime;

    private LocalTime closeTime;

}
