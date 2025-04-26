package com.cyro.craveKart.request;

import com.cyro.craveKart.model.Address;
import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class OrderRequest {

    private ObjectId restaurantId;

    private Address deliveryAddress;

}
