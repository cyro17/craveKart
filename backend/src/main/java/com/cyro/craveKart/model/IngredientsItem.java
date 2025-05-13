package com.cyro.craveKart.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "ingredientsItems")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngredientsItem {

	@Id
	private ObjectId id;

	private String name;

	@DBRef
	private IngredientCategory category;

	@DBRef
	private Restaurant restaurant;

	private boolean inStock = true;
}
