package com.cyro.cravekart.models;

import com.cyro.cravekart.models.enums.DeliveryStatus;
import com.cyro.cravekart.models.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "deliveries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Delivery {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(fetch = FetchType.LAZY,
      optional = false)
  @JoinColumn(name = "order_id", unique = true, nullable = false)
  private Order order;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "delivery_partner_id")
  private DeliveryPartner deliveryPartner;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "pickup_address_id", nullable = false)
  private Address pickupAddress;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "drop_address_id", nullable = false)
  private Address dropoffAddress;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private DeliveryStatus deliveryStatus;

  @Column(nullable = false)
  private BigDecimal deliveryFee;

  private Double distanceKm;

  private Integer estimatedTime; // in minutes

  @CreationTimestamp
  private LocalDateTime createdAt;

  @UpdateTimestamp
  private LocalDateTime updatedAt;

  @PrePersist
  public void onCreate() {
    if (this.deliveryStatus == null) {
      this.deliveryStatus = DeliveryStatus.PENDING_ASSIGNMENT;
    }
  }

}
