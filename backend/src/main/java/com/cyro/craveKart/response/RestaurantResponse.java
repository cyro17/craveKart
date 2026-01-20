package com.cyro.cravekart.response;

import com.cyro.cravekart.models.Address;
import com.cyro.cravekart.models.ContactInfo;
import com.cyro.cravekart.models.Restaurant;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
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

    return  new RestaurantResponse(
      restaurant.getName(),
        restaurant.getDescription(),
        restaurant.getCuisineType(),
        restaurant.getAddress().getFullAddress(),
        restaurant.getContactInfo().getEmail(),
        restaurant.getContactInfo().getMobile(),
        restaurant.getOpeningHours(),
        restaurant.getNumRating(),
        restaurant.getImages(),
        restaurant.getCreatedAt(),
        restaurant.isOpen()
    );
  }


}
