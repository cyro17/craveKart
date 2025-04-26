package com.cyro.craveKart.request;

import lombok.Data;
import org.bson.types.ObjectId;
import java.util.List;

@Data
public class AddCartItemRequest {
    private ObjectId menuItemId;
    private int quantity;
    private List<String> ingredients;
}
