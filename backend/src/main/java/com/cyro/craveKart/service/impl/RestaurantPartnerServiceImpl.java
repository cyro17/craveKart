package com.cyro.cravekart.service.impl;

import com.cyro.cravekart.config.security.AuthContextService;
import com.cyro.cravekart.config.security.AuthService;
import com.cyro.cravekart.exception.RestaurantException;
import com.cyro.cravekart.models.Order;
import com.cyro.cravekart.models.Restaurant;
import com.cyro.cravekart.models.RestaurantPartner;
import com.cyro.cravekart.models.User;
import com.cyro.cravekart.models.enums.OrderStatus;
import com.cyro.cravekart.repository.OrderRepository;
import com.cyro.cravekart.repository.RestaurantPartnerRepository;
import com.cyro.cravekart.repository.RestaurantRepository;
import com.cyro.cravekart.service.OrderService;
import com.cyro.cravekart.service.RestaurantPartnerService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantPartnerServiceImpl implements RestaurantPartnerService {
  private final AuthContextService  authContextService;
  private final OrderService orderService;
  private final OrderRepository orderRepository;
  private final RestaurantRepository restaurantRepository;
  private final RestaurantPartnerRepository restaurantPartnerRepository;

  @Override
  public Order acceptOrder(Long orderId) throws AccessDeniedException {
    Order order = getOrderForPartner(orderId);
    if(order.getOrderStatus() != OrderStatus.PENDING) {
      throw new AccessDeniedException("Order cannot be accepted");
    }
    order.setOrderStatus(OrderStatus.CONFIRMED);
    return  orderRepository.save(order);
  }

  @Override
  public Order rejectOrder(Long orderId) throws AccessDeniedException, BadRequestException {
    Order order = getOrderForPartner(orderId);

    if (order.getOrderStatus() != OrderStatus.PENDING) {
      throw new BadRequestException("Order cannot be rejected");
    }

    order.setOrderStatus(OrderStatus.FAILED);
    return orderRepository.save(order);
  }

  @Override
  public List<Order> getPendingOrdersForRestaurants(Long restaurantId)
      throws AccessDeniedException, BadRequestException {
    RestaurantPartner partner = authContextService.getRestaurantPartner();
    if(!partner.getRestaurant().getId().equals(restaurantId)) {
      throw  new AccessDeniedException("Not authorized for this order");
    }
    return orderRepository
        .findByRestaurantIdAndOrderStatus(restaurantId, OrderStatus.PENDING);
  }

  @Override
  public RestaurantPartner getById(Long restaurantPartnerId) throws AccessDeniedException, BadRequestException {
    return restaurantPartnerRepository.findById(restaurantPartnerId).orElseThrow(
        ()-> new AccessDeniedException("Not authorized for this restaurant")
    );
  }

  @Override
  public RestaurantPartner createRestaurantPartner(User user) {
    if(restaurantPartnerRepository.existsByUser(user)){
      throw new RuntimeException("Restaurant already exists");
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

  @Override
  public List<Order> getNewOrders() {
    return List.of();
  }

  private Order getOrderForPartner(Long orderId) throws AccessDeniedException {

    RestaurantPartner partner = authContextService.getRestaurantPartner();
    Order order = orderRepository.findById(orderId).orElseThrow(
        () -> new RestaurantException("No order found with id: " + orderId)
    );

    Restaurant restaurant = restaurantRepository.findById(order.getRestaurantId()).orElseThrow(
        () -> new RestaurantException("No restaurant found with id: " + order.getRestaurantId()));

    if (!partner.getRestaurant().getId().equals(restaurant.getId())) {
      throw new AccessDeniedException("Order does not belong to your restaurant");
    }
    return order;
  }
}
