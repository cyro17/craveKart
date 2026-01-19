package com.cyro.cravekart.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "order_items")
public class OrderItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String foodName;

  // snapshots

  // restaurant details
  @Column(name = "Restaurant ID")
  private Long restaurantId;
  @Column(name = "Restaurant Name")
  private String restaurantName;

  // order item price and qty
  @Column(precision = 10, scale = 2, name = "Price")
  private BigDecimal foodPrice;

  @Column(name = "Quantity")
  private Integer quantity;

  @Column(precision = 10, scale = 2, name = "Total")
  private BigDecimal totalPrice;


  @ElementCollection
  @CollectionTable(
      name = "order_item_ingredients",
      joinColumns = @JoinColumn(name = "order_item_id")
  )
  @Column(name = "ingredient_name")
  private List<String> ingredients = new ArrayList<>();

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id", nullable = false)
  private Order order;

  @CreationTimestamp
  private LocalDateTime createdAt;
  @UpdateTimestamp
  private LocalDateTime updatedAt;

}
