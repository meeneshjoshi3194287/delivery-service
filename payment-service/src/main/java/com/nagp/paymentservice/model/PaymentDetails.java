package com.nagp.paymentservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDetails {

    @NotNull
    @NotEmpty
    @Size(min = 16, max = 16, message = "Card Number should be of 16 digits.")
    @Pattern(regexp = "[0-9]{16}", message = "Card number should always contain numerical values.")
    private String cardNumber;
    @NotNull
    @NotEmpty
    @Size(min = 3, max = 3, message = "CVV should be of 3 digits.")
    @Pattern(regexp = "[0-9]{3}", message = "CVV should always contain numerical values.")
    private String cvv;

    private Double amount;
}
