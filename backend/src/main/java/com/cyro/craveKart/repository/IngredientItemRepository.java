package com.cyro.cravekart.repository;

import com.cyro.cravekart.models.IngredientItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface IngredientItemRepository extends JpaRepository<IngredientItem, Long> {

  List<IngredientItem> findByRestaurantId(Long restaurantId);

  @Query("select e from IngredientItem e " +
      "where e.restaurant.id = :restaurantId " +
      "and lower(e.name) = lower(:name) " +
      "and e.category.name = :categoryName")
  public IngredientItem findByRestaurantIdAndNameIgnoreCase(
                                      Long restaurantId,
                                      String name,
                                      String categoryName);

  Optional<IngredientItem> findByNameIgnoreCaseAndRestaurantId(String name, Long restaurantId);


}
