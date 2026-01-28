package com.cyro.cravekart.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Getter
@Setter
@ToString(exclude = {"foods", "orders", "favoritedBy"})
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(
    name = "restaurant",
    indexes = {
        @Index(name = "idx_restaurant_partner", columnList = "partner_id"),
        @Index(name = "idx_restaurant_open", columnList = "open"),
        @Index(name = "idx_restaurant_cuisine", columnList = "cuisineType"),
        @Index(name = "idx_restaurant_created", columnList = "createdAt"),
        @Index(name = "idx_restaurant_open_cuisine", columnList = "open,cuisineType"),
        @Index(name = "idx_restaurant_open_created", columnList = "open,createdAt")
    }
)
public class Restaurant {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @EqualsAndHashCode.Include
  private Long id;

  @Column(nullable = false)
  private String name;

  private String description;

  @Column(nullable = false)
  private String cuisineType;

  @OneToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "partner_id", nullable = false, unique = true)
  private RestaurantPartner restaurantPartner;

  @ManyToMany(mappedBy = "favoriteRestaurants")
  private Set<Customer> favoritedBy = new HashSet<>();

  @OneToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "address_id")
  private Address address;

  @Embedded
  private ContactInfo contactInfo;

  private String openingHours;

  private int ratingCount;

  @ElementCollection
  @CollectionTable(
      name = "restaurant_images",
      joinColumns = @JoinColumn(name = "restaurant_id"),
      indexes = {
          @Index(name = "idx_restaurant_images_restaurant", columnList = "restaurant_id")
      }
  )
  @Column(name = "image_url", length = 1000)
  private List<String> images = new ArrayList<>();

  private boolean open = false;

  @JsonIgnore
  @OneToMany(mappedBy = "restaurant",
      cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Food> foods = new ArrayList<>();


  @CreationTimestamp
  private LocalDateTime createdAt;

  @UpdateTimestamp
  private LocalDateTime updatedAt;

}
