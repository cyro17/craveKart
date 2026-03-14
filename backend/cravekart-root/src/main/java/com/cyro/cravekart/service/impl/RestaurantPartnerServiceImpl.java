package com.cyro.cravekart.service.impl;

import com.cravekart.core.events.notification.OrderConfirmedEvent;
import com.cyro.cravekart.config.security.AuthContextService;
import com.cyro.cravekart.exception.BadRequestException;
import com.cyro.cravekart.exception.ForbiddenException;
import com.cyro.cravekart.exception.ResourceNotFoundException;
import com.cyro.cravekart.models.Order;
import com.cyro.cravekart.models.RestaurantPartner;
import com.cyro.cravekart.models.User;
import com.cyro.cravekart.models.enums.OrderStatus;
import com.cyro.cravekart.publishers.OrderCofirmedEventPublisher;
import com.cyro.cravekart.repository.CustomerRepository;
import com.cyro.cravekart.repository.OrderRepository;
import com.cyro.cravekart.repository.RestaurantPartnerRepository;
import com.cyro.cravekart.repository.RestaurantRepository;
import com.cyro.cravekart.response.OrderItemResponse;
import com.cyro.cravekart.response.RestaurantOrderSummary;
import com.cyro.cravekart.service.OrderService;
import com.cyro.cravekart.service.RestaurantPartnerService;
import com.cyro.cravekart.service.SseEmitterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestaurantPartnerServiceImpl implements RestaurantPartnerService {
  private final AuthContextService  authContextService;
  private final OrderService orderService;
  private final OrderRepository orderRepository;
  private final RestaurantRepository restaurantRepository;
  private final RestaurantPartnerRepository restaurantPartnerRepository;
  private final SseEmitterService sseEmitterService;
  private final CustomerRepository customerRepository;


  private static final Set<OrderStatus> RESTAURANT_ACTIONABLE = Set.of(
          OrderStatus.PAID,
          OrderStatus.CONFIRMED,
          OrderStatus.PREPARING
  );
  private final OrderCofirmedEventPublisher orderCofirmedEventPublisher;

  // Order queue

  @Override
  @Transactional(readOnly=true)
  public List<RestaurantOrderSummary> getNewOrders(){
    RestaurantPartner partner = authContextService.getRestaurantPartner();

    Long restaurantId = partner.getRestaurant().getId();

    return orderRepository.findByRestaurantIdAndOrderStatus(restaurantId, OrderStatus.PAID)
            .stream()
            .map(this::toSummary)
            .toList();

  }

  // active orders

  public List<RestaurantOrderSummary> getActiveOrders(){
    RestaurantPartner restaurantPartner = authContextService.getRestaurantPartner();
    Long restaurantId = restaurantPartner.getRestaurant().getId();

    List<OrderStatus> activeStatuses = List.of(OrderStatus.CONFIRMED,
            OrderStatus.PREPARING, OrderStatus.READY_FOR_PICKUP);

      return orderRepository.findByRestaurantIdAndOrderStatusIn(
              restaurantId, activeStatuses).stream()
                    .map(this::toSummary).toList();
  }

  // all orders - history
  public List<RestaurantOrderSummary> getOrderHistory(){
    RestaurantPartner restaurantPartner = authContextService.getRestaurantPartner();
    Long id = restaurantPartner.getRestaurant().getId();

    return  orderRepository.findByRestaurantIdOrderByCreatedAtDesc(id)
            .stream()
            .map(this::toSummary)
            .toList();

  }

  // single order detail == validation it belongs to this restaurant

  @Override
  @Transactional
  public RestaurantOrderSummary getOrderDetail(Long orderId){
    Order order = getOrderForPartner(orderId);
    return toSummary(order);
  }


  // accept ( Order status PAID-> CONFIRMED )
  @Override
  @Transactional
  public RestaurantOrderSummary acceptOrder(Long orderId) {

    Order order = getOrderForPartner(orderId);
    if(order.getOrderStatus() != OrderStatus.PAID){
      throw new BadRequestException(
              "Order can only be accepted when status is PAID, current: " + order.getOrderStatus());
    }

    order.setOrderStatus(OrderStatus.CONFIRMED);
    order.setAcceptedAt(LocalDateTime.now());
    orderRepository.save(order);

    // notify customer via SSE
    sseEmitterService.pushOrderStatusUpdate(
            order.getCustomerId(),
            order.getId(),
            OrderStatus.CONFIRMED,
            "Your order has been accepted by the restaurant !"
    );

    String customerEmail = customerRepository.findById(order.getCustomerId())
            .map(customer -> customer.getUser().getEmail())
            .orElseThrow(() -> new RuntimeException("Customer not found"));

    log.info("Order {} accepted by restaurant {} ",orderId, order.getRestaurantId());

    orderCofirmedEventPublisher.publish(OrderConfirmedEvent.builder()
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

  // mark preparing

  public RestaurantOrderSummary markPreparing(Long orderId){
    Order order = getOrderForPartner(orderId);

    if(order.getOrderStatus() != OrderStatus.CONFIRMED){
      throw new BadRequestException(
              "Order must be CONFIRMED before marking as PREPARING, Current: {}"
              + order.getOrderStatus()
      );
    }

    order.setOrderStatus(OrderStatus.PREPARING);
    orderRepository.save(order);

    sseEmitterService.pushOrderStatusUpdate(
            order.getCustomerId(),
            order.getId(),
            OrderStatus.PREPARING,
            "The restaurant is preparing your order"
    );

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
                      + order.getOrderStatus()
      );
    }

    order.setOrderStatus(OrderStatus.READY_FOR_PICKUP);
    order.setPreparedAt(LocalDateTime.now());
    orderRepository.save(order);

    sseEmitterService.pushOrderStatusUpdate(
            order.getCustomerId(),
            order.getId(),
            OrderStatus.READY_FOR_PICKUP,
            "Your order is packed and ready! A delivery partner is on the way."
    );

    log.info("Order {} is READY_FOR_PICKUP", orderId);
    return toSummary(order);
  }


  // reject

  @Override
  @Transactional
  public void rejectOrder(Long orderId, String reason) {
    Order order = getOrderForPartner(orderId);

    if (order.getOrderStatus() != OrderStatus.PAID) {
      throw new BadRequestException(
              "Order can only be rejected when status is PAID. Current: "
                      + order.getOrderStatus()
      );
    }

    order.setOrderStatus(OrderStatus.PAYMENT_FAILED);
    order.setCancelledAt(LocalDateTime.now());
    orderRepository.save(order);

    sseEmitterService.pushPaymentFailed(
            order.getId(),
            order.getCustomerId(),
            reason != null ? reason :
                    "restaurant rejected your order, Refund will be initiated"
    );

    log.warn("Order {} rejected by restaurant. Reason: {}", orderId, reason);
  }




  // get pending orders

  @Override
  public List<RestaurantOrderSummary> getPendingOrdersForRestaurants(Long restaurantId) {
    RestaurantPartner partner = authContextService.getRestaurantPartner();
    if(!partner.getRestaurant().getId().equals(restaurantId)) {
      throw  new ForbiddenException("Not authorized for this order");
    }
    return orderRepository
        .findByRestaurantIdAndOrderStatus(restaurantId, OrderStatus.PAYMENT_PENDING)
            .stream().map(this::toSummary).toList();
  }

  // get restaurant by partner id

  @Override
  public RestaurantPartner getById(Long restaurantPartnerId) {
    return restaurantPartnerRepository.findById(restaurantPartnerId).orElseThrow(
        ()-> new ResourceNotFoundException("restaurant partner not found with id : " + restaurantPartnerId)
    );
  }

  // create Restaurant partner ( ONLY ADMINS )

  @Override
  public RestaurantPartner createRestaurantPartner(User user) {
    if(restaurantPartnerRepository.existsByUser(user)){
      throw new BadRequestException("Restaurant already exists");
    }
    RestaurantPartner restaurantPartner = new RestaurantPartner();
    restaurantPartner.setUser(user);
    return  restaurantPartnerRepository.save(restaurantPartner);

  }

  @Override
  public void apply(RestaurantPartner restaurantPartner) {

  }

  @Override
  public void getStatus(RestaurantPartner restaurantPartner) {

  }


  /* HELPER METHODS */

  private Order getOrderForPartner(Long orderId) {
    Long restaurantId = authContextService.getRestaurantPartner().getRestaurant().getId();

    Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new ResourceNotFoundException("order not found with id : " + orderId));

    if(!order.getRestaurantId().equals(restaurantId)) {
      throw new ForbiddenException("Not authorized for this order");
    }

    return order;
  }

  private RestaurantOrderSummary toSummary(Order order){
    List<OrderItemResponse> items = order.getOrderItems() == null ?
            List.of() :
            order.getOrderItems().stream()
                    .map(item -> OrderItemResponse.builder()
                            .foodName(item.getFoodName())
                            .quantity(item.getQuantity())
                            .unitPrice(item.getFoodPrice())
                            .totalPrice(item.getTotalPrice())
                            .imageUrl(item.getImageUrl())
                            .build()
                    ).toList();

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
}
