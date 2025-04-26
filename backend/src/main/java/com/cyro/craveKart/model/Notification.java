package com.cyro.craveKart.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

import java.util.Date;

@Document(collection = "notifications") // Specifies the MongoDB collection name
@Data
public class Notification {

    @Id // MongoDB uses @Id for primary key
    private ObjectId id; // MongoDB typically uses String for the ID (you can use Long if preferred)

    @DBRef // MongoDB reference for User (similar to @ManyToOne in JPA)
    private User customer;

    @DBRef // MongoDB reference for Restaurant (similar to @ManyToOne in JPA)
    private Restaurant restaurant;

    private String message;

    private Date sentAt; // MongoDB stores Date objects as is

    private boolean readStatus;
}
