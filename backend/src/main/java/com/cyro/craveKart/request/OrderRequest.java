package com.cyro.craveKart.request;

import com.cyro.craveKart.model.Address;
import lombok.Data;

@Data
public class OrderRequest {

    private Long restaurantId;

    private Address deliveryAddress;

}
