package com.cyro.craveKart.request;

import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class CreateIngredientRequest {
    private ObjectId restaurantId;
    private String name;
    private ObjectId ingredientCategoryId;
}
