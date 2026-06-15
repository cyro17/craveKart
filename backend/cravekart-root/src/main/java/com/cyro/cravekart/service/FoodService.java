package com.cyro.cravekart.service;

import com.cyro.cravekart.models.Food;
import com.cyro.cravekart.request.AddFoodRequest;
import com.cyro.cravekart.response.FoodResponse;
import java.util.List;

public interface FoodService {

  public Food addFood(AddFoodRequest req);

  boolean deleteFood(Long foodId, Long restaurantId);

  public List<Food> getRestaurantFoods(
      Long restaurantId, boolean isVeg, boolean isSeasonal, String foodCategory);

  public List<Food> searchFood(String keyword);

  public Food findFoodById(Long foodId);

  public FoodResponse updateAvailibilityStatus(Long foodId, Long restaurantId);
}
