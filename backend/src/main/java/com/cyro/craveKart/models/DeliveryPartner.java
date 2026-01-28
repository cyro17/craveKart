package com.cyro.cravekart.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "delivery_partners",
  indexes = {
    @Index(name = "idx_delivery_partner_available", columnList = "available"),
      @Index(name = "idx_delivery_partner_user", columnList = "user_id")
  }
)
@Data
public class DeliveryPartner {

  @Id
  private Long id;

  @OneToOne(fetch = FetchType.LAZY)
  @MapsId
  @JoinColumn(name = "user_id")
  private User user;

  private boolean available = false;
  private boolean verified = false;

  @OneToMany(mappedBy = "deliveryPartner", fetch  = FetchType.LAZY)
  private List<Delivery> deliveryList = new ArrayList<>();

  @CreationTimestamp
  private LocalDateTime createdAt;

  @UpdateTimestamp
  private LocalDateTime updatedAt;

}
