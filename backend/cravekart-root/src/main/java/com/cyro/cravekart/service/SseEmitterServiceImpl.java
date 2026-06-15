package com.cyro.cravekart.service;

import com.cyro.cravekart.models.enums.OrderStatus;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * Service for managing Server-Sent Events (SSE) connections.
 *
 * <p>Maintains separate real-time communication channels for:
 *
 * <ul>
 *   <li><b>Customers</b>: Order status updates (payment → confirmation → delivery → delivered)
 *   <li><b>Restaurants</b>: New incoming orders to accept/reject
 * </ul>
 *
 * <p>Each channel has distinct lifecycle management, timeout strategies, and event types. Uses
 * ConcurrentHashMap to handle concurrent connections from multiple users.
 *
 * @author CraveKart Team
 * @version 1.0
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SseEmitterServiceImpl implements SseEmitterService {

  /**
   * Stores active SSE emitter connections for customers.
   *
   * <p><b>Key</b>: customerId (Long) <b>Value</b>: SseEmitter instance for that customer
   *
   * <p><b>Lifecycle</b>:
   *
   * <ul>
   *   <li>2-minute timeout (customer may close browser/tab)
   *   <li>Auto-removes on timeout, error, or completion
   *   <li>Completes after terminal order states (DELIVERED, CANCELLED, PAYMENT_FAILED)
   * </ul>
   *
   * <p><b>Events sent</b>: payment-ready, payment-received, order-status, payment-failed
   */
  private final Map<Long, SseEmitter> customerEmitters = new ConcurrentHashMap<>();

  /**
   * Stores active SSE emitter connections for restaurant partners.
   *
   * <p><b>Key</b>: restaurantId (Long) <b>Value</b>: SseEmitter instance for that restaurant
   *
   * <p><b>Lifecycle</b>:
   *
   * <ul>
   *   <li>No timeout (0L) — restaurant dashboard stays open indefinitely
   *   <li>Auto-removes on error or completion
   *   <li>Persists across multiple order events
   * </ul>
   *
   * <p><b>Events sent</b>: new-order (when customer places order)
   */
  private final Map<Long, SseEmitter> restaurantEmitters = new ConcurrentHashMap<>();

  // ═══════════════════════════════════════════════════════════════════════════════════════════════
  // ── CUSTOMER CHANNEL ───────────────────────────────────────────────────────────────────────────
  // ═══════════════════════════════════════════════════════════════════════════════════════════════

  /**
   * Registers a new SSE emitter for a customer.
   *
   * <p>Lifecycle:
   *
   * <ul>
   *   <li>Closes any existing stale emitter for this customer
   *   <li>Creates new emitter with 2-minute timeout
   *   <li>Attaches cleanup callbacks (onCompletion, onTimeout, onError)
   *   <li>Stores in customerEmitters map
   * </ul>
   *
   * @param customerId the ID of the customer registering for order updates
   * @return the newly created SseEmitter
   */
  @Override
  public SseEmitter register(Long customerId) {

    // Check if a stale emitter already exists for this customer (e.g., from browser reconnect)
    SseEmitter existingEmitter = customerEmitters.get(customerId);

    // Close the stale emitter to free resources and clean up connections
    if (existingEmitter != null) {
      existingEmitter.complete();
    }

    // Create new emitter with 2-minute timeout
    // Timeout ensures connection doesn't hang indefinitely if customer closes browser
    SseEmitter emitter = new SseEmitter(120_000L);

    // Callback: triggered when client closes connection gracefully (HTTP close frame)
    emitter.onCompletion(() -> customerEmitters.remove(customerId));

    // Callback: triggered when 2-minute timeout expires (no activity from client)
    emitter.onTimeout(
        () -> {
          log.warn("SSE timeout for customerId = {}", customerId);
          customerEmitters.remove(customerId);
        });

    // Callback: triggered on IO error or network issue
    emitter.onError(
        e -> {
          log.error("SSE emitter error for customerId = {}", customerId, e);
          customerEmitters.remove(customerId);
        });

    // Register the new emitter in the map
    customerEmitters.put(customerId, emitter);
    log.info("SSE emitter registered for customerId = {}", customerId);

    return emitter;
  }

  /**
   * Sends Stripe clientSecret to frontend over SSE for payment processing.
   *
   * <p>Called after order is created and payment intent is initiated. Customer frontend uses
   * clientSecret to confirm payment with Stripe.
   *
   * <p>Flow:
   *
   * <ul>
   *   <li>1. Order Service creates order → Payment Service initiates Stripe PaymentIntent
   *   <li>2. Payment Service gets clientSecret → calls this method
   *   <li>3. SSE event pushed to customer frontend
   *   <li>4. Frontend receives clientSecret and displays Stripe payment form
   *   <li>5. Emitter is completed (single-event channel for payment flow)
   * </ul>
   *
   * @param orderId the ID of the order
   * @param customerId the ID of the customer
   * @param clientSecret the Stripe client secret for payment intent confirmation
   */
  @Override
  public void pushClientSecret(Long orderId, Long customerId, String clientSecret) {

    log.info("🔍 pushClientSecret called. orderId={} customerId={}", orderId, customerId);
    log.info(
        "🔍 Active emitters: {}", customerEmitters.keySet()); // Debug: shows all registered keys

    // Retrieve the registered SSE emitter for this customer
    SseEmitter emitter = customerEmitters.get(customerId);

    // If no emitter found, customer may have closed browser before payment form loaded
    if (emitter == null) {
      log.warn("SSE emitter not found for customerId = {}", customerId);
      return;
    }

    try {
      // Send payment-ready event to frontend with clientSecret
      // Frontend uses this to initialize Stripe payment element
      emitter.send(
          SseEmitter.event()
              .name("payment-ready")
              .data(
                  Map.of(
                      "customerId", customerId,
                      "clientSecret", clientSecret,
                      "orderId", orderId)));
      log.info("SSE event 'payment-ready' sent for orderId = {}", orderId);

      // Complete emitter and remove from map (payment flow moves to webhook handling)
      emitter.complete();
      customerEmitters.remove(customerId);

    } catch (IOException e) {
      // Network error while sending SSE event
      log.error("Failed to push SSE event 'payment-ready' for orderId = {}", orderId, e);
      customerEmitters.remove(customerId);
    }
  }

  /**
   * Notifies customer that payment has been received and order is confirmed.
   *
   * <p>Called by Payment Service after Stripe webhook confirms payment success. Order moves to
   * CONFIRMED state, waiting for restaurant acceptance.
   *
   * @param customerId the ID of the customer
   * @param orderId the ID of the confirmed order
   */
  @Override
  public void pushPaymentReceived(Long customerId, Long orderId) {
    this.pushEvent(
        customerId,
        "payment-received",
        Map.of(
            "customerId",
            customerId,
            "orderId",
            orderId,
            "status",
            OrderStatus.CONFIRMED.name(),
            "message",
            "Payment Received! Waiting for restaurant to accept"));
  }

  /**
   * Pushes order status update to customer.
   *
   * <p>Called whenever order transitions to a new state:
   *
   * <ul>
   *   <li>CONFIRMED → restaurant accepted order
   *   <li>PREPARING → kitchen started preparing
   *   <li>READY → order ready for pickup
   *   <li>OUT_FOR_DELIVERY → delivery partner picked up
   *   <li>DELIVERED → order reached customer (terminal state)
   *   <li>CANCELLED → order cancelled by restaurant or system (terminal state)
   * </ul>
   *
   * <p>After terminal states (DELIVERED, CANCELLED, PAYMENT_FAILED), emitter is automatically
   * completed and removed to free resources.
   *
   * @param customerId the ID of the customer
   * @param orderId the ID of the order
   * @param orderStatus the new status of the order
   * @param message human-readable status message for customer
   */
  @Override
  public void pushOrderStatusUpdate(
      Long customerId, Long orderId, OrderStatus orderStatus, String message) {

    // Send the status update event
    this.pushEvent(
        customerId,
        "order-status",
        Map.of(
            "customerId", customerId,
            "orderId", orderId,
            "status", orderStatus.name(),
            "message", message));

    // For terminal states, close the emitter connection to free resources
    // Customer no longer needs to receive updates after order is delivered/cancelled
    if (orderStatus == OrderStatus.DELIVERED
        || orderStatus == OrderStatus.CANCELLED
        || orderStatus == OrderStatus.PAYMENT_FAILED) {
      SseEmitter emitter = customerEmitters.remove(customerId);
      if (emitter != null) {
        emitter.complete();
      }
    }
  }

  /**
   * Notifies customer that payment processing failed.
   *
   * <p>Called by Payment Service when Stripe payment fails (declined card, insufficient funds,
   * etc). Order remains in PENDING state and may be retried.
   *
   * @param orderId the ID of the order
   * @param customerId the ID of the customer
   * @param reason human-readable error reason (e.g., "Card declined", "Insufficient funds")
   */
  @Override
  public void pushPaymentFailed(Long orderId, Long customerId, String reason) {
    this.pushEvent(
        customerId, "payment-failed", Map.of("status", "PAYMENT_FAILED", "message", reason));
  }

  // ═══════════════════════════════════════════════════════════════════════════════════════════════
  // ── RESTAURANT PARTNER CHANNEL ─────────────────────────────────────────────────────────────────
  // ═══════════════════════════════════════════════════════════════════════════════════════════════

  /**
   * Registers a new SSE emitter for a restaurant partner.
   *
   * <p>Lifecycle:
   *
   * <ul>
   *   <li>Closes any existing stale emitter for this restaurant
   *   <li>Creates new emitter with NO timeout (0L) — restaurant dashboard runs indefinitely
   *   <li>Attaches cleanup callbacks (onCompletion, onError)
   *   <li>Stores in restaurantEmitters map
   * </ul>
   *
   * <p><b>Key difference from customer channel:</b> Restaurant emitters have no timeout because
   * partner dashboards stay open all day. We rely on explicit close or error handling, not
   * timeout-based cleanup.
   *
   * @param restaurantId the ID of the restaurant registering to receive orders
   * @return the newly created SseEmitter
   */
  @Override
  public SseEmitter registerRestaurant(Long restaurantId) {

    // Check if a stale emitter already exists for this restaurant
    SseEmitter existingEmitter = restaurantEmitters.get(restaurantId);

    // Close the stale emitter to force reconnect with fresh connection
    if (existingEmitter != null) {
      existingEmitter.complete();
    }

    // Create new emitter with NO timeout (0L)
    // Restaurant dashboard is expected to stay open throughout business hours
    SseEmitter emitter = new SseEmitter(0L);

    // Callback: triggered when partner closes dashboard (HTTP close frame)
    emitter.onCompletion(() -> restaurantEmitters.remove(restaurantId));

    // Callback: triggered on IO error or network issue with restaurant connection
    emitter.onError(
        e -> {
          log.error("Restaurant SSE error for restaurantId = {}", restaurantId, e);
          restaurantEmitters.remove(restaurantId);
        });

    // Register the new emitter in the map
    restaurantEmitters.put(restaurantId, emitter);
    log.info("Restaurant SSE registered for restaurantId = {}", restaurantId);

    return emitter;
  }

  /**
   * Notifies restaurant partner that a new order has been placed.
   *
   * <p>Called by Order Service immediately after order is created and payment confirmed. Restaurant
   * receives order details in real-time to start preparation.
   *
   * <p>If restaurant has no active SSE connection, order is still visible via polling (fallback
   * mechanism). This method is "best-effort" — if push fails, system doesn't fail.
   *
   * @param restaurantId the ID of the restaurant receiving the order
   * @param orderId the ID of the new order
   * @param customerName the name of the customer who placed the order
   * @param totalPrice the total price of the order (for display)
   */
  @Override
  public void pushNewOrder(
      Long restaurantId, Long orderId, String customerName, String totalPrice) {

    // Retrieve the registered SSE emitter for this restaurant
    SseEmitter emitter = restaurantEmitters.get(restaurantId);

    // If no active connection, order is still available via polling (not an error condition)
    if (emitter == null) {
      log.info(
          "No restaurant SSE emitter for restaurantId = {}, order {} visible via polling",
          restaurantId,
          orderId);
      return;
    }

    try {
      // Send new-order event with order details to restaurant dashboard
      emitter.send(
          SseEmitter.event()
              .name("new-order")
              .data(
                  Map.of(
                      "orderId", orderId,
                      "customerName", customerName,
                      "totalPrice", totalPrice,
                      "message", "New order received!")));

      log.info(
          "SSE event 'new-order' pushed to restaurantId={}, for orderId={}", restaurantId, orderId);

    } catch (IOException e) {
      // Network error while sending SSE event (connection may have been severed)
      log.error("Failed to push SSE event 'new-order' for restaurantId = {}", restaurantId, e);
      restaurantEmitters.remove(restaurantId);
    }
  }

  // ═══════════════════════════════════════════════════════════════════════════════════════════════
  // ── HELPER METHODS ─────────────────────────────────────────────────────────────────────────────
  // ═══════════════════════════════════════════════════════════════════════════════════════════════

  /**
   * Generic helper method to push an SSE event to a customer.
   *
   * <p>Encapsulates common error handling and logging logic for all customer-facing events.
   *
   * @param customerId the ID of the customer
   * @param eventName the SSE event name (arbitrary, used by frontend to route handler)
   * @param data the event payload (typically a Map with event-specific fields)
   */
  private void pushEvent(Long customerId, String eventName, Object data) {

    // Retrieve the registered SSE emitter for this customer
    SseEmitter emitter = customerEmitters.get(customerId);

    // If no emitter found, customer may have closed browser or connection timed out
    if (emitter == null) {
      log.warn("SSE emitter not found for customerId = {}", customerId);
      return;
    }

    try {
      // Send the SSE event to the customer's browser
      emitter.send(SseEmitter.event().name(eventName).data(data));

    } catch (IOException e) {
      // Network error while sending SSE event (connection dropped)
      log.error("Failed to push SSE event '{}' for customerId = {}", eventName, customerId, e);
      customerEmitters.remove(customerId);
    }
  }
}
