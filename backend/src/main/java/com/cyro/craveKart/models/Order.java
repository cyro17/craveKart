package com.cyro.cravekart.models;

import com.cyro.cravekart.models.enums.OrderStatus;
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
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(
    name = "orders",
    indexes = {
        @Index(name = "idx_orders_customer_id", columnList = "customer_id"),
        @Index(name = "idx_orders_order_status", columnList = "orderStatus"),
        @Index(name = "idx_orders_restaurant_id", columnList = "restaurantId"),
        @Index(name = "idx_orders_created_at", columnList = "createdAt"),
        @Index(name = "idx_orders_customer_status", columnList = "customer_id,orderStatus"),
        @Index(name = "idx_orders_restaurant_status", columnList = "restaurantId,orderStatus")
    }
)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id",  nullable = false)
    private User customer;

    private Long restaurantId;
    private String restaurantName;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deliveryAddress_id")
    private Address deliveryAddress;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private Payment payment;

    private Integer totalItem;

    @Column(precision = 10, scale = 2)
    private BigDecimal totalAmount;


    private String paymentGateway;
    private String paymentOrderId;
    private String paymentId;


    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;


}
