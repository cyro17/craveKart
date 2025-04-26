package com.cyro.craveKart.model;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "ingredientCategories") // Specifies the MongoDB collection name
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngredientCategory {

	@Id // MongoDB uses @Id for primary key
	private ObjectId id; // MongoDB typically uses String for the ID (you can use Long if needed)

	private String name;

	@DBRef // MongoDB reference for Restaurant (similar to @ManyToOne)
	private Restaurant restaurant;

	@DBRef // MongoDB reference for IngredientsItem (similar to @OneToMany)
	private List<IngredientsItem> ingredients = new ArrayList<>();
}
