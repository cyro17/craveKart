package com.cyro.craveKart.service;

import com.cyro.craveKart.exception.FoodException;
import com.cyro.craveKart.model.Category;
import com.cyro.craveKart.model.Food;
import com.cyro.craveKart.model.Restaurant;
import com.cyro.craveKart.request.CreateFoodRequest;

import java.util.List;

public interface FoodService {

    public Food createFood(CreateFoodRequest createFoodRequest,
                           Category category,
                           Restaurant restaurant) throws FoodException;
    void deleteFood(Long foodId) throws FoodException;

    public List<Food> getRestaurantsFood(Long restaurantId,
                                         boolean isVeg,
                                         boolean isNonVeg,
                                         boolean isSeasonal,
                                         String foodCategory) throws FoodException;

    public List<Food> searchFood(String food);

    public Food findFoodById(Long foodId) throws FoodException;

    public Food updateAvalibilityStatus(Long foodId) throws FoodException;

}
