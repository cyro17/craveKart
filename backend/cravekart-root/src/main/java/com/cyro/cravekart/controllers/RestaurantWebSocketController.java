package com.cyro.cravekart.controllers;

import com.cyro.cravekart.config.kafka.KafkaTopicConfiguration;
import com.cyro.cravekart.exception.ResourceNotFoundException;
import com.cyro.cravekart.models.Order;
import com.cyro.cravekart.models.enums.OrderStatus;
import com.cyro.cravekart.repository.OrderRepository;
import com.cyro.cravekart.service.impl.OutboxServiceImpl;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Slf4j
@Controller
public class RestaurantWebSocketController {

  private final OrderRepository orderRepository;
  private final OutboxServiceImpl outboxService;
  private final SimpMessagingTemplate messagingTemplate;

  // accept
  @MessageMapping("/order.accept")
  @Transactional
  public void acceptOrder(@Payload Map<String, Long> payload) {
    Long orderId = payload.get("orderId");
    Order order = this.fetchOrder(orderId);

    if (order.getOrderStatus() != OrderStatus.CONFIRMED) {
      log.warn("acceptOrder called on order {} with status {} ", orderId, order.getOrderStatus());
    }

    order.setOrderStatus(OrderStatus.ACCEPTED);
    order.setAcceptedAt(LocalDateTime.now());
    orderRepository.save(order);

    notifyCustomer(order, "ACCEPTED", "Restaurant accepted your order");

    outboxService.save(
        KafkaTopicConfiguration.ORDER_CONFIRMED,
        order.getRestaurantId().toString(),
        "OrderAcceptedEvent",
        Map.of(
            "orderId",
            orderId,
            "restaurantId",
            order.getRestaurantId(),
            "customerId",
            order.getCustomerId(),
            "status",
            "ACCEPTED"));

    log.info("Order accepted: {} by restaurant {} ", orderId, order.getRestaurantId());
  }

  //  REJECT ORDER

  @MessageMapping("/order.reject")
  public void rejectOrder(@Payload Map<String, Long> payload) {
    Long orderId = payload.get("orderId");
  }

  //  READY FOR PICKUP

  @MessageMapping("/order.ready")
  @Transactional
  public void markReady(@Payload Map<String, Long> payload) {

    Long orderId = payload.get("orderId");
    Order order = this.fetchOrder(orderId);

    // if order is not in prep state, -> throw error

    if (order.getOrderStatus() != OrderStatus.PREPARING) {
      log.warn("Mark ready called on order {} with status {}", orderId, order.getOrderStatus());
      return;
    }

    order.setOrderStatus(OrderStatus.READY_FOR_PICKUP);
    orderRepository.save(order);

    // notify customer
    this.notifyCustomer(
        order, "READY_FOR_PICKUP", "Your order is ready ! Delivery partner on the way.");

    outboxService.save(
        "order-ready",
        order.getRestaurantId().toString(),
        "OrderReadyEvent",
        Map.of(
            "orderId", orderId,
            "restaurantId", order.getRestaurantId(),
            "customerId", order.getCustomerId()));

    log.info("Order{} marked READY FOR PICKUP", orderId);
  }

  //  UTIL

  private Order fetchOrder(Long orderId) {
    return orderRepository
        .findById(orderId)
        .orElseThrow(() -> new ResourceNotFoundException("Order not found: " + orderId));
  }

  private void notifyCustomer(Order order, String status, String message) {
    messagingTemplate.convertAndSend(
        "/topic/orders",
        order.getId(),
        Map.of(
            "orderId", order.getId(),
            "status", status,
            "message", message));
  }
}
