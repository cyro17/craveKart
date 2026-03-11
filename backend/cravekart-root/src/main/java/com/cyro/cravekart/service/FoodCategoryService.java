package com.cyro.cravekart.service;

import com.cyro.cravekart.exception.RestaurantException;
import com.cyro.cravekart.models.FoodCategory;
import com.cyro.cravekart.request.CreateCategoryRequest;
import com.cyro.cravekart.response.CreateCategoryResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FoodCategoryService {
  public CreateCategoryResponse createCategory (CreateCategoryRequest request) ;
  public List<FoodCategory> findCategoryByRestaurantId(Long restaurantId) ;
  public FoodCategory findCategoryById(Long id) ;
  List<FoodCategory> getAllFoodCategories() ;
}
