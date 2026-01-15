package com.cyro.cravekart.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = {"category", "restaurant", "ingredientItems"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class Food {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @EqualsAndHashCode.Include
  private Long id;

  private String name;
  private String description;

  @Column(precision = 10, scale = 2)
  private BigDecimal price;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id", nullable = false)
  private FoodCategory category;

  @ElementCollection
  @CollectionTable(
      name = "food_images",
      joinColumns = @JoinColumn(name = "food_id")
  )
  @Column(name = "image_url", length = 1000)
  private List<String> images = new ArrayList<>();

  private Boolean available;
  private Boolean vegetarian;
  private Boolean seasonal;

  @ManyToOne
  @JoinColumn(name = "restaurant_id", nullable = false)
  private Restaurant restaurant;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "food_ingredients",
      joinColumns = @JoinColumn(name = "food_id"),
      inverseJoinColumns = @JoinColumn(name = "ingredient_id")
  )
  private Set<IngredientItem> ingredientItems = new HashSet<>();

  @CreationTimestamp
  private LocalDateTime createdAt;
  @UpdateTimestamp
  private LocalDateTime updatedAt;

}
