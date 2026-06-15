package com.cyro.cravekart.listener;

import com.cyro.cravekart.events.order.NewOrderEvent;
import com.cyro.cravekart.models.ProcessedEvent;
import com.cyro.cravekart.repository.ProcessedEventRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class RestaurantSagaListener {

  private static final String CONSUMER_NAME = "RestaurantSagaListener";
  private final SimpMessagingTemplate messagingTemplate;
  private final ProcessedEventRepository processedEventRepository;
  private final ObjectMapper objectMapper;

  @KafkaListener(
      topics = "order-confirmed",
      groupId = "restaurant-service",
      containerFactory = "stringKafkaListenerContainerFactory")
  @Transactional
  public void handleNewOrder(ConsumerRecord<String, String> record) {
    String eventId = record.topic() + "-" + record.partition() + "-" + record.offset();

    if (processedEventRepository.existsByEventId(eventId)) {
      log.warn("Duplicate event skipped: {}", eventId);
      return;
    }

    processedEventRepository.save(
        ProcessedEvent.builder().eventId(eventId).consumer(CONSUMER_NAME).build());

    NewOrderEvent event;
    try {
      event = objectMapper.readValue(record.value(), NewOrderEvent.class);
    } catch (Exception e) {
      log.error("Failed to deserialize NewOrderEvent: {}", record.value(), e);
      throw new RuntimeException("Failed to deserialize NewOrderEvent", e);
    }

    String destination = "/topic/restaurant." + event.getRestaurantId();
    messagingTemplate.convertAndSend(destination, event);

    log.info(
        "Order {} pushed to restauratn {} via STOMP", event.getOrderId(), event.getRestaurantId());
  }
}
