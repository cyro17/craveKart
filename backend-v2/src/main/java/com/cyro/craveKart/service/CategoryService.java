package com.cyro.craveKart.service;

import java.util.List;

import com.cyro.craveKart.exceptions.RestaurantException;
import com.cyro.craveKart.model.Category;

public interface CategoryService {
	
	public Category createCategory (String name,Long userId) throws RestaurantException;
	public List<Category> findCategoryByRestaurantId(Long restaurantId) throws RestaurantException;
	public Category findCategoryById(Long id) throws RestaurantException;

}
