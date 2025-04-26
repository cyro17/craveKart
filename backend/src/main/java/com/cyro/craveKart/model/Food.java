package com.cyro.craveKart.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "foods") // Specifies the MongoDB collection name
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Food {

    @Id // MongoDB uses @Id for the primary key
    private ObjectId id; // MongoDB typically uses String for the ID (you can use Long if needed)

    private String name;
    private String description;
    private Long price;

    @DBRef // MongoDB reference for Category (similar to @ManyToOne in JPA)
    private Category foodCategory;

    private List<String> images = new ArrayList<>(); // MongoDB stores lists directly

    private boolean available;

    @DBRef // MongoDB reference for Restaurant (similar to @ManyToOne in JPA)
    private Restaurant restaurant;

    private boolean isVegetarian;
    private boolean isSeasonal;

    @DBRef // MongoDB reference for IngredientsItem (similar to @ManyToMany in JPA)
    private List<IngredientsItem> ingredients = new ArrayList<>();

    private Date creationDate; // MongoDB stores Date objects as is
}
