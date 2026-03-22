package com.cyro.cravekart.repository;

import com.cyro.cravekart.models.RestaurantPartner;
import com.cyro.cravekart.models.User;
import com.cyro.cravekart.models.enums.OnboardingStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantPartnerRepository extends JpaRepository<RestaurantPartner, Long> {

  Optional<RestaurantPartner> findByUser(User user);

  Optional<RestaurantPartner> findByUserId(Long userId);

  List<RestaurantPartner> findByOnboardingStatus(OnboardingStatus status);
  
  boolean existsByUser(User user);
}
