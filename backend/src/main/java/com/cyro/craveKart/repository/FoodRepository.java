package com.cyro.craveKart.repository;

import com.cyro.craveKart.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long> {

    List<Food> findByRestaurantId(Long restaurantId);

    List<Food> searchByNameOrCategory(@Param("keyword") String keyword);
}
