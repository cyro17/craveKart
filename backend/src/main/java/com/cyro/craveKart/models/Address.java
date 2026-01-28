package com.cyro.cravekart.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "addresses")
public class Address {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String firstName;
  private String lastName;

  private String streetAddress;

  private String city;

  private String state;

  private String postalCode;

  private String country;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "customer_id", nullable = false)
  private Customer customer;

  @OneToOne(fetch = FetchType.LAZY,
      mappedBy = "address")
  private Restaurant restaurant;

  @CreationTimestamp
  private LocalDateTime createdAt;
  @UpdateTimestamp
  private LocalDateTime updatedAt;

  public String getFullAddress() {
    return String.join(", ",
        streetAddress != null ? streetAddress: "",
        city != null ? city : "",
        state != null ? state : "",
        postalCode != null ? postalCode : "");
  }
}
