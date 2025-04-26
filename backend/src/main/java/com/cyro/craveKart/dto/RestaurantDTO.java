package com.cyro.craveKart.dto;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import lombok.Data;
import org.bson.types.ObjectId;
import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "restaurants")  // Maps this class to the 'restaurants' collection in MongoDB
public class RestaurantDTO {

    private String title;

    @Field("images")  // Specify the field name in MongoDB, if needed (optional)
    private List<String> images = new ArrayList<>();

    private String description;

    @Field("_id")  // MongoDB uses _id by default as the primary key
    private ObjectId id;

}
