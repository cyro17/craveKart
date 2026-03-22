package com.cyro.cravekart.service.impl;

import com.cravekart.core.events.notification.OrderConfirmedEvent;
import com.cyro.cravekart.config.security.AuthContextService;
import com.cyro.cravekart.exception.BadRequestException;
import com.cyro.cravekart.exception.ForbiddenException;
import com.cyro.cravekart.exception.ResourceNotFoundException;
import com.cyro.cravekart.models.OnboardingApplication;
import com.cyro.cravekart.models.Order;
import com.cyro.cravekart.models.RestaurantPartner;
import com.cyro.cravekart.models.User;
import com.cyro.cravekart.models.enums.OnboardingStatus;
import com.cyro.cravekart.models.enums.OrderStatus;
import com.cyro.cravekart.publishers.OrderEventPublisher;
import com.cyro.cravekart.repository.CustomerRepository;
import com.cyro.cravekart.repository.OrderRepository;
import com.cyro.cravekart.repository.RestaurantPartnerRepository;
import com.cyro.cravekart.repository.RestaurantRepository;
import com.cyro.cravekart.request.CreateRestaurantRequest;
import com.cyro.cravekart.response.OrderItemResponse;
import com.cyro.cravekart.response.RestaurantOrderSummary;
import com.cyro.cravekart.response.RestaurantPartnerResponse;
import com.cyro.cravekart.service.OrderService;
import com.cyro.cravekart.service.RestaurantPartnerService;
import com.cyro.cravekart.service.SseEmitterService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestaurantPartnerServiceImpl implements RestaurantPartnerService {
  private final AuthContextService authContextService;
  private final OrderService orderService;
  private final OrderRepository orderRepository;
  private final RestaurantRepository restaurantRepository;
  private final RestaurantPartnerRepository restaurantPartnerRepository;
  private final SseEmitterService sseEmitterService;
  private final CustomerRepository customerRepository;

  private static final Set<OrderStatus> RESTAURANT_ACTIONABLE =
      Set.of(
          OrderStatus.PAYMENT_PENDING,
          OrderStatus.CONFIRMED,
          OrderStatus.ACCEPTED,
          OrderStatus.PREPARING);

  private final OrderEventPublisher orderEventPublisher;

  // ═══════════════════════════════════════════════
  //  ORDER QUEUES
  // ═══════════════════════════════════════════════

  @Override
  @Transactional(readOnly = true)
  public List<RestaurantOrderSummary> getNewOrders() {
    RestaurantPartner partner = authContextService.getRestaurantPartner();

    Long restaurantId = partner.getRestaurant().getId();

    return orderRepository
        .findByRestaurantIdAndOrderStatus(restaurantId, OrderStatus.CONFIRMED)
        .stream()
        .map(this::toSummary)
        .toList();
  }

  // active orders

  @Override
  @Transactional(readOnly = true)
  public List<RestaurantOrderSummary> getActiveOrders() {
    RestaurantPartner restaurantPartner = authContextService.getRestaurantPartner();
    Long restaurantId = restaurantPartner.getRestaurant().getId();

    List<OrderStatus> activeStatuses =
        List.of(OrderStatus.ACCEPTED, OrderStatus.PREPARING, OrderStatus.READY_FOR_PICKUP);

    return orderRepository.findByRestaurantIdAndOrderStatusIn(restaurantId, activeStatuses).stream()
        .map(this::toSummary)
        .toList();
  }

  // all orders - history
  public List<RestaurantOrderSummary> getOrderHistory() {
    RestaurantPartner restaurantPartner = authContextService.getRestaurantPartner();
    Long id = restaurantPartner.getRestaurant().getId();

    return orderRepository.findByRestaurantIdOrderByCreatedAtDesc(id).stream()
        .map(this::toSummary)
        .toList();
  }

  // single order detail == validation it belongs to this restaurant

  @Override
  @Transactional
  public RestaurantOrderSummary getOrderDetail(Long orderId) {
    Order order = getOrderForPartner(orderId);
    return toSummary(order);
  }

  // accept ( Order status CONFIRMED-> ACCEPTED )
  @Override
  @Transactional
  public RestaurantOrderSummary acceptOrder(Long orderId) {

    Order order = getOrderForPartner(orderId);
    if (order.getOrderStatus() != OrderStatus.CONFIRMED) {
      throw new BadRequestException(
          "Order can only be accepted when status is PAID, current: " + order.getOrderStatus());
    }

    order.setOrderStatus(OrderStatus.ACCEPTED);
    order.setAcceptedAt(LocalDateTime.now());
    orderRepository.save(order);

    // notify customer via SSE
    sseEmitterService.pushOrderStatusUpdate(
        order.getCustomerId(),
        order.getId(),
        OrderStatus.ACCEPTED,
        "Your order has been accepted by the restaurant !");

    String customerEmail =
        customerRepository
            .findById(order.getCustomerId())
            .map(customer -> customer.getUser().getEmail())
            .orElseThrow(() -> new RuntimeException("Customer not found"));

    log.info("Order {} accepted by restaurant {} ", orderId, order.getRestaurantId());

    orderEventPublisher.publishOrderPaid(
        OrderConfirmedEvent.builder()
            .orderId(order.getId())
            .customerId(order.getCustomerId())
            .customerName(order.getCustomerName())
            .customerEmail(customerEmail)
            .restaurantName(order.getRestaurantName())
            .totalPrice("₹" + order.getTotalPrice())
            .estimatedDeliveryTime("30-45 mintues")
            .build());

    log.info("Order {} confirmed by restaurant {}", orderId, order.getRestaurantId());

    return this.toSummary(order);
  }

  // ACCEPTED -> PREPARING

  @Override
  @Transactional
  public RestaurantOrderSummary markPreparing(Long orderId) {
    Order order = getOrderForPartner(orderId);

    if (order.getOrderStatus() != OrderStatus.ACCEPTED) {
      throw new BadRequestException(
          "Order must be CONFIRMED before marking as PREPARING, Current: {}"
              + order.getOrderStatus());
    }

    order.setOrderStatus(OrderStatus.PREPARING);
    orderRepository.save(order);

    sseEmitterService.pushOrderStatusUpdate(
        order.getCustomerId(),
        order.getId(),
        OrderStatus.PREPARING,
        "The restaurant is preparing your order");

    log.info("Order {} is now PREPARING", orderId);
    return this.toSummary(order);
  }

  // ready for pickup

  @Override
  @Transactional
  public RestaurantOrderSummary markReadyForPickUp(Long orderId) {
    Order order = getOrderForPartner(orderId);

    if (order.getOrderStatus() != OrderStatus.PREPARING) {
      throw new BadRequestException(
          "Order must be PREPARING before marking as READY_FOR_PICKUP. Current: "
              + order.getOrderStatus());
    }

    order.setOrderStatus(OrderStatus.READY_FOR_PICKUP);
    order.setPreparedAt(LocalDateTime.now());
    orderRepository.save(order);

    sseEmitterService.pushOrderStatusUpdate(
        order.getCustomerId(),
        order.getId(),
        OrderStatus.READY_FOR_PICKUP,
        "Your order is packed and ready! A delivery partner is on the way.");

    log.info("Order {} is READY_FOR_PICKUP", orderId);
    return toSummary(order);
  }

  // CONFIRMED -> REJECTED

  @Override
  @Transactional
  public void rejectOrder(Long orderId, String reason) {
    Order order = getOrderForPartner(orderId);

    if (order.getOrderStatus() != OrderStatus.CONFIRMED) {
      throw new BadRequestException(
          "Order can only be rejected when status is PAID. Current: " + order.getOrderStatus());
    }

    order.setOrderStatus(OrderStatus.REJECTED);
    order.setRejectedAt(LocalDateTime.now());
    order.setCancelledAt(LocalDateTime.now());
    orderRepository.save(order);

    // notify customer
    sseEmitterService.pushOrderStatusUpdate(
        order.getId(),
        order.getCustomerId(),
        OrderStatus.REJECTED,
        reason != null ? reason : "restaurant rejected your order, Refund will be initiated");

    // initiate refund
    this.initiateRefund(order);

    log.warn("Order {} rejected by restaurant. Reason: {}", orderId, reason);
  }

  // get pending orders

  @Override
  public List<RestaurantOrderSummary> getPendingOrdersForRestaurants(Long restaurantId) {
    RestaurantPartner partner = authContextService.getRestaurantPartner();
    if (!partner.getRestaurant().getId().equals(restaurantId)) {
      throw new ForbiddenException("Not authorized for this order");
    }
    return orderRepository
        .findByRestaurantIdAndOrderStatus(restaurantId, OrderStatus.PAYMENT_PENDING)
        .stream()
        .map(this::toSummary)
        .toList();
  }

  // get restaurant by partner id

  @Override
  public RestaurantPartnerResponse getById(Long restaurantPartnerId) {
    return restaurantPartnerRepository
        .findById(restaurantPartnerId)
        .map(RestaurantPartnerResponse::from)
        .orElseThrow(
            () ->
                new ResourceNotFoundException(
                    "restaurant partner not found with id : " + restaurantPartnerId));
  }

  // ═══════════════════════════════════════════════
  //  ADMIN / SETUP
  // ═══════════════════════════════════════════════

  @Override
  public RestaurantPartner createRestaurantPartner(User user) {
    if (restaurantPartnerRepository.existsByUser(user)) {
      throw new BadRequestException("Restaurant already exists");
    }
    RestaurantPartner restaurantPartner = new RestaurantPartner();
    restaurantPartner.setUser(user);
    return restaurantPartnerRepository.save(restaurantPartner);
  }

  @Override
  public void apply(CreateRestaurantRequest restaurantRequest) {

    RestaurantPartner partner = authContextService.getRestaurantPartner();

    // check approved
    if (partner.getOnboardingStatus() == OnboardingStatus.APPROVED) {
      throw new BadRequestException("Your restaurant is already approved and live.");
    }

    // already pending
    if (partner.getOnboardingStatus() == OnboardingStatus.PENDING) {
      throw new BadRequestException(
          "Your restaurant is under review, Please wait for admin approval.");
    }

    OnboardingApplication application =
        OnboardingApplication.builder()
            .restaurantPartner(partner)
            .restaurantName(restaurantRequest.getName())
            .cuisineType(restaurantRequest.getCuisineType())
            .description(restaurantRequest.getDescription())
            .openingHours(restaurantRequest.getOpeningHours())
            .streetAddress(restaurantRequest.getAddressRequest().getStreetAddress())
            .city(restaurantRequest.getAddressRequest().getCity())
            .state(restaurantRequest.getAddressRequest().getState())
            .country(restaurantRequest.getAddressRequest().getCountry())
            .postalCode(restaurantRequest.getAddressRequest().getPostalCode())
            .build();

    partner.setApplication(application);
    partner.setOnboardingStatus(OnboardingStatus.PENDING);
    partner.setAppliedAt(LocalDateTime.now());

    // save

    restaurantPartnerRepository.save(partner);
    log.info(
        "Partner {} submitted onboarding application for restaurant : {} ",
        partner.getId(),
        restaurantRequest.getName());
  }

  @Override
  public void getStatus(RestaurantPartner restaurantPartner) {}

  /* HELPER METHODS */

  private Order getOrderForPartner(Long orderId) {
    Long restaurantId = authContextService.getRestaurantPartner().getRestaurant().getId();

    Order order =
        orderRepository
            .findById(orderId)
            .orElseThrow(
                () -> new ResourceNotFoundException("order not found with id : " + orderId));

    if (!order.getRestaurantId().equals(restaurantId)) {
      throw new ForbiddenException("Not authorized for this order");
    }

    return order;
  }

  private RestaurantOrderSummary toSummary(Order order) {
    List<OrderItemResponse> items =
        order.getOrderItems() == null
            ? List.of()
            : order.getOrderItems().stream()
                .map(
                    item ->
                        OrderItemResponse.builder()
                            .foodName(item.getFoodName())
                            .quantity(item.getQuantity())
                            .unitPrice(item.getFoodPrice())
                            .totalPrice(item.getTotalPrice())
                            .imageUrl(item.getImageUrl())
                            .build())
                .toList();

    return RestaurantOrderSummary.builder()
        .orderId(order.getId())
        .orderStatus(order.getOrderStatus())
        .customerName(order.getCustomerName())
        .customerPhone(order.getCustomerPhone())
        .deliveryAddressLine(order.getDeliveryAddressLine())
        .specialInstruction(order.getSpecialInstruction())
        .items(items)
        .totalItems(order.getTotalItems())
        .subtotal(order.getSubtotal())
        .restaurantCharge(order.getRestaurantCharge())
        .totalPrice(order.getTotalPrice())
        .createdAt(order.getCreatedAt())
        .acceptedAt(order.getAcceptedAt())
        .preparedAt(order.getPreparedAt())
        .build();
  }

  private void initiateRefund(Order order) {
    try {
      order.setOrderStatus(OrderStatus.REFUND_INITIATED);
      orderRepository.save(order);

      sseEmitterService.pushOrderStatusUpdate(
          order.getCustomerId(),
          order.getId(),
          OrderStatus.REFUND_INITIATED,
          "Refund has been initiated. Amount will reflect in 5-7 business days.");
    } catch (Exception e) {
      log.info("Failed to initiate refund for order {}", order.getId(), e);
    }
  }
}
