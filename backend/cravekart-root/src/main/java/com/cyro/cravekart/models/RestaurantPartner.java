package com.cyro.cravekart.models;

import com.cyro.cravekart.models.enums.OnboardingStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "restaurant_partners")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantPartner {
  @Id private Long id;

  @OneToOne(fetch = FetchType.LAZY)
  @MapsId
  @JoinColumn(name = "user_id")
  private User user;

  @OneToOne(mappedBy = "restaurantPartner", fetch = FetchType.LAZY)
  @JoinColumn(nullable = false)
  private Restaurant restaurant;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "onboarding_admin")
  private Admin onboardingAdmin;

  @OneToOne(
      mappedBy = "restaurantPartner",
      cascade = CascadeType.ALL,
      fetch = FetchType.LAZY,
      orphanRemoval = true)
  private OnboardingApplication application;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  @Builder.Default
  private OnboardingStatus onboardingStatus = OnboardingStatus.NOT_APPLIED;

  private List<String> images = new ArrayList<>();

  private String rejectionReason;

  @Builder.Default private boolean active = false;
  @Builder.Default private boolean verified = false;

  private LocalDateTime approvedAt;
  private LocalDateTime appliedAt;

  @CreationTimestamp private LocalDateTime createdAt;
}
