package com.cyro.cravekart.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "onboarding_applications")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OnboardingApplication {
  @Id private Long id;

  @OneToOne
  @MapsId
  @JoinColumn(name = "partner_id")
  private RestaurantPartner restaurantPartner;

  private String restaurantName;
  private String cuisineType;
  private String description;
  private String openingHours;
  private String fssaiLicence;
  private String contactNumber;

  private String streetAddress;
  private String city;
  private String state;
  private String postalCode;
  private String country;

  // review

  private String rejectionReason;
  private LocalDateTime reviewedAt;

  @CreationTimestamp private LocalDateTime submittedAt;
  @UpdateTimestamp private LocalDateTime updatedAt;
}
