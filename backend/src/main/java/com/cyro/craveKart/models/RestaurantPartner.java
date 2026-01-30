package com.cyro.cravekart.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "restaurant_partners")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantPartner {
  @Id
  private Long id;

  @OneToOne(fetch =  FetchType.LAZY)
  @MapsId
  @JoinColumn(name = "user_id")
  private User user;

  @OneToOne(mappedBy = "restaurantPartner", fetch = FetchType.LAZY)
  @JoinColumn(nullable = false)
  private Restaurant restaurant;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "onboarding_admin")
  private Admin onboardingAdmin;

  private boolean active = false;
  private boolean verified = false;

  @CreationTimestamp
  private LocalDateTime createdAt;

  @UpdateTimestamp
  private LocalDateTime updatedAt;
}
