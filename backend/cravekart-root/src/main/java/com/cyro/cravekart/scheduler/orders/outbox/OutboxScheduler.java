package com.cyro.cravekart.scheduler.orders.outbox;

import com.cyro.cravekart.models.OutboxEvent;
import com.cyro.cravekart.repository.OutboxEventRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
@ConditionalOnProperty(name = "app.kafka.enabled", havingValue = "true")
public class OutboxScheduler {
  private static final int MAX_RETRIES = 3;

  private final OutboxEventRepository outboxEventRepository;
  private final KafkaTemplate<String, Object> kafkaTemplate;

  @Scheduled(fixedDelayString = "${app.outbox.poll-interval-ms:5000}")
  @Transactional
  public void relay() {
    List<OutboxEvent> pending = outboxEventRepository.findPendingEvents();

    if (pending.isEmpty()) return;

    for (OutboxEvent event : pending) {
      try {
        kafkaTemplate.send(event.getTopic(), event.getPayload());
        event.setStatus(OutboxEvent.OutboxStatus.PUBLISHED);
        event.setProcessedAt(LocalDateTime.now());
        log.debug("Published outbox event id {} type {}", event.getId(), event.getEventType());
      } catch (Exception ex) {

        event.setRetryCount(event.getRetryCount() + 1);

        if (event.getRetryCount() >= MAX_RETRIES) {
          event.setStatus(OutboxEvent.OutboxStatus.FAILED);
          log.error(
              "Outbox event permanently failed id {} type {}", event.getId(), event.getEventType());
        } else {
          log.warn(
              "Outbox relay failed (retry {}/{}): id={}",
              event.getRetryCount(),
              MAX_RETRIES,
              event.getId());
        }
      }
    }
  }
}
