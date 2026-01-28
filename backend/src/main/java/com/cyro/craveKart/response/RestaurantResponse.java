package com.cyro.cravekart.response;

import com.cyro.cravekart.models.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class RestaurantResponse implements Serializable {

  private String name;
  private String description;
  private String cuisineType;
  private String fullAddress;

  private String email;
  private String phone;
  private String openingHours;
  private int ratings;
  private List<String> images;
  private LocalDateTime registeredAt;
  private boolean open;


  public static RestaurantResponse from(Restaurant restaurant) {
    return RestaurantResponse.builder()
        .name(restaurant.getName())
        .description(restaurant.getDescription())
        .cuisineType(restaurant.getCuisineType())
        .fullAddress(
            restaurant.getAddress() != null ?
                restaurant.getAddress().getFullAddress(): null
        )
        .email(
            restaurant.getContactInfo() != null
                ? restaurant.getContactInfo().getMail()
                : null
        )
        .phone(
            restaurant.getContactInfo() != null
                ? restaurant.getContactInfo().getMobile()
                : null)
        .openingHours(restaurant.getOpeningHours())
        .ratings(restaurant.getNumRating())
        .images(
            restaurant.getImages() == null
                ? List.of()
                : restaurant.getImages().stream()
                .map(String::valueOf) // or getUrl()
                .toList()
        )
        .registeredAt(restaurant.getCreatedAt())
        .open(restaurant.isOpen())
        .build();
  }


}
