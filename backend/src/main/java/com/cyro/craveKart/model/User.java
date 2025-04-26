package com.cyro.craveKart.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.cyro.craveKart.dto.RestaurantDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users") // MongoDB collection name
@Data
@NoArgsConstructor
public class User {

	@Id // MongoDB uses @Id for the primary key
	private ObjectId id; // MongoDB typically uses String for the ID (you can use Long if preferred)

	private String fullName;
	private String email;

	private String password; // Keep the password as a regular field (use @JsonProperty for sensitive data)

	private USER_ROLE role; // Assume this is an enum for the user's role

	@DBRef // MongoDB reference for Order (similar to @OneToMany in JPA)
	private List<Order> orders = new ArrayList<>();

	private List<RestaurantDTO> favorites = new ArrayList<>(); // Embedded list of RestaurantDTO objects

	@DBRef // MongoDB reference for Address (similar to @OneToMany in JPA)
	private List<Address> addresses = new ArrayList<>();

	private String status;
}
