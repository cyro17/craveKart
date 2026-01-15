package com.cyro.cravekart.service;

import com.cyro.cravekart.models.Food;
import com.cyro.cravekart.models.FoodCategory;
import com.cyro.cravekart.models.IngredientItem;
import com.cyro.cravekart.models.Restaurant;
import com.cyro.cravekart.repository.FoodCategoryRepository;
import com.cyro.cravekart.repository.FoodRepository;
import com.cyro.cravekart.repository.RestaurantRepository;
import com.cyro.cravekart.request.AdminCreateFoodRequest;
import com.cyro.cravekart.service.utils.AdminIngredientResolverService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AdminFoodServiceImpl implements AdminFoodService{
  private final FoodRepository foodRepository;
  private final RestaurantRepository restaurantRepository;
  private final FoodCategoryRepository foodCategoryRepository;
  private final AdminIngredientResolverService adminIngredientResolverService;

  @Override
  public Food createFood(AdminCreateFoodRequest request) {
    Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId())
        .orElseThrow(() -> new RuntimeException("Restaurant not found"));

    FoodCategory foodCategory = foodCategoryRepository.findByNameIgnoreCaseAndRestaurantId(
        request.getFoodCategoryName(), restaurant.getId()
    ).orElseGet(
        () -> foodCategoryRepository.save(
            FoodCategory.builder()
                .name(request.getFoodCategoryName())
                .restaurant(restaurant)
                .build()
        )
    );

    Food food = Food.builder()
        .name(request.getName())
        .description(request.getDescription())
        .price(request.getPrice())
        .vegetarian(request.getVegetarian())
        .seasonal(request.getSeasonal())
        .available(request.getAvailable())
        .category(foodCategory)
        .restaurant(restaurant)
        .build();

    Set<IngredientItem> ingredientItems = new HashSet<>();
    food.setIngredientItems(
      adminIngredientResolverService.resolve(
          request.getIngredientsRequests(),
          restaurant
      )
    );
    return foodRepository.save(food);

  }

}
