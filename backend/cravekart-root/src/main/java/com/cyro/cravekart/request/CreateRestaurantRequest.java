package com.cyro.cravekart.request;

import com.cyro.cravekart.models.ContactInfo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateRestaurantRequest {
  @NotBlank(message = "Restaurant name is required")
  private String name;

  @NotBlank(message = "Description is required")
  private String description;

  @NotBlank(message = "Cuisine type is required")
  private String cuisineType;

  @NotNull(message = "Contact info is required")
  @Valid
  private ContactInfo contactInfo;

  @NotBlank(message = "Opening hours are required")
  private String openingHours;

  @NotNull(message = "Address is required")
  @Valid
  private AddressRequest addressRequest; // ← validates nested address fields

  private List<String> images;
  private String fssaiLicence;
}
