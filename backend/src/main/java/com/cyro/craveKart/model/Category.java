package com.cyro.craveKart.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "categories") // Specifies the MongoDB collection name
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id // MongoDB uses @Id for primary key
    private ObjectId id; // MongoDB IDs are typically Strings (not Long)

    private String name;

    @DBRef // MongoDB reference for restaurant (similar to @ManyToOne)
    @JsonIgnore
    private Restaurant restaurant;

}
