package com.cyro.cravekart.repository;

import com.cyro.cravekart.models.FoodCategory;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodCategoryRepository extends JpaRepository<FoodCategory, Long> {

  boolean existsByNameIgnoreCaseAndRestaurantId(String name, Long restaurantId);

  boolean existsByNameIgnoreCase(String name);

  Optional<FoodCategory> findByNameIgnoreCaseAndRestaurantId(String name, Long restaurantId);

  List<FoodCategory> findByRestaurantIdOrderByIdAsc(Long restaurantId);
}
