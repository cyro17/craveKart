package com.cyro.cravekart.repository;

import com.cyro.cravekart.models.Restaurant;
import com.cyro.cravekart.models.RestaurantPartner;
import com.cyro.cravekart.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RestaurantPartnerRepository extends JpaRepository<RestaurantPartner, Long> {

  Optional<RestaurantPartner> findByUser(User user);
  Optional<RestaurantPartner> findByUserId(Long userId);

  boolean existsByUser(User user);
}
