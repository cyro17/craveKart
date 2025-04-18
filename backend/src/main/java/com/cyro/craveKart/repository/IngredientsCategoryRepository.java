package com.cyro.craveKart.repository;

import com.cyro.craveKart.model.IngredientCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IngredientsCategoryRepository extends JpaRepository<IngredientCategory, Long> {

    List<IngredientCategory> findRestaurantId(Long id);

    @Query("SELECT e FROM IngredientCategory e "
            + "WHERE e.restaurant.id = :restaurantId "
            + "AND lower(e.name) = lower(:name)")
    IngredientCategory findByRestaurantIdAndNameIgnoreCase(
            @Param("restaurantId") Long restaurantId,
            @Param("name") String name
    );

}
