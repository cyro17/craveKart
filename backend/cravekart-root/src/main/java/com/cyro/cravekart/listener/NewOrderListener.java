package com.cyro.cravekart.listener;

import com.cyro.cravekart.config.kafka.KafkaTopicConfiguration;
import com.cyro.cravekart.events.order.NewOrderEvent;
import com.cyro.cravekart.service.SseEmitterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class NewOrderListener {
  private final SseEmitterService sseEmitterService;

  @KafkaListener(topics = KafkaTopicConfiguration.ORDER_CONFIRMED, groupId = "order-service")
  public void onOrderConfirmed(NewOrderEvent event) {
    log.info(
        "NewOrderListener: orderId={}, restaurantId={}",
        event.getOrderId(),
        event.getRestaurantId());

    sseEmitterService.pushNewOrder(
        event.getRestaurantId(),
        event.getOrderId(),
        event.getCustomerName(),
        event.getTotalPrice().toString());
  }
}
