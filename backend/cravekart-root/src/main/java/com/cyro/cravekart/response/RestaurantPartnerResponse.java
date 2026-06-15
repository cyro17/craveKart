package com.cyro.cravekart.response;

import com.cyro.cravekart.models.OnboardingApplication;
import com.cyro.cravekart.models.RestaurantPartner;
import com.cyro.cravekart.models.enums.OnboardingStatus;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantPartnerResponse {

  // partner info
  private Long partnerId;
  private String partnerUsername;
  private String partnerEmail;
  private OnboardingStatus onboardingStatus;
  private String rejectionReason;

  private LocalDateTime appliedAt;

  // application details

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

  private LocalDateTime submittedAt;

  public static RestaurantPartnerResponse from(RestaurantPartner partner) {
    OnboardingApplication app = partner.getApplication();

    RestaurantPartnerResponseBuilder builder =
        RestaurantPartnerResponse.builder()
            .partnerId(partner.getId())
            .partnerUsername(partner.getUser().getUsername())
            .partnerEmail(partner.getUser().getEmail())
            .onboardingStatus(partner.getOnboardingStatus())
            .appliedAt(partner.getAppliedAt())
            .rejectionReason(partner.getRejectionReason());

    if (app != null) {
      builder
          .restaurantName(app.getRestaurantName())
          .cuisineType(app.getCuisineType())
          .description(app.getDescription())
          .openingHours(app.getOpeningHours())
          .fssaiLicence(app.getFssaiLicence())
          .contactNumber(app.getContactNumber())
          .streetAddress(app.getStreetAddress())
          .city(app.getCity())
          .state(app.getState())
          .postalCode(app.getPostalCode())
          .country(app.getCountry())
          .submittedAt(app.getSubmittedAt());
    }

    return builder.build();
  }
}
