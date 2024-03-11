package com.nagp.paymentservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDetails {

    @NotNull(message = "transactionId cannot be null")
    @NotEmpty(message = "transactionId cannot be empty")
    private String transactionId;

}