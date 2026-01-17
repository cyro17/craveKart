package com.cyro.cravekart.service;

import com.cyro.cravekart.config.security.AuthService;
import com.cyro.cravekart.exception.OrderException;
import com.cyro.cravekart.exception.RestaurantException;
import com.cyro.cravekart.models.*;
import com.cyro.cravekart.models.enums.OrderStatus;
import com.cyro.cravekart.repository.*;
import com.cyro.cravekart.response.PlaceOrder;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
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
  public PlaceOrder placeOrder()  {

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
    return  PlaceOrder.builder()
        .orderId(order.getId())
        .orderStatus(OrderStatus.CREATED)
        .totalPrice(order.getTotalAmount()).build();
  }

  @Override
  public Order updateOrder(Long orderId, String OrderStatus) {
    return null;
  }

  @Override
  public void cancelOrder(Long orderId) {

  }

  @Override
  public List<Order> getUserOrders(Long id) {
    return List.of();
  }

  @Override
  public List<Order> getOrdersOfRestaurant(Long restaurantId, String orderStatus) throws OrderException, RestaurantException {
    return List.of();
  }
}
