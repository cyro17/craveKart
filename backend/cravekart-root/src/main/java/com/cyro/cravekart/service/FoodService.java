package com.cyro.cravekart.service;

import com.cyro.cravekart.exception.FoodException;
import com.cyro.cravekart.models.Food;
import com.cyro.cravekart.request.AdminCreateFoodRequest;
import com.cyro.cravekart.request.CreateFoodRequest;

import java.util.List;

public interface FoodService {

  public Food createFood(CreateFoodRequest req) ;

  void deleteFood(Long foodId);

  public List<Food> getRestaurantFoods(Long restaurantId,
                                       boolean isVeg,
                                       boolean isSeasonal ,
                                       String foodCategory) ;

  public List<Food> searchFood(String keyword);

  public  Food findFoodById(Long foodId) ;

  public Food updateAvailibilityStatus(Long foodId) ;

}
