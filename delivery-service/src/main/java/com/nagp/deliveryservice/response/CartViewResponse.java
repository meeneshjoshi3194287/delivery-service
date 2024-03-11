package com.nagp.deliveryservice.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartViewResponse {

    private String numberOfItems;

    private String totalPrice;

    private List<CartData> cartDataList;

}
