package com.cyro.cravekart.service.impl;

import com.cyro.cravekart.config.kafka.KafkaTopicConfiguration;
import com.cyro.cravekart.config.security.AuthContextService;
import com.cyro.cravekart.events.order.NewOrderEvent;
import com.cyro.cravekart.events.order.OrderCreatedEvent;
import com.cyro.cravekart.exception.BadRequestException;
import com.cyro.cravekart.exception.ForbiddenException;
import com.cyro.cravekart.exception.ResourceNotFoundException;
import com.cyro.cravekart.models.*;
import com.cyro.cravekart.models.enums.DeliveryType;
import com.cyro.cravekart.models.enums.OrderStatus;
import com.cyro.cravekart.repository.*;
import com.cyro.cravekart.request.PlaceOrderRequest;
import com.cyro.cravekart.response.*;
import com.cyro.cravekart.service.OrderService;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderServiceImpl implements OrderService {
  private final AddressRepository addressRepository;
  private final CartRepository cartRepository;
  private final AuthContextService authService;
  private final OrderRepository orderRepository;
  private final OutboxServiceImpl outboxService;

  // customer
  @Override
  @Transactional
  public OrderResponse placeOrder(PlaceOrderRequest request, String idempotencyKey) {
    Optional<OrderResponse> idempotentResponse = checkIdempotency(idempotencyKey);
    if (idempotentResponse.isPresent()) return idempotentResponse.get();

    Customer customer = authService.getCustomer();
    Cart cart = fetchValidatedCart(customer.getId());
    String deliveryAddress = resolveDeliveryAddress(request, customer);
    Restaurant restaurant = cart.getItems().get(0).getFood().getRestaurant();

    // Step 1 — build order shell
    Order order =
        Order.builder()
            .customerId(customer.getId())
            .customerName(customer.getUser().getUsername())
            .customerPhone(customer.getUser().getContact().getMobile())
            .deliveryAddressLine(deliveryAddress)
            .restaurantId(restaurant.getId())
            .restaurantName(restaurant.getName())
            .restaurantAddress(
                restaurant.getAddress() != null
                    ? restaurant.getAddress().getStreetAddress()
                    : "Address not available")
            .specialInstruction(request.getSpecialInstruction())
            .orderStatus(OrderStatus.PAYMENT_PENDING)
            .idempotencyKey(idempotencyKey)
            .build();

    // Step 2 — build items (no order reference yet)
    List<OrderItem> items = buildOrderItems(cart, order);
    order.setOrderItems(items);
    order.setTotalItems(items.stream().mapToInt(OrderItem::getQuantity).sum());

    // Step 3 — apply voucher + calculate totals BEFORE saving
    applyVoucherIfPresent(order, request.getVoucherCode());
    calculateOrderTotals(order);

    // Step 4 — ONE save — cascade saves items CascadeType.ALL is set
    Order savedOrder = orderRepository.save(order);

    log.info("Order saved id={}", savedOrder.getId());
    queueOrderCreatedEvent(savedOrder);
    cartRepository.deleteByCustomerId(customer.getId());

    return buildOrderResponse(savedOrder);
  }

  @Override
  @Transactional
  public Order markAsConfirmed(Long orderId) {
    //    RestaurantPartner restaurantPartner = authService.getRestaurantPartner();
    Order order =
        orderRepository
            .findById(orderId)
            .orElseThrow(() -> new ResourceNotFoundException("Order does not exist"));

    if (order.getOrderStatus() != OrderStatus.PAYMENT_PENDING) {
      throw new BadRequestException("Pending payment...");
    }

    order.setOrderStatus(OrderStatus.CONFIRMED);
    order.setAcceptedAt(LocalDateTime.now());

    log.info("*****************Order marked as confirmed, id = {}*********************", orderId);

    Order savedOrder = orderRepository.save(order);
    outboxService.save(
        KafkaTopicConfiguration.ORDER_CONFIRMED,
        savedOrder.getRestaurantId().toString(),
        "NewOrderEvent",
        buildOrderResponse(savedOrder));

    return savedOrder;
  }

  @Override
  public Order updatePreparationStatus(Long orderId, OrderStatus orderStatus) {
    return null;
  }

  @Override
  public Order pickupOrder(Long orderId) throws BadRequestException {
    DeliveryPartner partner = authService.getDeliveryPartner();

    Order order =
        orderRepository
            .findById(orderId)
            .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

    if (order.getOrderStatus() != OrderStatus.READY_FOR_PICKUP) {
      throw new BadRequestException("Order not ready");
    }

    order.setDeliveryPartnerId(partner.getId());
    order.setDeliveryPartnerName(partner.getUser().getFirstName());
    order.setOrderStatus(OrderStatus.OUT_FOR_DELIVERY);
    order.setPickedUpAt(LocalDateTime.now());

    return orderRepository.save(order);
  }

  @Override
  public Order deliveryOrder(Long orderId) {
    return null;
  }

  @Override
  public String cancelOrder(Long orderId) {

    Customer customer = authService.getCustomer();
    Order order =
        orderRepository
            .findById(orderId)
            .orElseThrow(() -> new ResourceNotFoundException("Order does not exist"));

    if (!order.getCustomerId().equals(customer.getId())) {
      throw new ForbiddenException(
          "This order request does not belong to the customer with id :" + customer.getId());
    }

    if (order.getOrderStatus() == OrderStatus.CONFIRMED
        || order.getOrderStatus() == OrderStatus.OUT_FOR_DELIVERY
        || order.getOrderStatus() == OrderStatus.DELIVERED) {
      throw new ForbiddenException("Order request cannot be Cancelled");
    }

    order.setOrderStatus(OrderStatus.CANCELLED);
    order.setCancelledAt(LocalDateTime.now());
    orderRepository.save(order);
    return "Order Cancelled SUccesfully";
  }

  //  private boolean updateOrderStatus(Long orderId, OrderStatus orderStatus) {
  //    Optional<Order> order = orderRepository.findById(orderId);
  //    if (!order.isPresent()) return false;
  //    order.get().setOrderStatus(orderStatus);
  //    return true;
  //  }

  @Override
  public List<OrderResponse> getCustomerOrders(Long customerId) {
    List<Order> orders = orderRepository.findByCustomerIdOrderByCreatedAtDesc(customerId);
    return orders.stream().map(this::buildOrderResponse).toList();
  }

  public OrderResponse getOrderById(Long orderId) {
    Order order =
        orderRepository
            .findById(orderId)
            .orElseThrow(() -> new ResourceNotFoundException("Order does not exist"));
    return buildOrderResponse(order);
  }

  @Override
  public List<Order> getOrdersOfRestaurant(Long restaurantId, String orderStatus) {
    return List.of();
  }

  @Override
  @PreAuthorize("hasRole('RESTAURANT_PARTNER')")
  public List<Order> getPendingOrdersForRestaurant() {
    RestaurantPartner restaurantPartner = authService.getRestaurantPartner();
    Long restaurantId = restaurantPartner.getRestaurant().getId();
    return orderRepository.findByRestaurantIdAndOrderStatus(restaurantId, OrderStatus.CONFIRMED);
  }

  @Override
  @Transactional
  public void markAsFailed(Long orderId) {
    Order order =
        orderRepository
            .findById(orderId)
            .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

    if (order.getOrderStatus() == OrderStatus.CANCELLED) {
      return;
    }

    if (order.getOrderStatus() != OrderStatus.PAYMENT_PENDING) {
      log.warn("invalid payment failure state for order {}", orderId);
      return;
    }

    order.setOrderStatus(OrderStatus.CANCELLED);
    order.setCancelledAt(LocalDateTime.now());
    orderRepository.save(order);

    log.info("Order {} marked as CANCELLED due to payment failure ", orderId);
  }

  // ==================== Utility methods ==================

  //  create order publisher

  private Optional<OrderResponse> checkIdempotency(String idempotencyKey) {
    if (idempotencyKey == null) return Optional.empty();

    return orderRepository
        .findByIdempotencyKey(idempotencyKey)
        .map(
            existing -> {
              log.info(
                  "Duplicate request for idempotency key {}, returning existing order {}",
                  idempotencyKey,
                  existing.getId());
              return buildOrderResponse(existing);
            });
  }

  private Cart fetchValidatedCart(Long customerId) {
    Cart cart =
        cartRepository
            .findByCustomerIdWithItems(customerId)
            .orElseThrow(() -> new ResourceNotFoundException("Cart not found for customer"));

    if (cart.getItems().isEmpty()) {
      throw new BadRequestException("Cart is empty");
    }

    return cart;
  }

  private List<OrderItem> buildOrderItems(Cart cart, Order order) {
    // order reference is set after Order is attached in buildOrder
    return cart.getItems().stream()
        .map(
            cartItem ->
                OrderItem.builder()
                    .order(order)
                    .foodName(cartItem.getFood().getName())
                    .foodPrice(cartItem.getFood().getPrice())
                    .quantity(cartItem.getQuantity())
                    .totalPrice(cartItem.getTotalPrice())
                    .imageUrl(cartItem.getImageUrl())
                    .build())
        .toList();
  }

  private void applyVoucherIfPresent(Order order, String voucherCode) {
    if (voucherCode != null) {
      this.applyCoupon(order, voucherCode);
    }
  }

  private void queueOrderCreatedEvent(Order savedOrder) {

    // amount
    long amountInPaise =
        savedOrder
            .getTotalPrice()
            .setScale(2, RoundingMode.HALF_UP)
            .multiply(BigDecimal.valueOf(100))
            .setScale(0, RoundingMode.HALF_UP)
            .longValueExact();

    // event
    OrderCreatedEvent event =
        OrderCreatedEvent.builder()
            .orderId(savedOrder.getId())
            .customerId(savedOrder.getCustomerId())
            .restaurantId(savedOrder.getRestaurantId())
            .customerName(savedOrder.getCustomerName())
            .currency("inr")
            .amount(amountInPaise)
            .build();

    outboxService.save("order-created", "OrderCreatedEvent", savedOrder.getId().toString(), event);
    log.info("Outbox event queued for orderId={}", savedOrder.getId());
  }

  private void calculateOrderTotals(Order order) {

    // subtotal
    BigDecimal subTotal =
        order.getOrderItems().stream()
            .map(OrderItem::getTotalPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

    // tax rate + commission rate

    BigDecimal taxRate = new BigDecimal("0.05");
    BigDecimal commissionRate = new BigDecimal("0.20");

    BigDecimal tax = subTotal.multiply(taxRate);
    BigDecimal restaurantCharge = new BigDecimal("50");
    BigDecimal deliveryFee =
        order.getDeliveryAddressLine() != null ? new BigDecimal("40") : BigDecimal.ZERO;

    BigDecimal discount = order.getDiscount() != null ? order.getDiscount() : BigDecimal.ZERO;

    BigDecimal total = subTotal.add(tax).add(deliveryFee).add(restaurantCharge).subtract(discount);

    BigDecimal commission = subTotal.multiply(commissionRate);

    order.setSubtotal(subTotal);
    order.setTaxAmount(tax);
    order.setDeliveryFee(deliveryFee);
    order.setRestaurantCharge(restaurantCharge);
    order.setDiscount(discount);
    order.setTotalPrice(total);
    order.setPlatformFee(commission);
  }

  private void applyCoupon(Order order, String couponCode) {
    if ("NEW50".equalsIgnoreCase(couponCode)) {
      order.setDiscount(new BigDecimal("50"));
    }
  }

  private OrderResponse buildOrderResponse(Order order) {

    String paymentStatus;
    switch (order.getOrderStatus()) {
      case PAYMENT_PENDING -> paymentStatus = "PENDING";
      case CONFIRMED -> paymentStatus = "PAID";
      case CANCELLED -> paymentStatus = "FAILED";
      default -> paymentStatus = "NOT_APPLICABLE";
    }

    PaymentSummary paymentSummary =
        PaymentSummary.builder()
            .paymentId(null)
            .paymentMethod(null)
            .paymentStatus(paymentStatus)
            .build();

    return OrderResponse.builder()
        .orderId(order.getId())
        .orderStatus(order.getOrderStatus())
        .restaurant(
            RestaurantSummary.builder()
                .restaurantId(order.getRestaurantId())
                .name(order.getRestaurantName())
                .restaurantAddress(order.getRestaurantAddress())
                .build())
        .customer(
            CustomerSummary.builder()
                .customerId(order.getCustomerId())
                .name(order.getCustomerName())
                .build())
        .items(
            order.getOrderItems().stream()
                .map(
                    item ->
                        OrderItemResponse.builder()
                            .foodName(item.getFoodName())
                            .quantity(item.getQuantity())
                            .unitPrice(item.getFoodPrice())
                            .totalPrice(item.getTotalPrice())
                            .imageUrl(item.getImageUrl())
                            .build())
                .toList())
        .pricing(
            PriceBreakdown.builder()
                .subtotal(order.getSubtotal())
                .tax(order.getTaxAmount())
                .deliveryFee(order.getDeliveryFee())
                .restaurantCharge(order.getRestaurantCharge())
                .discount(order.getDiscount())
                .platformFee(order.getPlatformFee())
                .total(order.getTotalPrice())
                .build())
        .delivery(order.getDeliveryAddressLine())
        .payment(paymentSummary)
        .createdAt(order.getCreatedAt())
        .estimatedDeliveryTime(
            order.getCreatedAt() != null ? order.getCreatedAt().plusMinutes(32) : null)
        .build();
  }

  private String resolveDeliveryAddress(PlaceOrderRequest request, Customer customer) {
    // add delivery address
    if (request.getDeliveryType() != DeliveryType.DELIVERY) return null;

    Address address =
        addressRepository
            .findById(request.getAddressId())
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "Address not found with id: " + request.getAddressId()));
    if (!address.getCustomer().getId().equals(customer.getId())) {
      throw new BadRequestException("Invalid address for this customer");
    }

    return address.getFullAddress();
  }

  private NewOrderEvent buildNewOrderEvent(Order order) {
    return NewOrderEvent.builder()
        .orderId(order.getId())
        .restaurantId(order.getRestaurantId())
        .restaurantName(order.getRestaurantName())
        .customerId(order.getCustomerId())
        .customerName(order.getCustomerName())
        .customerPhone(order.getCustomerPhone())
        .deliveryAddress(order.getDeliveryAddressLine()) // field name matches your class
        .specialInstructions(order.getSpecialInstruction())
        .subtotal(order.getSubtotal())
        .totalPrice(order.getTotalPrice()) // BigDecimal → BigDecimal, no conversion
        .totalItems(order.getTotalItems())
        .createdAt(order.getCreatedAt())
        .items(
            order.getOrderItems().stream()
                .map(
                    item ->
                        NewOrderEvent.Item.builder()
                            .foodName(item.getFoodName())
                            .quantity(item.getQuantity())
                            .unitPrice(item.getFoodPrice())
                            .totalPrice(item.getTotalPrice())
                            .imageUrl(item.getImageUrl())
                            .build())
                .toList())
        .build();
  }
}
