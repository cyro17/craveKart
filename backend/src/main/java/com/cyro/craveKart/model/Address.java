package com.cyro.craveKart.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "addresses") // This defines the MongoDB collection name
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Address {
	@Id // MongoDB's way of marking the primary key
	private ObjectId id; // MongoDB uses String for the ID by default

	private String fullName;
	private String streetAddress;
	private String city;
	private String state;
	private String postalCode;
	private String country;
}