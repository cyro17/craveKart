package com.cyro.craveKart.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Document(collection = "payments") // Specifies the MongoDB collection name
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

    @Id // MongoDB uses @Id for primary key
    private ObjectId id; // MongoDB typically uses String for the ID (you can use Long if preferred)

    private Long orderId;
    private String paymentMethod;
    private String paymentStatus;
    private double totalAmount;

    private Date createdAt; // MongoDB stores Date objects as is
}
