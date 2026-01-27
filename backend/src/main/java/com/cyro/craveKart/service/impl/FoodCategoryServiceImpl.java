package com.cyro.cravekart.service.impl;

import com.cyro.cravekart.exception.RestaurantException;
import com.cyro.cravekart.models.FoodCategory;
import com.cyro.cravekart.models.Restaurant;
import com.cyro.cravekart.repository.FoodCategoryRepository;
import com.cyro.cravekart.repository.FoodRepository;
import com.cyro.cravekart.repository.RestaurantRepository;
import com.cyro.cravekart.request.CreateCategoryRequest;
import com.cyro.cravekart.response.CreateCategoryResponse;
import com.cyro.cravekart.service.FoodCategoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FoodCategoryServiceImpl implements FoodCategoryService {
  private final FoodCategoryRepository foodCategoryRepository;
  private final RestaurantRepository restaurantRepository;
  private final FoodRepository foodRepository;

  @Override
  public CreateCategoryResponse createCategory(CreateCategoryRequest request)
      throws RestaurantException {
    Restaurant restaurant = restaurantRepository.findById(request.getRestaurant_id())
        .orElseThrow(() -> new RestaurantException("Restaurant not found"));

    boolean exist = foodCategoryRepository
        .existsByNameIgnoreCaseAndRestaurantId(request.getName(), restaurant.getId());
    if(exist) throw new RestaurantException("Category already exists");

    FoodCategory savedCategory = FoodCategory.builder().
        name(request.getName())
        .restaurant(restaurant).build();
    foodCategoryRepository.save(savedCategory);

    return CreateCategoryResponse.builder()
        .id(savedCategory.getId())
        .name(savedCategory.getName())
        .restaurantId(savedCategory.getRestaurant().getId()).build();
  }

  @Override
  public List<FoodCategory> findCategoryByRestaurantId(Long restaurantId)
      throws RestaurantException {
    return List.of();
  }

  @Override
  public FoodCategory findCategoryById(Long id) throws RestaurantException {
    return null;
  }

  @Override
  public List<FoodCategory> getAllFoodCategories() {
    return foodCategoryRepository.findAll();
  }
}
