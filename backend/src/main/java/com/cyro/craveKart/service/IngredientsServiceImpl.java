package com.cyro.cravekart.service;


import com.cyro.cravekart.exception.RestaurantException;
import com.cyro.cravekart.models.IngredientCategory;
import com.cyro.cravekart.models.IngredientItem;
import com.cyro.cravekart.models.Restaurant;
import com.cyro.cravekart.repository.IngredientCategoryRepo;
import com.cyro.cravekart.repository.IngredientItemRepository;
import com.cyro.cravekart.repository.RestaurantRepository;
import com.cyro.cravekart.request.CreateIngredientCategoryRequest;
import com.cyro.cravekart.request.CreateIngredientItemRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class IngredientsServiceImpl implements IngredientsService {
  private final IngredientCategoryRepo categoryRepo;
  private final RestaurantRepository  restaurantRepository;
  private final IngredientItemRepository ingredientItemRepository;
  private final RestaurantService restaurantService;

  @Override
  public IngredientCategory createIngredientCategory(CreateIngredientCategoryRequest request) throws RestaurantException {

    if( categoryRepo.existsByRestaurantIdAndNameIgnoreCase(
        request.getRestaurantId(), request.getName()))
      throw new RestaurantException("Ingredient Category already exists for this restaurant");

    Restaurant restaurant = restaurantRepository.findById(
        request.getRestaurantId()).orElseThrow(() ->
          new RuntimeException("Ingredient category already exists for this restaurant"));

    IngredientCategory category = IngredientCategory.builder()
        .name(request.getName())
        .restaurant(restaurant)
        .build();
    return  categoryRepo.save(category);
  }

  @Override
  public IngredientCategory findIngredientCategoryById(Long id) throws RestaurantException {
    Optional<IngredientCategory> opt = categoryRepo.findById(id);
    if(opt.isEmpty())
      throw new RestaurantException("Ingredient Category not found for this restaurant");
    return opt.get();
  }

  @Override
  public List<IngredientCategory> findByRestaurantId(Long restaurantId) throws Exception {
    List<IngredientCategory> allByRestaurantId =
        categoryRepo.findAllByRestaurantId(restaurantId);
    return allByRestaurantId;
  }

  @Override
  public IngredientItem createIngredientItem(CreateIngredientItemRequest request) {

    IngredientCategory category = findIngredientCategoryById(request.getCategoryId());
    IngredientItem existingItem = ingredientItemRepository.findByRestaurantIdAndNameIgnoreCase(
        request.getRestaurantId(), request.getName(), category.getName());

    if(existingItem != null){
      return existingItem;
    }

    Restaurant restaurantById = restaurantService.getRestaurantById_util(request.getRestaurantId());
    IngredientItem item = IngredientItem.builder()
        .name(request.getName())
        .restaurant(restaurantById)
        .category(category)
        .build();

    IngredientItem savedItem = ingredientItemRepository.save(item);
    category.getIngredients().add(savedItem);
    return savedItem;
  }

  @Override
  public IngredientItem updateStock(Long ingredientId) {
    Optional<IngredientItem> item = ingredientItemRepository.findById(ingredientId);
    if(item.isEmpty())
      throw new RestaurantException("Ingredient not found with this id" +  ingredientId);
    IngredientItem ingredientItem = item.get();
    ingredientItem.setInStock(!ingredientItem.isInStock());
    return ingredientItemRepository.save(ingredientItem);
  }


}














