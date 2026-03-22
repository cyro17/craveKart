package com.cyro.cravekart.service;

import org.springframework.stereotype.Service;

@Service
public interface OutboxService {
  public void save(String topic, String eventType, Object payload);
}
