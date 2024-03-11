package com.nagp.restaurantsandmenuservice.model;

import com.nagp.restaurantsandmenuservice.enums.UpdateItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateItemQuantity {

    @NotNull
    @NotEmpty
    private String itemId;
    private UpdateItem update;
    @NotNull
    @Min(1)
    private Integer quantity;

}
