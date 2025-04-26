package com.cyro.craveKart.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "carts") // This specifies the MongoDB collection name
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Cart {

	@Id // MongoDB uses @Id for primary key
	private ObjectId id; // MongoDB IDs are typically Strings (not Long)

	@DBRef // MongoDB reference for customer (similar to @OneToOne)
	private User customer;

	private List<CartItem> items = new ArrayList<>(); // MongoDB stores embedded objects

	private Long total;
}
