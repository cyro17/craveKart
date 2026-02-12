package com.cyro.cravekart.repository;

import com.cyro.cravekart.models.Restaurant;
import com.cyro.cravekart.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long>, JpaSpecificationExecutor<Restaurant> {

  @Query("select r from Restaurant r where" +
      " lower(r.name) LIKE  lower(concat('%', :query, '%')) or " +
      "lower(r.cuisineType) LIKE lower(concat('%', :query, '%'))")
  List<Restaurant> findBySearchQuery(String query);

  Restaurant findByRestaurantPartner(Long userId);

  boolean existsByNameIgnoreCase(String name);

  @Query("select r from Restaurant r " +
      "join r.restaurantPartner rp " +
      "where r.open = true and rp.active = true and rp.verified = true")
  List<Restaurant> findLiveRestaurants();

}
