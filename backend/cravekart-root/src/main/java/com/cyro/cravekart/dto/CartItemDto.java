package com.cyro.cravekart.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto {

  private Long id;
  private FoodItemDto foodItemDto;
  private Integer quantity;
  private CartDto cart;
}
