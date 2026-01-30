package com.cyro.cravekart.service.impl;

import com.cyro.cravekart.config.security.AuthContextService;
import com.cyro.cravekart.config.security.AuthService;
import com.cyro.cravekart.exception.OrderException;
import com.cyro.cravekart.exception.RestaurantException;
import com.cyro.cravekart.models.*;
import com.cyro.cravekart.models.enums.OrderStatus;
import com.cyro.cravekart.repository.*;
import com.cyro.cravekart.response.PlaceOrderResponse;
import com.cyro.cravekart.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderServiceImpl implements OrderService {
  private final FoodRepository foodRepository;
  private final RestaurantRepository restaurantRepository;
  private final FoodCategoryRepository foodCategoryRepository;
  private final CartRepository cartRepository;
  private final AuthContextService authService;
  private final OrderRepository orderRepository;


  // customer
  @Override
  public PlaceOrderResponse placeOrder()  {

    Customer customer = authService.getCustomer();

    log.info(String.valueOf(customer.getUser().getId()));

    Address address = customer.getAddresses().get(0);

    Cart cart = cartRepository.findByCustomerId(
        customer.getId()).orElseThrow(
        () -> new RuntimeException("User does not exist")
    );
    if(cart.getItems().isEmpty()) {
      throw new RuntimeException("cart is empty");
    }

    Restaurant restaurant = cart.getItems().get(0).getFood().getRestaurant();

    Order order = Order.builder()
        .customerId(customer.getId())
        .customerName(customer.getUser().getUsername())
        .customerPhone(customer.getUser().getContact().getMobile())
        .deliveryAddressLine(address.getFullAddress())
//        .deliveryCity(customer.getAddresses().get(0).getCity())
//        .deliveryPinCode(customer.getAddresses().get(0).getPostalCode())
        .restaurantId(restaurant.getId())
        .restaurantName(restaurant.getName())
        .restaurantAddress(restaurant.getAddress().getStreetAddress())
        .orderStatus(OrderStatus.CREATED)
        .build();



    List<OrderItem> orderItems = new ArrayList<>();

    for(CartItem cartItem : cart.getItems()) {
      OrderItem orderItem = new  OrderItem();
      orderItem.setOrder(order);

      orderItem.setFoodName(cartItem.getFood().getName());
      orderItem.setFoodPrice(cartItem.getFood().getPrice());
      orderItem.setQuantity(cartItem.getQuantity());
      orderItem.setTotalPrice(cartItem.getTotalPrice());
      // get the food , fetch restaurant details from it

      orderItems.add(orderItem);
    }

    order.setOrderItems(orderItems);
    order.setTotalItems(orderItems.size());


    order.setTotalAmount(
        orderItems.stream()
            .map(OrderItem::getTotalPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add)
    );

    orderRepository.save(order);

    cartRepository.deleteByCustomerId(customer.getId());

    return  PlaceOrderResponse.builder()
        .orderId(order.getId())
        .orderStatus(OrderStatus.PENDING)
        .totalPrice(order.getTotalAmount()).build();
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

    if(order.getOrderStatus() != OrderStatus.PENDING &&
      order.getOrderStatus() != OrderStatus.CREATED){
      throw new BadRequestException("Order cannot be confirmed");
    }

    order.setOrderStatus(OrderStatus.CONFIRMED);
    order.setAcceptedAt(LocalDateTime.now());
    return order;
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

    return order;
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

    if(order.getOrderStatus().equals((OrderStatus.PENDING))){
      throw new BadRequestException("Order request cannot be Cancelled");
    }

     updateOrderStatus(orderId, OrderStatus.CANCELLED);
    order.setCancelledAt(LocalDateTime.now());
      return  "Order Cancelled SUccesfully";
  }

  private boolean updateOrderStatus(Long orderId, OrderStatus orderStatus) {
    Optional<Order> order = orderRepository.findById(orderId);
    if(!order.isPresent()) return  false;
    order.get().setOrderStatus(orderStatus);
    return true;
  }

  @Override
  public List<Order> getCustomerOrders(Long customerId) {
    return orderRepository.findByCustomerId(customerId);
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
    return orderRepository.findByRestaurantIdAndOrderStatus(restaurantId, OrderStatus.PENDING);
  }


}
