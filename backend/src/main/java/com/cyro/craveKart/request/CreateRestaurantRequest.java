  package com.cyro.cravekart.request;

  import com.cyro.cravekart.models.Address;
  import com.cyro.cravekart.models.ContactInfo;
  import jakarta.validation.Valid;
  import lombok.AllArgsConstructor;
  import lombok.Builder;
  import lombok.Data;
  import lombok.NoArgsConstructor;

  import java.time.LocalDateTime;
  import java.util.List;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public class CreateRestaurantRequest {

    private String name;
    private String description;
    private String cuisineType;
    @Valid
    private ContactInfo contactInfo;
    private String openingHours;
    private AddressRequest addressRequest;
    private List<String> images;

  }
