package com.cyro.cravekart.service.impl;

import com.cyro.cravekart.Exceptions.OrderPublishException;
import com.cyro.cravekart.config.security.AuthContextService;
import com.cyro.cravekart.events.OrderCreatedEvent;
import com.cyro.cravekart.exception.OrderException;
import com.cyro.cravekart.exception.RestaurantException;
import com.cyro.cravekart.models.*;
import com.cyro.cravekart.models.enums.DeliveryType;
import com.cyro.cravekart.models.enums.OrderStatus;
import com.cyro.cravekart.repository.*;
import com.cyro.cravekart.request.PlaceOrderRequest;
import com.cyro.cravekart.response.*;
import com.cyro.cravekart.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderServiceImpl implements OrderService {
  private final AddressRepository addressRepository;
  private final FoodRepository foodRepository;
  private final RestaurantRepository restaurantRepository;
  private final FoodCategoryRepository foodCategoryRepository;
  private final CartRepository cartRepository;
  private final AuthContextService authService;
  private final OrderRepository orderRepository;
  private final ModelMapper modelMapper;
//  private final KafkaTemplate<String, OrderNotifcationEvent>

  private final KafkaTemplate<String, Object> kafkaTemplate;

  // customer
  @Override
  public OrderResponse placeOrder(PlaceOrderRequest request)  {

    // fetch customer
    Customer customer = authService.getCustomer();

    // get cart
    Cart cart = cartRepository.findByCustomerId(
        customer.getId()).orElseThrow(
        () -> new RuntimeException("User does not exist")
    );
    // check if cart is empty
    if(cart.getItems().isEmpty()) {
      throw new RuntimeException("cart is empty");
    }

    // fetch restaurant
    Restaurant restaurant = cart.getItems().get(0).getFood().getRestaurant();

    String deliveryAddressLine = null;

    // add delivery address
    if(request.getDeliveryType() == DeliveryType.DELIVERY){
      Address address = addressRepository.findById(request.getAddressId()).orElseThrow(
          () -> new RuntimeException("Address not found")
      );

      if(!address.getCustomer().getId().equals(customer.getId())){
        throw new RuntimeException("Invadi address for this customer");
      }

      deliveryAddressLine = address.getFullAddress();
    }

    // creating order
    Order order = Order.builder()
        .customerId(customer.getId())
        .customerName(customer.getUser().getUsername())
        .customerPhone(customer.getUser().getContact().getMobile())
        .deliveryAddressLine(deliveryAddressLine)
        .restaurantId(restaurant.getId())
        .restaurantName(restaurant.getName())
        .restaurantAddress(restaurant.getAddress().getStreetAddress())
        .specialInstruction(request.getSpecialInstruction())
        .orderStatus(OrderStatus.PAYMENT_PENDING)
        .build();


    // creating order items
    List<OrderItem> orderItems = cart.getItems().stream()
        .map(cartItem ->
            OrderItem.builder()
                .order(order)
                .foodName(cartItem.getFood().getName())
                .foodPrice(cartItem.getFood().getPrice())
                .quantity(cartItem.getQuantity())
                .totalPrice(cartItem.getTotalPrice())
                .imageUrl(cartItem.getImageUrl())
                .build()
        ).toList();

    order.setOrderItems(orderItems);
    order.setTotalItems(
        orderItems.stream()
            .mapToInt(OrderItem::getQuantity)
            .sum()
    );

    // apply voucher
    if(request.getVoucherCode() != null) {
      applycoupon(order, request.getVoucherCode());
    }

    // calculate total
    calculateOrderTotals(order);

    // order created and saved, now create a order created event
    Order savedOrder = orderRepository.save(order);
    log.info("Order saved, id = {}", savedOrder.getId());

    publishOrderCreatedEvent(savedOrder);

    // clear cart
    cartRepository.deleteByCustomerId(customer.getId());

    return  buildOrderResponse(savedOrder);
  }

  // restaurant partner
  @Override
  public Order confirmOrder(Long orderId) throws AccessDeniedException, BadRequestException {
    RestaurantPartner restaurantPartner = authService.getRestaurantPartner();
    Order order = orderRepository.findById(orderId).orElseThrow(
        () -> new RuntimeException("Order does not exist")
    );
    if( !order.getRestaurantId().equals(restaurantPartner.getRestaurant().getId())){
      throw new AccessDeniedException("Order does not belong to your restaurant");
    }

    if(order.getOrderStatus() != OrderStatus.PAID){
      throw new BadRequestException("Pending payment...");
    }

    order.setOrderStatus(OrderStatus.CONFIRMED);
    order.setAcceptedAt(LocalDateTime.now());

    return orderRepository.save(order);
  }

  @Override
  public Order updatePreparationStatus(Long orderId, OrderStatus orderStatus) {
    return null;
  }

  @Override
  public Order pickupOrder(Long orderId) throws BadRequestException {
    DeliveryPartner partner = authService.getDeliveryPartner();

    Order order = orderRepository.findById(orderId)
        .orElseThrow(() -> new RuntimeException("Order not found"));

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
  public String cancelOrder(Long orderId) throws AccessDeniedException, BadRequestException {

    Customer customer = authService.getCustomer();
    Order order = orderRepository.findById(orderId).orElseThrow(
        ()-> new RuntimeException("Order does not exist")
    );

    if( !order.getCustomerId().equals(customer.getId())) {
      throw new AccessDeniedException("This order request does not belong to the customer with id :" +
          customer.getId());
    }

    if(order.getOrderStatus() == OrderStatus.CONFIRMED ||
        order.getOrderStatus() == OrderStatus.OUT_FOR_DELIVERY ||
        order.getOrderStatus() == OrderStatus.DELIVERED){
      throw new BadRequestException("Order request cannot be Cancelled");
    }

    order.setOrderStatus(OrderStatus.CANCELLED);
    order.setCancelledAt(LocalDateTime.now());
    orderRepository.save(order);
    return  "Order Cancelled SUccesfully";
  }

  private boolean updateOrderStatus(Long orderId, OrderStatus orderStatus) {
    Optional<Order> order = orderRepository.findById(orderId);
    if(!order.isPresent()) return  false;
    order.get().setOrderStatus(orderStatus);
    return true;
  }

  @Override
  public List<OrderResponse> getCustomerOrders(Long customerId) {
    List<Order> orders = orderRepository.
        findByCustomerIdOrderByCreatedAtDesc(customerId);
    return  orders.stream()
        .map(this::buildOrderResponse)
        .toList();
  }

  @Override
  public List<Order> getOrdersOfRestaurant(Long restaurantId, String orderStatus) throws OrderException, RestaurantException {
    return List.of();
  }



  @Override
  @PreAuthorize("hasRole('RESTAURANT_PARTNER')")
  public List<Order> getPendingOrdersForRestaurant() {
    RestaurantPartner restaurantPartner = authService.getRestaurantPartner();
    Long restaurantId = restaurantPartner.getRestaurant().getId();
    return orderRepository.findByRestaurantIdAndOrderStatus(restaurantId, OrderStatus.PAID);

  }

  // ==================== Payment Callbacks ==================

  @Override
  public void markAsPaid(Long orderId) {
      Order order = orderRepository.findById(orderId)
          .orElseThrow(() -> new RuntimeException("Order not found"));

      if(order.getOrderStatus() == OrderStatus.PAID){
        log.info("Order {} is already PAID, skipping duplicate event.", orderId);
        return;

      }

      if (order.getOrderStatus() != OrderStatus.PAYMENT_PENDING) {
        log.warn("Order {} cannot be marked as PAID from status {}",
            orderId, order.getOrderStatus());
        return;
      }

      order.setOrderStatus(OrderStatus.PAID);
      order.setPaidAt(LocalDateTime.now());
      log.info("Order {} marked as PAID", orderId);

      orderRepository.save(order);
      log.info("Order {} marked as PAID", orderId);


    }

  @Override
  public void markAsFailed(Long orderId) {
    Order order = orderRepository.findById(orderId)
        .orElseThrow(() -> new RuntimeException("Order not found"));

    if(order.getOrderStatus() == OrderStatus.CANCELLED){
      return;
    }

    if(order.getOrderStatus() != OrderStatus.PAYMENT_PENDING){
      log.warn("invalid payment failure state for order {}", orderId);
      return;
    }

    order.setOrderStatus(OrderStatus.CANCELLED);
    order.setCancelledAt(LocalDateTime.now());

    log.info("Order {} marked as CANCELLED due to payment failure ", orderId);
  }


//  HELPER METHODS ------------------------------------------------------
  private void publishOrderCreatedEvent(Order savedOrder){
    long amountInPaise = savedOrder.getTotalPrice()
        .setScale(2, RoundingMode.HALF_UP)
        .multiply(BigDecimal.valueOf(100)).longValueExact();

    OrderCreatedEvent event = OrderCreatedEvent.builder()
        .orderId(savedOrder.getId())
        .customerId(savedOrder.getCustomerId())
        .restaurantId(savedOrder.getRestaurantId())
        .customerName(savedOrder.getCustomerName())
        .currency("INR")
        .amount(amountInPaise)
        .build();

    kafkaTemplate.send("order-created",
        savedOrder.getId().toString(),
        event
    ).whenComplete((result, ex)-> {
      if (ex != null) {
        log.error("Failed to publish order-created for orderId = {}",
            savedOrder.getId(), ex);
        // ✅ throw so cart is NOT cleared and customer can retry
        throw new RuntimeException(
            "Failed to initiate payment for order: " + savedOrder.getId());
      }
      log.info("Published order-created for orderId = {}", savedOrder.getId());
    });


  }

  private void calculateOrderTotals(Order order){

    // subtotal
    BigDecimal subTotal = order.getOrderItems().stream()
        .map(OrderItem::getTotalPrice)
        .reduce(BigDecimal.ZERO, BigDecimal::add);

    // tax rate + commision rate
    BigDecimal taxRate = new BigDecimal("0.05");
    BigDecimal commissionRate = new  BigDecimal("0.20");

    BigDecimal tax = subTotal.multiply(taxRate);
    BigDecimal restaurantCharge = new BigDecimal("50");
    BigDecimal deliveryFee =  order.getDeliveryAddressLine() != null ?
        new BigDecimal("40") : BigDecimal.ZERO;

    BigDecimal discount = order.getDiscount() != null ?
        order.getDiscount() : BigDecimal.ZERO;

    BigDecimal total = subTotal
        .add(tax)
        .add(deliveryFee)
        .add(restaurantCharge)
        .subtract(discount);

    BigDecimal commission = subTotal.multiply(commissionRate);
//    BigDecimal payout = subTotal.add(restaurantCharge).subtract(commission);

    order.setSubtotal(subTotal);
    order.setTaxAmount(tax);
    order.setDeliveryFee(deliveryFee);
    order.setRestaurantCharge(restaurantCharge);
    order.setDiscount(discount);
    order.setTotalPrice(total);
    order.setPlatformFee(commission);

  }

  private void applycoupon(Order order, String couponCode){
    if("NEW50".equalsIgnoreCase(couponCode)){
      order.setDiscount(new  BigDecimal("50"));
    }
  }

  private OrderResponse buildOrderResponse(Order order){

    String paymentStatus;
    switch (order.getOrderStatus()){
      case PAYMENT_PENDING ->  paymentStatus = "PENDING";
      case PAID ->  paymentStatus = "PAID";
      case CANCELLED ->  paymentStatus = "FAILED";
      default ->  paymentStatus = "NOT_APPLICABLE";
    }

    PaymentSummary paymentSummary = PaymentSummary.builder()
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
                .build()
        )
        .customer(
            CustomerSummary.builder()
                .customerId(order.getCustomerId())
                .name(order.getCustomerName())
                .build()
        )
        .items(
            order.getOrderItems().stream()
                .map(item-> OrderItemResponse.builder()
                    .foodName(item.getFoodName())
                    .quantity(item.getQuantity())
                    .unitPrice(item.getFoodPrice())
                    .totalPrice(item.getTotalPrice())
                    .imageUrl(item.getImageUrl())
                    .build())
                .toList()
        )

        .pricing(
            PriceBreakdown.builder()
                .subtotal(order.getSubtotal())
                .tax(order.getTaxAmount())
                .deliveryFee(order.getDeliveryFee())
                .restaurantCharge(order.getRestaurantCharge())
                .discount(order.getDiscount())
                .platformFee(order.getPlatformFee())
                .total(order.getTotalPrice())
                .build()
        )

        .delivery(order.getDeliveryAddressLine())
        .payment(paymentSummary)
        .createdAt(order.getCreatedAt())
        .estimatedDeliveryTime(
            order.getCreatedAt() != null ?
                order.getCreatedAt().plusMinutes(32) : null)

        .build();

  }

}




