package com.cyro.craveKart.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Document(collection = "orderItems")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderItem {

    @Id
    private ObjectId id;
    @DBRef
    private Food food;

    private int quantity;
    private Long totalPrice;

    private List<String> ingredients;
}
