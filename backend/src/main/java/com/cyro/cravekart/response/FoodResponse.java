package com.cyro.cravekart.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class FoodResponse {
  private Long id;
  private String name;
  private String description;
  private BigDecimal price;
  private boolean vegetarian;
  private List<String> images;
}
