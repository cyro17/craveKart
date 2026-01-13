package com.cyro.cravekart.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(exclude = {"category", "restaurant", "ingredientItems"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class Food {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String description;
  private Long price;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id", nullable = false)
  private Category category;

  @ElementCollection
  @CollectionTable(
      name = "food_images",
      joinColumns = @JoinColumn(name = "food_id")
  )
  @Column(name = "image_url", length = 1000)
  private List<String> images = new ArrayList<>();

  private boolean available;

  @ManyToOne
  @JoinColumn(name = "restaurant_id", nullable = false)
  private Restaurant restaurant;

  private boolean vegetarian;
  private boolean seasonal;

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
