package com.cyro.craveKart.service;

import java.util.List;

import com.cyro.craveKart.exceptions.FoodException;
import com.cyro.craveKart.exceptions.RestaurantException;
import com.cyro.craveKart.model.Category;
import com.cyro.craveKart.model.Food;
import com.cyro.craveKart.model.Restaurant;
import com.cyro.craveKart.request.CreateFoodRequest;

public interface FoodService {

	public Food createFood(CreateFoodRequest req,Category category,
						   Restaurant restaurant) throws FoodException, RestaurantException;

	void deleteFood(Long foodId) throws FoodException;
	
	public List<Food> getRestaurantsFood(Long restaurantId,
			boolean isVegetarian, boolean isNonveg, boolean isSeasonal,String foodCategory) throws FoodException;
	
	public List<Food> searchFood(String keyword);
	
	public Food findFoodById(Long foodId) throws FoodException;

	public Food updateAvailibilityStatus(Long foodId) throws FoodException;
}
