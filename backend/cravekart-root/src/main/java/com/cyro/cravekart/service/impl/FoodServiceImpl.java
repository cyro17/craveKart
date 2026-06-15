package com.cyro.cravekart.service.impl;

import com.cyro.cravekart.exception.BadRequestException;
import com.cyro.cravekart.exception.ResourceNotFoundException;
import com.cyro.cravekart.exception.UnauthorizedException;
import com.cyro.cravekart.models.Food;
import com.cyro.cravekart.models.FoodCategory;
import com.cyro.cravekart.models.IngredientItem;
import com.cyro.cravekart.models.Restaurant;
import com.cyro.cravekart.repository.FoodCategoryRepository;
import com.cyro.cravekart.repository.FoodRepository;
import com.cyro.cravekart.repository.IngredientItemRepository;
import com.cyro.cravekart.repository.RestaurantRepository;
import com.cyro.cravekart.request.AddFoodRequest;
import com.cyro.cravekart.response.FoodResponse;
import com.cyro.cravekart.service.FoodService;
import jakarta.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class FoodServiceImpl implements FoodService {

  private final FoodRepository foodRepository;
  private final RestaurantRepository restaurantRepository;
  private final FoodCategoryRepository foodCategoryRepository;
  private final IngredientItemRepository ingredientItemRepository;
  private final ModelMapper modelMapper;

  @Override
  @Transactional
  public Food addFood(AddFoodRequest req) {

    // if food already exist
    if (foodRepository.existsByRestaurantIdAndNameIgnoreCase(
        req.getRestaurant_id(), req.getName())) {
      throw new BadRequestException("Food already exists");
    }

    Restaurant restaurant =
        restaurantRepository
            .findById(req.getRestaurant_id())
            .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));

    //    boolean exists =
    //        foodCategoryRepository.existsByNameIgnoreCaseAndRestaurantId(
    //            req.getFoodCategoryName(), req.getRestaurant_id());
    //
    //    if (!exists) {
    //      FoodCategory category =
    //
    // FoodCategory.builder().name(req.getFoodCategoryName()).restaurant(restaurant).build();
    //      foodCategoryRepository.save(category);
    //    }

    Set<IngredientItem> ingredients = new HashSet<>();

    if (req.getIngredientItemsIds() != null && !req.getIngredientItemsIds().isEmpty()) {
      ingredients.addAll(ingredientItemRepository.findAllById(req.getIngredientItemsIds()));
    }

    FoodCategory category =
        foodCategoryRepository
            .findByNameIgnoreCaseAndRestaurantId(req.getFoodCategoryName(), req.getRestaurant_id())
            .orElseGet(
                () ->
                    foodCategoryRepository.save(
                        FoodCategory.builder()
                            .name(req.getFoodCategoryName())
                            .restaurant(restaurant)
                            .build()));
    Food food =
        Food.builder()
            .category(category)
            .restaurant(restaurant)
            .description(req.getDescription())
            .images(req.getImages() != null ? req.getImages() : new ArrayList<>())
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
  @Transactional
  public boolean deleteFood(Long foodId, Long restaurantId) {
    //    Food food =
    //        foodRepository
    //            .findById(foodId)
    //            .orElseThrow(() -> new ResourceNotFoundException("Food not found"));
    //    food.setRestaurant(null);
    //    foodRepository.delete(food);

    Food food =
        foodRepository
            .findById(foodId)
            .orElseThrow(() -> new ResourceNotFoundException("Food not found"));

    Long foodRestaurantId = food.getRestaurant().getId();
    if (!foodRestaurantId.equals(restaurantId)) {
      return false;
    }

    foodRepository.delete(food);
    return true;
  }

  @Override
  public List<Food> getRestaurantFoods(
      Long restaurantId, boolean isVeg, boolean isSeasonal, String foodCategory) {

    List<Food> foods = foodRepository.findByRestaurantId(restaurantId);

    if (isVeg) foods = filterByVeg(foods, isVeg);

    if (!isVeg) foods = filterByVeg(foods, !isVeg);

    if (isSeasonal) foods = filterBySeasonal(foods, isSeasonal);

    if (foodCategory != null && !foodCategory.equals(""))
      foods = filterByFoodCategory(foods, foodCategory);

    return foods;
  }

  private List<Food> filterByVeg(List<Food> foods, boolean isVeg) {
    return foods.stream().filter(food -> food.isVegetarian() == isVeg).collect(Collectors.toList());
  }

  private List<Food> filterBySeasonal(List<Food> foods, boolean isSeasonal) {
    return foods.stream()
        .filter(food -> food.isSeasonal() == isSeasonal)
        .collect(Collectors.toList());
  }

  private List<Food> filterByFoodCategory(List<Food> foods, String foodCategory) {
    return foods.stream()
        .filter(food -> food.getCategory().equals(foodCategory))
        .collect(Collectors.toList());
  }

  @Override
  public List<Food> searchFood(String keyword) {
    List<Food> foods = new ArrayList<>();
    if (keyword != "") {
      foods = foodRepository.searchByNameOrCategory(keyword);
    }
    return foods;
  }

  @Override
  public Food findFoodById(Long foodId) {
    Optional<Food> food = foodRepository.findById(foodId);
    if (food.isPresent()) {
      return food.get();
    }
    throw new ResourceNotFoundException("Food with is " + foodId + "not found");
  }

  @Override
  public FoodResponse updateAvailibilityStatus(Long foodId, Long restaurantId) {
    Food food = findFoodById(foodId);
    if (food.getRestaurant().getId().equals(restaurantId)) food.setAvailable(!food.isAvailable());
    else throw new UnauthorizedException("Not Authorized !!");
    foodRepository.save(food);
    return modelMapper.map(food, FoodResponse.class);
  }
}
