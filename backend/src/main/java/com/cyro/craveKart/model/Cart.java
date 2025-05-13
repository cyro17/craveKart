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

@Document(collection = "carts")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Cart {

	@Id
	private ObjectId id;

	@DBRef
	private User customer;

	private List<CartItem> items = new ArrayList<>();

	private Double total;
}
