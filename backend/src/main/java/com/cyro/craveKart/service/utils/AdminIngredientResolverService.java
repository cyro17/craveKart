package com.cyro.cravekart.service.utils;

import com.cyro.cravekart.models.IngredientCategory;
import com.cyro.cravekart.models.IngredientItem;
import com.cyro.cravekart.models.Restaurant;
import com.cyro.cravekart.repository.IngredientCategoryRepo;
import com.cyro.cravekart.repository.IngredientItemRepository;
import com.cyro.cravekart.request.AdminIngredientsRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AdminIngredientResolverService {

  public final IngredientCategoryRepo ingredientCategoryRepo;
  public final IngredientItemRepository ingredientItemRepo;

  public Set<IngredientItem> resolve(
      List<AdminIngredientsRequest> requests,
      Restaurant restaurant
  ){
    Set<IngredientItem> resolvedItems = new HashSet<>();
    if(requests ==null )
      return  resolvedItems;

    for(AdminIngredientsRequest request : requests){

      IngredientCategory category = ingredientCategoryRepo
          .findByNameIgnoreCaseAndRestaurantId(
            request.getCategoryName(), restaurant.getId()
      ).orElseGet(
          () -> ingredientCategoryRepo.save(
              IngredientCategory.builder()
                  .name(request.getCategoryName())
                  .restaurant(restaurant)
                  .build()
          ));
      for(String ingredientName: request.getIngredients()){

        IngredientItem item = ingredientItemRepo
            .findByNameIgnoreCaseAndRestaurantId(
              ingredientName, restaurant.getId()
        ).orElseGet(() ->
            ingredientItemRepo.save(
                IngredientItem.builder()
                    .name(ingredientName)
                    .category(category)
                    .restaurant(restaurant)
                    .build()
            ));
        resolvedItems.add(item);
      }
    }
    return  resolvedItems;
  }
}
