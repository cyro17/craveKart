package com.cyro.cravekart.response;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateRestaurantResponse {
  private Long id;
  private String username;
  private String name;
}
