package com.cyro.cravekart.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class SseEmitterServiceImpl implements SseEmitterService {
  private final Map<Long, SseEmitter> emitters =
      new ConcurrentHashMap<>();

  @Override
  public SseEmitter register(Long orderId) {
    SseEmitter emitter = new SseEmitter(120_000L);

    emitter.onCompletion(
        ()-> emitters.remove(orderId)
    );
    emitter.onTimeout(()->{
      log.warn("SSE timeout for orderId = {}", orderId);
      emitters.remove(orderId);
    });

    emitter.onError(e-> {
      log.error("SSE emitter error for orderId = {}", orderId, e);
      emitters.remove(orderId);
    });

    emitters.put(orderId, emitter);
    log.info("SSE emitter registered for orderId = {}", orderId);

    return  emitter;
  }

  @Override
  public void pushClientSecret(Long orderId,
                   Long customerId,
                   String clientSecret) {
    SseEmitter emitter = emitters.get(orderId);

    if(emitter == null){
      log.warn("SSE emitter not found for orderId = {}", orderId);
      return;
    }

    try {
      emitter.send(SseEmitter.event()
          .name("payment-ready")
          .data(Map.of(
              "orderId", orderId,
              "clientSecret", clientSecret,
              "customerId", customerId
          ))
      );
      log.info("SSE emitter sent for orderId = {}", orderId);
      emitter.complete();
      emitters.remove(orderId);
    } catch (IOException e){
      log.error("Failed to push SSE event for orderId = {}", orderId, e);
      emitters.remove(orderId);
    }
  }

  @Override
  public void pushOrderConfirmed(Long orderId, Long customerId) {

  }

  @Override
  public void pushPaymentFailed(Long orderId, Long customerId, String reason) {

  }

//  ==================== helper ===============================
  
}
