package com.cyro.craveKart.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Document(collection = "orderItems") // Specifies the MongoDB collection name
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderItem {

    @Id // MongoDB uses @Id for primary key
    private ObjectId id; // MongoDB typically uses String for the ID (you can use Long if preferred)

    @DBRef // MongoDB reference for Food (similar to @ManyToOne in JPA)
    private Food food;

    private int quantity;
    private Long totalPrice;

    private List<String> ingredients;
}
