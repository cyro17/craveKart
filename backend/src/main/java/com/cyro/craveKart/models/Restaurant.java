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
public class Restaurant {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @EqualsAndHashCode.Include
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "owner_id", nullable = false)
  private User owner;

  private String name;
  private String description;
  private String cuisineType;

  @ManyToMany(mappedBy = "favorites")
  @JsonIgnore
  private Set<User> favoritedBy = new HashSet<>();

  @ManyToOne
  @JoinColumn(name = "address_id")
  private Address address;

  @Embedded
  private ContactInfo contactInfo;

  private String openingHours;

//  @JsonIgnore
//  @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
//  private List<Order> orders = new ArrayList<>();

  private int numRating;

  @ElementCollection
  @CollectionTable(
      name = "restaurant_images",
      joinColumns = @JoinColumn(name = "restaurant_id")
  )
  @Column(name = "image_url", length = 1000)
  private List<String> images;

  private LocalDateTime registrationDate;

  private boolean open;

  @JsonIgnore
  @OneToMany(mappedBy = "restaurant",
      cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Food> foods = new ArrayList<>();


  @CreationTimestamp
  private LocalDateTime createdAt;

  @UpdateTimestamp
  private LocalDateTime updatedAt;

  public Object getem() {
  }

  public Object getrain() {
  }
}
