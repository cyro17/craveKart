package com.cyro.cravekart.service;

import com.cyro.cravekart.exception.FoodException;
import com.cyro.cravekart.models.Category;
import com.cyro.cravekart.models.Food;
import com.cyro.cravekart.models.Restaurant;
import com.cyro.cravekart.request.CreateFoodRequest;

import java.util.List;

public interface FoodService {

  public Food createFood(CreateFoodRequest req,
                         Category category,
                         Restaurant restaurant) throws FoodException;

  void deleteFood(Long foodId);

  public List<Food> getRestaurantFoods(Long restaurantId,
                                       boolean isVeg,
                                       boolean isSeasonal ,
                                       String foodCategory) throws FoodException;

  public List<Food> searchFood(String keyword);

  public  Food findFoodById(Long foodId) throws FoodException;

  public Food updateAvailibilityStatus(Long foodId) throws FoodException;

}
