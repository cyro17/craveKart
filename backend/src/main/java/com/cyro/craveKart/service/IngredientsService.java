package com.cyro.cravekart.service;

import com.cyro.cravekart.exception.RestaurantException;
import com.cyro.cravekart.models.IngredientCategory;
import com.cyro.cravekart.models.IngredientItem;
import com.cyro.cravekart.request.CreateIngredientCategoryRequest;
import com.cyro.cravekart.request.CreateIngredientItemRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IngredientsService {

  public IngredientCategory createIngredientCategory(
      CreateIngredientCategoryRequest request) throws RestaurantException;

  public IngredientCategory findIngredientCategoryById(Long id) throws RestaurantException;
  public List<IngredientCategory> findByRestaurantId(Long restaurantId) throws Exception;
  public  IngredientItem createIngredientItem(CreateIngredientItemRequest request);
  IngredientItem updateStock(Long ingredientId);
}
