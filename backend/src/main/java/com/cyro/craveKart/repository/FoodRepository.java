package com.cyro.cravekart.repository;

import com.cyro.cravekart.models.Food;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {

  List<Food> findByRestaurantId(Long restaurantId);

  @Query("select f from Food f where f.name like %:keyword or " +
      "f.category.name like %:keyword and " +
      "f.restaurant !=null")
  List<Food> searchByNameOrCategory(String keyword);

  boolean existsByRestaurantIdAndNameIgnoreCase(Long restaurantId, String name);

  List<Food> findByRestaurantIdAndAvailableTrueOrderByCategoryIdAsc(Long restaurantId);

}
