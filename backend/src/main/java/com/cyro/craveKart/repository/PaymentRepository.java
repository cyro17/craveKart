package com.cyro.cravekart.repository;

import com.cyro.cravekart.models.Payment;
import com.cyro.cravekart.models.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {

  Optional<Payment> findByOrderId(Long orderId);

  Optional<Payment> findByStripePaymentIntentId(String stripePaymentIntentId);

  boolean existsByStripeEventId(String stripeEventId);
  boolean existsByOrderId(Long orderId);
  Optional<Payment> findByIdempotencyKey(String idempotencyKey);

  long countByStatus(PaymentStatus status);


}
