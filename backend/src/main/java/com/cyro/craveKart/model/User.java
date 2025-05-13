package com.cyro.craveKart.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
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

	@Id
	private ObjectId id;

	private String fullName;

	@Indexed(unique = true, sparse = true)
	private String email;

	private String password;

	private USER_ROLE role;

	@DBRef
	private List<Order> orders = new ArrayList<>();

	private List<RestaurantDTO> favorites = new ArrayList<>();

	@DBRef
	private List<Address> addresses = new ArrayList<>();

	private String status;
}
