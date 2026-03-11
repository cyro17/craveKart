package com.cyro.cravekart.repository;

import com.cyro.cravekart.models.IngredientCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface IngredientCategoryRepo extends JpaRepository<IngredientCategory, Long> {

  Optional<IngredientCategory> findByRestaurantIdAndNameIgnoreCase(Long restaurant_id, String name);

  boolean existsByRestaurantIdAndNameIgnoreCase(Long restaurantId, String name);

  List<IngredientCategory> findAllByRestaurantId(Long restaurantId);

  Optional<IngredientCategory> findByNameIgnoreCaseAndRestaurantId(String name, Long restaurantId);

}


