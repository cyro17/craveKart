package com.cyro.cravekart.service;

import com.cyro.cravekart.models.enums.OrderStatus;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@Slf4j
@RequiredArgsConstructor
public class SseEmitterServiceImpl implements SseEmitterService {

  private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();
  private final String PAYMENT_RECEIVED_EVENT = "payment-ready";

  // ── Register ──────────────────────────────────────────────

  @Override
  public SseEmitter register(Long customerId) {

    // check if emitter exists, if it does , then close it
    SseEmitter existingEmitter = emitters.get(customerId);

    // close stale emitter if exist
    if (existingEmitter != null) {
      existingEmitter.complete();
    }

    // create new emitter with timeout ot 2 min
    SseEmitter emitter = new SseEmitter(120_000L);

    emitter.onCompletion(() -> emitters.remove(customerId));

    emitter.onTimeout(
        () -> {
          log.warn("SSE timeout for orderId = {}", customerId);
          emitters.remove(customerId);
        });

    emitter.onError(
        e -> {
          log.error("SSE emitter error for orderId = {}", customerId, e);
          emitters.remove(customerId);
        });

    emitters.put(customerId, emitter);
    log.info("SSE emitter registered for orderId = {}", customerId);

    return emitter;
  }

  // send clientSecret to frontend ─────────────────

  @Override
  public void pushClientSecret(Long orderId, Long customerId, String clientSecret) {

    log.info("🔍 pushClientSecret called. orderId={} customerId={}", orderId, customerId);
    log.info("🔍 Active emitters: {}", emitters.keySet()); // shows all registered keys

    // fetch customer emitter
    SseEmitter emitter = emitters.get(customerId);

    if (emitter == null) {
      log.warn("SSE emitter not found for orderId = {}", customerId);
      return;
    }

    try {
      // send event

      emitter.send(
          SseEmitter.event()
              .name("payment-ready")
              .data(
                  Map.of(
                      "customerId", customerId,
                      "clientSecret", clientSecret,
                      "orderId", orderId)));
      log.info("SSE emitter sent for orderId = {}", orderId);

      // mark complete and remove it
      emitter.complete();
      emitters.remove(customerId);
    } catch (IOException e) {
      log.error("Failed to push SSE event for orderId = {}", orderId, e);
      emitters.remove(customerId);
    }
  }

  // ── Step 2: Stripe confirmed — payment received ───────────

  @Override
  public void pushPaymentReceived(Long customerId, Long orderId) {
    this.pushEvent(
        customerId,
        "payment-received",
        Map.of(
            "customerID",
            customerId,
            "orderId",
            orderId,
            "status",
            OrderStatus.CONFIRMED.name(),
            "message",
            "Payment Received! waiting for restaurant to accept"));
  }

  @Override
  public void pushOrderStatusUpdate(
      Long customerId, Long orderId, OrderStatus orderStatus, String message) {
    this.pushEvent(
        customerId,
        "order-status",
        Map.of(
            "customerId", customerId,
            "orderId", orderId,
            "status", orderStatus.name(),
            "message", message));

    if (orderStatus == OrderStatus.DELIVERED
        || orderStatus == OrderStatus.CANCELLED
        || orderStatus == OrderStatus.PAYMENT_FAILED) {
      SseEmitter emitter = emitters.remove(customerId);
      if (emitter != null) emitter.complete();
    }
  }

  @Override
  public void pushPaymentFailed(Long orderId, Long customerId, String reason) {
    this.pushEvent(
        customerId, "payment-failed", Map.of("status", "PAYMENT_FAILED", "message", reason));
  }

  //  ==================== helper ===============================
  private void pushEvent(Long customerId, String eventName, Object data) {
    SseEmitter emitter = emitters.get(customerId);
    if (emitter == null) {
      log.warn("SSE emitter not found for orderId = {}", customerId);
      return;
    }
    try {
      emitter.send(SseEmitter.event().name(eventName).data(data));
    } catch (IOException e) {
      log.error("Failed to push SSE event for orderId = {}", customerId, e);
      emitters.remove(customerId);
    }
  }
}
