package com.cyro.craveKart.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "events")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Events {

	@Id
	private ObjectId id;

	private String image;

	private String startedAt;

	private String endsAt;

	private String name;

	@DBRef
	private Restaurant restaurant;

	private String location;
}
