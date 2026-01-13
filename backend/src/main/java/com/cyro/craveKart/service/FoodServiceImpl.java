package com.cyro.cravekart.service;

import com.cyro.cravekart.exception.FoodException;
import com.cyro.cravekart.models.Category;
import com.cyro.cravekart.models.Food;
import com.cyro.cravekart.models.Restaurant;
import com.cyro.cravekart.repository.FoodRepository;
import com.cyro.cravekart.request.CreateFoodRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {

  private final FoodRepository foodRepository;

  @Override
  public Food createFood(CreateFoodRequest req, Category category, Restaurant restaurant)
  throws FoodException{
    Food food = Food.builder()
        .category(category)
        .restaurant(restaurant)
        .description(req.getDescription())
        .images(req.getImages())
        .name(req.getName())
        .price(req.getPrice())
        .seasonal(req.isSeasonal())
        .vegetarian(req.isVegetarian())
        .ingredientItems(req.getIngredientItems())
        .build();

    foodRepository.save(food);
    return  restaurant.getFoods().add(food) ? food : null;
  }

  @Override
  public void deleteFood(Long foodId) {
    Food food = foodRepository.findById(foodId).orElseThrow(() -> new RuntimeException());
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
