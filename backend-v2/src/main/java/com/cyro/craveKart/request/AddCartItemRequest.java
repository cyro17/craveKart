package com.cyro.craveKart.request;

import java.util.List;

import com.cyro.craveKart.model.Food;

import lombok.Data;

@Data
public class AddCartItemRequest {
	
	private Long menuItemId;
	private int quantity;
	private List<String> ingredients;

}
