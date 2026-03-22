package com.cyro.cravekart.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "reviews")
public class Review {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Long orderId;

  @Column(nullable = false)
  private Long customerId;

  @Column(nullable = false)
  private Long restaurantId;

  @Column(nullable = false)
  private Integer rating;

  private String comment;

  @CreationTimestamp private LocalDateTime createdAt;
}
