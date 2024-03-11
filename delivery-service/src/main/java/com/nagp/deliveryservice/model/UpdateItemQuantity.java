package com.nagp.deliveryservice.model;

import com.nagp.deliveryservice.enums.UpdateItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateItemQuantity {

    private String itemId;
    private String update;
    private Integer quantity;

}
