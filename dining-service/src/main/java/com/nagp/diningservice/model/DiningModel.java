package com.nagp.diningservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.Valid;
import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiningModel {

    @NotBlank(message = "restaurantId is required")
    private String restaurantId;

    @NotNull(message = "Date is required")
    private String date;

    @Valid
    @NotNull(message = "bookingSlot is required")
    private BookingSlot bookingSlot;

    @Min(value = 1, message = "Number of guests should be at least 1")
    @NotNull(message = "Number of guests is required")
    private Integer noOfGuests;

    @NotBlank(message = "User email is required")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Invalid email format")
    private String userEMail;

    @NotBlank(message = "User contact number is required")
    @Pattern(regexp = "\\d{10}", message = "Contact number should be 10 digits")
    private String userContactNo;

}
