package com.cyro.cravekart.listener;

import com.cyro.cravekart.events.PaymentFailedEvent;
import com.cyro.cravekart.events.PaymentSuccessEvent;
import com.cyro.cravekart.models.Order;
import com.cyro.cravekart.models.enums.OrderStatus;
import com.cyro.cravekart.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderPaymentListener {

  private final OrderRepository orderRepository;

  @KafkaListener(
      topics = "payment-success",
      groupId = "order-group",
      containerFactory = "kafkaListenerContainerFactory"
  )
  public void handleSuccess(PaymentSuccessEvent event){
    Order order = orderRepository.findById(event.getOrderId()).orElseThrow();
    order.setOrderStatus(OrderStatus.PAID);
  }

  @KafkaListener(
      topics = "payment-failed",
      groupId = "order-group",
      containerFactory = "kafkaListenerContainerFactory"
  )
  public void handleFailure(PaymentFailedEvent event){
    Order order = orderRepository.findById(event.getOrderId()).orElseThrow();
    order.setOrderStatus(OrderStatus.PAYMENT_FAILED);

    log.info("Order {} marked as FAILED", event.getOrderId());
  }

}
