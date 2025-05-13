package com.cyro.craveKart.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "restaurants") // Specifies the MongoDB collection name
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Restaurant {

    @Id
    private ObjectId id;

    @DBRef
    private User owner;

    private String name;
    private String description;
    private String cuisineType;

    @DBRef
    private Address address;

    private ContactInformation contactInformation;

    private String openingHours;

    @DBRef
    private List<Review> reviews = new ArrayList<>();

    @DBRef
    private List<Order> orders = new ArrayList<>();

    private int numRating;

    private List<String> images;

    private LocalDateTime registrationDate;

    private boolean open;

    @DBRef
    private List<Food> foods = new ArrayList<>();
}
