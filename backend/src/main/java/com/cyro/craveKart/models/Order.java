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

    // customer snapshot
    @Column(name = "customer_id", nullable = false)
    private Long customerId;
    @Column(name = "customer_name", nullable = false)
    private String customerName;
    @Column(name = "customer_phone", nullable = false)
    private String customerPhone;

    // restaurant snapshot
    @Column(name = "restaurant_id", nullable = false)
    private Long restaurantId;
    @Column(name = "restaurant_name",  nullable = false)
    private String restaurantName;
    @Column(name = "restaurant_address", nullable = false)
    private String restaurantAddress;

    // delivery partner
    @Column(name = "delivery_partner_id")
    private Long deliveryPartnerId;
    @Column(name = "delivery_partner_name")
    private String deliveryPartnerName;

    // delivery snapshot
    @Column(name = "delivery_address_line",  nullable = false)
    private String deliveryAddressLine;
//    @Column(name = "delivery_city",   nullable = false)
//    private String deliveryCity;
//    @Column(name = "delivery_pincode",   nullable = false)
//    private String deliveryPinCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false)
    private OrderStatus orderStatus;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "deliveryAddress_id")
//    private Address deliveryAddress;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

//    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
//    private Payment payment;

    @Column(name = "total_items",  nullable = false)
    private Integer totalItems;

    private BigDecimal subtotal;
    private BigDecimal taxAmount;
    private BigDecimal deliveryFee;
    private BigDecimal restaurantCharge;
    private BigDecimal discount;
    private BigDecimal platformFee;

    private String specialInstruction;


    @Column(name = "total_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice;

    private LocalDateTime acceptedAt;
    private LocalDateTime preparedAt;
    private LocalDateTime pickedUpAt;
    private LocalDateTime deliveredAt;
    private LocalDateTime paidAt;
    private LocalDateTime cancelledAt;


    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;




//    @PrePersist
//    public void onCreate(){
//        this.createdAt = LocalDateTime.now();
//        this.orderStatus = OrderStatus.CREATED;
//    }
}
