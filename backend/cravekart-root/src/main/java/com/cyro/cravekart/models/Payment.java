package com.cyro.cravekart.models;

import com.cyro.cravekart.models.enums.PaymentProvider;
import com.cyro.cravekart.models.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.coyote.http11.filters.SavedRequestInputFilter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(
    name = "payments",
    indexes = {
        @Index(name = "idx_payment_order_id", columnList = "orderId"),
        @Index(name = "idx_payment_status", columnList = "status")
    }
)
@Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // order reference -------------------------------
    @Column(nullable = false, unique = true)
    private Long orderId;

    @Column(nullable = false)
    private Long customerId;

    // amount -----------------------------------------
    @Column(nullable = false)
    private Long amount;

    @Column(nullable = false, length = 10)
    private String currency;

// provider -------------------------------
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentProvider provider;

    // ─── Stripe PaymentIntent Fields ─────────────────────────────

    @Column(nullable = true)
    private String stripePaymentIntentId;

    @Column(length = 512)
    private String clientSecret;

    private String stripeEventId;

    private String idempotencyKey;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    @Builder.Default
    private Integer retryCount = 0;

    @Column(length = 1000)
    private String failureReason;

    private String failureCode;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private LocalDateTime paidAt;
    private LocalDateTime failedAt;

    @Version
    private Long version;

}
