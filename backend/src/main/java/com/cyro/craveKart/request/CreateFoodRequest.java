package com.cyro.craveKart.request;

import com.cyro.craveKart.model.Category;
import com.cyro.craveKart.model.IngredientsItem;
import lombok.Data;
import org.bson.types.ObjectId;

import java.util.List;

@Data
public class CreateFoodRequest {
    private String name;
    private String description;
    private Long price;


    private Category category;
    private List<String> images;


    private ObjectId restaurantId;

    private boolean vegetarian;
    private boolean seasonal;


    private List<IngredientsItem> ingredients;
}
