package com.cyro.cravekart.repository;

import com.cyro.cravekart.models.Order;
import com.cyro.cravekart.models.enums.OrderStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

  List<Order> findByCustomerId(Long customerId);

  List<Order> findByRestaurantId(Long restaurantId);

  void deleteByCustomerId(Long customerId);

  List<Order> findByRestaurantIdAndOrderStatus(Long restaurantId, OrderStatus orderStatus);

  boolean existsByDeliveryPartnerIdAndOrderStatusNot(
      Long deliveryPartnerId, OrderStatus orderStatus);

  List<Order> findByCustomerIdOrderByCreatedAtDesc(Long customerId);

  List<Order> findByRestaurantIdAndOrderStatusIn(Long restaurantId, List<OrderStatus> statuses);

  Optional<Order> findByIdempotencyKey(String idempotencyKey);

  List<Order> findByRestaurantIdOrderByCreatedAtDesc(Long id);

  List<Order> findByDeliveryPartnerIdAndOrderStatusIn(
      Long deliveryPartnerId, List<OrderStatus> statuses);
}
