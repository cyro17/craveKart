package com.cyro.cravekart.service.impl;

import com.cyro.cravekart.config.security.AuthService;
import com.cyro.cravekart.exception.OrderException;
import com.cyro.cravekart.exception.RestaurantException;
import com.cyro.cravekart.models.*;
import com.cyro.cravekart.models.enums.OrderStatus;
import com.cyro.cravekart.repository.*;
import com.cyro.cravekart.response.OrderResponse;
import com.cyro.cravekart.response.PlaceOrderResponse;
import com.cyro.cravekart.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {
  private final FoodRepository foodRepository;
  private final RestaurantRepository restaurantRepository;
  private final FoodCategoryRepository foodCategoryRepository;
  private final CartRepository cartRepository;
  private final AuthService  authService;
  private final OrderRepository orderRepository;


  @Override
  public PlaceOrderResponse placeOrder()  {

    User user = authService.getCurrentAuthUser();

    Cart cart = cartRepository.findByCustomerId(
        user.getId()).orElseThrow(
        () -> new RuntimeException("User does not exist")
    );
    if(cart.getItems().isEmpty()) {
      throw new RuntimeException("cart is empty");
    }

    Order order = Order.builder()
        .customer(user)
        .orderStatus(OrderStatus.CREATED).build();

    List<OrderItem> orderItems = new ArrayList<>();

    for(CartItem cartItem : cart.getItems()) {
      System.out.println(cartItem.getFood().getName());
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
    order.setTotalItem(orderItems.size());


    order.setTotalAmount(
        orderItems.stream()
            .map(OrderItem::getTotalPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add)
    );

    orderRepository.save(order);

    cartRepository.deleteByCustomerId(user.getId());
    return  PlaceOrderResponse.builder()
        .orderId(order.getId())
        .orderStatus(OrderStatus.CREATED)
        .totalPrice(order.getTotalAmount()).build();
  }

  @Override
  public Order updateOrder(Long orderId, String OrderStatus) {
    return null;
  }

  @Override
  public void cancelOrder(Long orderId) throws AccessDeniedException, BadRequestException {
    User currentAuthUser = authService.getCurrentAuthUser();
    Order order = orderRepository.findById(orderId).get();
    if( !order.getCustomer().getId().equals(currentAuthUser.getId())) {
      throw new AccessDeniedException("This order request does not belong to the customer with id :" + currentAuthUser.getId());
    }

    if(order.getOrderStatus().equals((OrderStatus.PENDING))){
      throw new BadRequestException("Order request cannot be Cancelled");
    }

     updateOrderStatus(orderId, OrderStatus.CANCELLED);
  }

  private boolean updateOrderStatus(Long orderId, OrderStatus orderStatus) {
    return false;
  }

  @Override
  public List<Order> getUserOrders(Long customerId) {
    return orderRepository.findByCustomerId(customerId);
  }

  @Override
  public List<Order> getOrdersOfRestaurant(Long restaurantId, String orderStatus) throws OrderException, RestaurantException {
    return List.of();
  }
}
