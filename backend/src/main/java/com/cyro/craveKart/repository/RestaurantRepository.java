package com.cyro.craveKart.repository;

import com.cyro.craveKart.model.Restaurant;
import com.cyro.craveKart.service.RestaurantService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

//    @Query("SELECT r FROM Restaurant r WHERE lower(r.name) ")
//    List<Restaurant> findBySearchQuery(String query);
    Restaurant findByOwnerId(Long userId);

}
