package com.cyro.cravekart.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "addresses")
public class Address {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String firstName;
  private String lastName;

  private String streetAddress;

  private String landmark;

  private String city;

  private String state;

  private String postalCode;

  private String country;

  @Builder.Default
  private Boolean isDefault = false;

  private Double latitude;
  private Double longitude;

  private String deliveryInstruction;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "customer_id")
  @JsonIgnore
  private Customer customer;

  @JsonIgnore
  @OneToOne(fetch = FetchType.LAZY)
  private Restaurant restaurant;

  @CreationTimestamp
  private LocalDateTime createdAt;
  @UpdateTimestamp
  private LocalDateTime updatedAt;

  public String getFullAddress() {
    return Stream.of(streetAddress, city, state, postalCode, country)
        .filter(Objects::nonNull)
        .filter(s -> !s.isBlank())
        .collect(Collectors.joining(", "));
  }
}
