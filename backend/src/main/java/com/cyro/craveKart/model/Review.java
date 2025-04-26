package com.cyro.craveKart.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Document(collection = "reviews") // MongoDB collection name
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {

    @Id // MongoDB uses @Id for the primary key
    private ObjectId id; // MongoDB typically uses String for the ID (you can use Long if preferred)

    @DBRef // MongoDB reference for User (similar to @ManyToOne in JPA)
    private User customer;

    @DBRef // MongoDB reference for Restaurant (similar to @ManyToOne in JPA)
    private Restaurant restaurant;

    private String message;

    private double rating;

    private LocalDateTime createdAt; // MongoDB supports Date fields natively
}
