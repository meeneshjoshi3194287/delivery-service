package com.nagp.deliveryservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
public class ConfirmOrder {

    @NotBlank(message = "Street address is required")
    private String streetAddress;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "Area code is required")
    private String areaCode;

    @NotBlank(message = "Contact no is required")
    @Pattern(regexp = "\\d{10}", message = "Contact number should be 10 digits")
    private String contactNo;
}
