package com.cyro.cravekart.repository;

import com.cyro.cravekart.models.DeliveryPartner;
import com.cyro.cravekart.models.Order;
import com.cyro.cravekart.models.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

  List<Order>  findByCustomerId(Long customerId);
  List<Order> findByRestaurantId(Long restaurantId);

  List<Order> findByRestaurantIdAndOrderStatus(
      Long restaurantId, OrderStatus orderStatus);

  boolean existsByDeliveryPartnerIdAndOrderStatusNot(
      Long deliveryPartnerId, OrderStatus orderStatus
  );

}
