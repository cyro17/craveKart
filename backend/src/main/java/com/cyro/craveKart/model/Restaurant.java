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

    @Id // MongoDB uses @Id for primary key
    private ObjectId id; // MongoDB typically uses String for the ID (you can use Long if preferred)

    @DBRef // MongoDB reference for User (similar to @OneToOne in JPA)
    private User owner;

    private String name;
    private String description;
    private String cuisineType;

    @DBRef // MongoDB reference for Address (similar to @ManyToOne in JPA)
    private Address address;

    private ContactInformation contactInformation; // Embedded object for contact info

    private String openingHours;

    @DBRef // MongoDB reference for Review (similar to @OneToMany in JPA)
    private List<Review> reviews = new ArrayList<>();

    @DBRef // MongoDB reference for Order (similar to @OneToMany in JPA)
    private List<Order> orders = new ArrayList<>();

    private int numRating;

    private List<String> images; // List of images

    private LocalDateTime registrationDate;

    private boolean open;

    @DBRef // MongoDB reference for Food (similar to @OneToMany in JPA)
    private List<Food> foods = new ArrayList<>();
}
