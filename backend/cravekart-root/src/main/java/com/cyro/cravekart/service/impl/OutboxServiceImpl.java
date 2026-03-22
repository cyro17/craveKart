package com.cyro.cravekart.service.impl;

import com.cyro.cravekart.models.OutboxEvent;
import com.cyro.cravekart.repository.OutboxEventRepository;
import com.cyro.cravekart.service.OutboxService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class OutboxServiceImpl implements OutboxService {
  private final ObjectMapper objectMapper;
  private final OutboxEventRepository outboxEventRepository;

  @Override
  public void save(String topic, String eventType, Object payload) {
    try {
      String json = objectMapper.writeValueAsString(payload);
      OutboxEvent event =
          OutboxEvent.builder()
              .topic(topic)
              .eventType(eventType)
              .payload(json)
              .status(OutboxEvent.OutboxStatus.PENDING)
              .build();

      outboxEventRepository.save(event);
      log.debug("Outbox event saved : type {} topic {} ", eventType, topic);

    } catch (Exception e) {
      throw new RuntimeException("Failed to serialize outbox event: ", e);
    }
  }
}
