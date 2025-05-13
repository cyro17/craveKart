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

@Document(collection = "ingredients category")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngredientCategory {

	@Id
	private ObjectId id;

	private String name;

	@DBRef
	private Restaurant restaurant;

	@DBRef
	private List<IngredientsItem> ingredients = new ArrayList<>();
}
