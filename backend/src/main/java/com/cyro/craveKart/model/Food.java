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

@Document(collection = "foods")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Food {

    @Id
    private ObjectId id;

    private String name;
    private String description;
    private Long price;

    @DBRef
    private Category foodCategory;

    private List<String> images = new ArrayList<>();

    private boolean available;

    @DBRef
    private Restaurant restaurant;

    private boolean isVegetarian;
    private boolean isSeasonal;

    @DBRef
    private List<IngredientsItem> ingredients = new ArrayList<>();

    private Date creationDate;
}
