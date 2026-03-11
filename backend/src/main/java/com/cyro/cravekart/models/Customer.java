package com.cyro.cravekart.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "customers")
@Data
public class Customer {
  @Id
  private Long id;

  @OneToOne(fetch = FetchType.LAZY)
  @MapsId
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToMany
  @JoinTable(
      name = "customer_favorite_restaurants",
      joinColumns = @JoinColumn(name = "customer_id"),
      inverseJoinColumns = @JoinColumn(name = "restaurant_id"),
      uniqueConstraints = {
          @UniqueConstraint(columnNames = {"customer_id", "restaurant_id"})
      }
  )
  private Set<Restaurant> favoriteRestaurants = new HashSet<>();

  @OneToMany(mappedBy = "customer",
      fetch = FetchType.LAZY,
      cascade = CascadeType.ALL,
      orphanRemoval = true)
  private List<Address> addresses = new ArrayList<>();

  @CreationTimestamp
  private LocalDateTime createdAt;

  @UpdateTimestamp
  private LocalDateTime updatedAt;

  // ===============HELPER===============================

  public void addAddress(Address address) {
    addresses.add(address);
    address.setCustomer(this);
  }

  public void removeAddress(Address address) {
    addresses.remove(address);
    address.setCustomer(null);
  }
}
