package com.cyro.cravekart.service;

import com.cyro.cravekart.exception.RestaurantException;
import com.cyro.cravekart.models.FoodCategory;
import com.cyro.cravekart.request.CreateCategoryRequest;
import com.cyro.cravekart.response.CreateCategoryResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FoodCategoryService {
  public CreateCategoryResponse createCategory (CreateCategoryRequest request) throws RestaurantException;
  public List<FoodCategory> findCategoryByRestaurantId(Long restaurantId) throws RestaurantException;
  public FoodCategory findCategoryById(Long id) throws RestaurantException;
  List<FoodCategory> getAllFoodCategories() throws RestaurantException;
}
