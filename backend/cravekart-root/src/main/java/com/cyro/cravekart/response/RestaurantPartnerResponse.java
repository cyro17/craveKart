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

    return RestaurantPartnerResponse.builder()
        // partner
        .partnerId(partner.getId())
        .partnerUsername(partner.getUser().getUsername())
        .partnerEmail(partner.getUser().getEmail())
        .onboardingStatus(partner.getOnboardingStatus())
        .appliedAt(partner.getAppliedAt())
        .rejectionReason(partner.getRejectionReason())
        .restaurantName(app != null ? app.getRestaurantName() : null)
        .cuisineType(app != null ? app.getCuisineType() : null)
        .description(app != null ? app.getDescription() : null)
        .openingHours(app != null ? app.getOpeningHours() : null)
        .fssaiLicence(app != null ? app.getFssaiLicence() : null)
        .contactNumber(app != null ? app.getContactNumber() : null)
        .streetAddress(app != null ? app.getStreetAddress() : null)
        .city(app != null ? app.getCity() : null)
        .state(app != null ? app.getState() : null)
        .postalCode(app != null ? app.getPostalCode() : null)
        .country(app != null ? app.getCountry() : null)
        .submittedAt(app != null ? app.getSubmittedAt() : null)
        .build();
  }
}
