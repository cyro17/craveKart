package com.cyro.craveKart.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "events") // Specifies the MongoDB collection name
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Events {

	@Id // MongoDB uses @Id for the primary key
	private ObjectId id; // MongoDB typically uses String for the ID (you can use Long if needed)

	private String image;

	private String startedAt; // You can choose to store this as a String or Date. I kept it as String here.

	private String endsAt; // Same as startedAt, you can store it as String or Date.

	private String name;

	@DBRef // MongoDB reference for Restaurant (similar to @ManyToOne in JPA)
	private Restaurant restaurant;

	private String location;
}
