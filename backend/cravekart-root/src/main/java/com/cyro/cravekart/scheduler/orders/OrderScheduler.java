package com.cyro.cravekart.scheduler.orders;

import com.cyro.cravekart.models.Order;
import com.cyro.cravekart.models.enums.OrderStatus;
import com.cyro.cravekart.repository.OrderRepository;
import com.cyro.cravekart.service.SseEmitterService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderScheduler {

  private final OrderRepository orderRepository;
  private final SseEmitterService sseEmitterService;

  @Scheduled(fixedRate = 60000)
  public void autoCancelUnacceptedOrders() {
    LocalDateTime cutoff = LocalDateTime.now().minusMinutes(3);
    List<Order> orders =
        orderRepository.findByOrderStatusAndConfirmedAtBefore(OrderStatus.CONFIRMED, cutoff);

    orders.forEach(
        order -> {
          order.setOrderStatus(OrderStatus.CANCELLED);
          order.setCancelledAt(LocalDateTime.now());
          orderRepository.save(order);

          // notify customer
          sseEmitterService.pushOrderStatusUpdate(
              order.getCustomerId(),
              order.getId(),
              OrderStatus.CANCELLED,
              "No restaurant response, Order auto-cancelled, Refund Initiated");
        });
  }
}
