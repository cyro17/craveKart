package com.cyro.cravekart.repository;

import com.cyro.cravekart.models.Restaurant;
import com.cyro.cravekart.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

  @Query("select r from Restaurant r where" +
      " lower(r.name) LIKE  lower(concat('%', :query, '%')) or " +
      "lower(r.cuisineType) LIKE lower(concat('%', :query, '%'))")
  List<Restaurant> findBySearchQuery(String query);

  List<Restaurant> findByOwnerId(Long userId);

}
