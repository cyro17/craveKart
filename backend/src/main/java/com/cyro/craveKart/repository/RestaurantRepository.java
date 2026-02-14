package com.cyro.cravekart.repository;

import com.cyro.cravekart.models.Restaurant;
import com.cyro.cravekart.models.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long>, JpaSpecificationExecutor<Restaurant> {

  @Query("select r from Restaurant r where" +
      " lower(r.name) LIKE  lower(concat('%', :query, '%')) or " +
      "lower(r.cuisineType) LIKE lower(concat('%', :query, '%'))")
  List<Restaurant> findBySearchQuery(String query);

  Restaurant findByRestaurantPartner(Long userId);

  boolean existsByNameIgnoreCase(String name);

//  @Query("select r from Restaurant r " +
//      "join r.restaurantPartner rp " +
//      "where r.open = true and rp.active = true and rp.verified = true")
//  List<Restaurant> findLiveRestaurants();
//
//  @Query("""
//    select distinct r from Restaurant r
//    left join fetch r.foodCategories c
//    left join fetch c.foods f
//    where r.slug = :slug
//""")
//  Optional<Restaurant> findBySlugWithCategoriesAndFoods(@Param("slug") String slug);

}
