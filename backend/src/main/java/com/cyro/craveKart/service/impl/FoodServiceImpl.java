package com.cyro.cravekart.service.impl;

import com.cyro.cravekart.exception.FoodException;
import com.cyro.cravekart.models.FoodCategory;
import com.cyro.cravekart.models.Food;
import com.cyro.cravekart.models.IngredientItem;
import com.cyro.cravekart.models.Restaurant;
import com.cyro.cravekart.repository.FoodCategoryRepository;
import com.cyro.cravekart.repository.FoodRepository;
import com.cyro.cravekart.repository.IngredientItemRepository;
import com.cyro.cravekart.repository.RestaurantRepository;
import com.cyro.cravekart.request.CreateFoodRequest;
import com.cyro.cravekart.service.FoodService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class FoodServiceImpl implements FoodService {

  private final FoodRepository foodRepository;
  private final RestaurantRepository restaurantRepository;
  private final FoodCategoryRepository foodCategoryRepository;
  private final IngredientItemRepository ingredientItemRepository;

  @Override
  public Food createFood(CreateFoodRequest req) throws FoodException {

    // if food already exist
    if(foodRepository.existsByRestaurantIdAndNameIgnoreCase(
        req.getRestaurant_id(), req.getName()))
      throw new FoodException("Food already exists");

    Restaurant restaurant = restaurantRepository.findById(
        req.getRestaurant_id()).orElseThrow(
        () -> new FoodException("Restaurant not found")
    );

    FoodCategory foodCategory = foodCategoryRepository.findById(
        req.getFoodCategoryId()).orElseThrow(
        () -> new FoodException("Food category not found")
    );

    Set<IngredientItem> ingredients = new HashSet<>();

    if(req.getIngredientItemsIds() != null &&
        req.getIngredientItemsIds().isEmpty()) {
      ingredients.addAll(ingredientItemRepository.findAllById(
          req.getIngredientItemsIds()
      ));
    }

    Food food = Food.builder()
        .category(foodCategory)
        .restaurant(restaurant)
        .description(req.getDescription())
        .images(req.getImages() != null ? req.getImages() : new  ArrayList<>())
        .name(req.getName())
        .price(req.getPrice())
        .seasonal(req.getSeasonal())
        .vegetarian(req.getVegetarian())
        .available(req.getAvailable())
        .ingredientItems(ingredients)
        .build();

    return foodRepository.save(food);

  }

  @Override
  public void deleteFood(Long foodId) {
    Food food = foodRepository.findById(foodId).orElseThrow(
        () -> new RuntimeException());
    food.setRestaurant(null);
    foodRepository.delete(food);
  }



  @Override
  public List<Food> getRestaurantFoods(Long restaurantId,
                                       boolean isVeg,
                                       boolean isSeasonal ,
                                       String foodCategory) throws FoodException {

    List<Food> foods = foodRepository.findByRestaurantId(restaurantId);

    if(isVeg)
      filterByVeg(foods, isVeg);

    if(!isVeg)
      filterByVeg(foods, !isVeg);

    if(isSeasonal)
      filterBySeasonal(foods, isSeasonal);

    if(foodCategory != null && !foodCategory.equals(""))
      filterByFoodCategory(foods, foodCategory);

    return foods;

  }

  private List<Food> filterByVeg(List<Food> foods, boolean isveg) {
    return foods.stream()
        .filter(food-> food.isVegetarian() == isveg)
        .collect(Collectors.toList());
  }

  private List<Food> filterBySeasonal(List<Food> foods, boolean isSeasonal) {
    return foods.stream()
        .filter(food -> food.isSeasonal() == isSeasonal)
        .collect(Collectors.toList());
  }

  private List<Food> filterByFoodCategory(List<Food> foods, String foodCategory) {
      return  foods.stream()
          .filter(food -> food.getCategory().equals(foodCategory))
          .collect(Collectors.toList());
  }

  @Override
  public List<Food> searchFood(String keyword) {
    List<Food> foods = new ArrayList<>();
    if(keyword != ""){
      foods = foodRepository.searchByNameOrCategory(keyword);
    }
    return  foods;
  }

  @Override
  public Food findFoodById(Long foodId) throws FoodException{
    Optional<Food> food = foodRepository.findById(foodId);
    if(food.isPresent()) {
      return food.get();
    }
    throw new FoodException("Food with is " + foodId + "not found");
  }

  @Override
  public Food updateAvailibilityStatus(Long foodId) throws FoodException {
    Food food = findFoodById(foodId);
    food.setAvailable(!food.isAvailable());
    foodRepository.save(food);
    return food;
  }
}
