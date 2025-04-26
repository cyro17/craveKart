package com.cyro.craveKart.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "ingredientsItems") // Specifies the MongoDB collection name
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngredientsItem {

	@Id // MongoDB uses @Id for primary key
	private ObjectId id; // MongoDB typically uses String for the ID (you can use Long if preferred)

	private String name;

	@DBRef // MongoDB reference for IngredientCategory (similar to @ManyToOne)
	private IngredientCategory category;

	@DBRef // MongoDB reference for Restaurant (similar to @ManyToOne)
	private Restaurant restaurant;

	private boolean inStoke = true;
}
