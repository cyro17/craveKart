package com.cyro.craveKart.dto;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import lombok.Data;
import org.bson.types.ObjectId;
import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "restaurants")
public class RestaurantDTO {

    private String title;

    @Field("images")
    private List<String> images = new ArrayList<>();

    private String description;

    @Field("_id")
    private ObjectId id;
}
