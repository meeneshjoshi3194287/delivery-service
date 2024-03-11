package com.nagp.restaurantsandmenuservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class AddressModel {


    @NotBlank(message = "Street address must not be blank")
    private String streetAddress;

    @NotBlank(message = "City must not be blank")
    private String city;

    @NotBlank(message = "Area code must not be blank")
    private String areaCode;
}
