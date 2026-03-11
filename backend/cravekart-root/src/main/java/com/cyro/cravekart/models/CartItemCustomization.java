package com.cyro.cravekart.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "cart_item_customization")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemCustomization {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "cart_item_id", nullable = false)
  private CartItem cartItem;

  @Column(nullable = false)
  private String ingredientName;

  @Column(nullable = false)
  private String action;

  @Column(precision = 10, scale = 2)
  private BigDecimal priceDelta;

}
