package com.cyro.cravekart.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateRestaurantResponse {
  private Long id;
//  private String partnerName;
  private String name;
  private boolean assigned;
}
