package com.cyro.craveKart.request;

import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class UpdateCartItemRequest {
    private ObjectId cartItemId;
    private int quantity;
}
