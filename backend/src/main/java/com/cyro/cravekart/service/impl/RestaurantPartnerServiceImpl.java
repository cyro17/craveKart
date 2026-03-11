package com.cyro.cravekart.service.impl;

import com.cyro.cravekart.config.security.AuthContextService;
import com.cyro.cravekart.exception.BadRequestException;
import com.cyro.cravekart.exception.ForbiddenException;
import com.cyro.cravekart.exception.ResourceNotFoundException;
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
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantPartnerServiceImpl implements RestaurantPartnerService {
  private final AuthContextService  authContextService;
  private final OrderService orderService;
  private final OrderRepository orderRepository;
  private final RestaurantRepository restaurantRepository;
  private final RestaurantPartnerRepository restaurantPartnerRepository;


  // accept
  @Override
  public Order acceptOrder(Long orderId) {

    Order order = getOrderForPartner(orderId);
    if(order.getOrderStatus() != OrderStatus.PAID) {
      throw new BadRequestException("Order must be paid before acceptance");
    }
    order.setOrderStatus(OrderStatus.CONFIRMED);
    return  orderRepository.save(order);
  }

  // reject

  @Override
  public Order rejectOrder(Long orderId) {
    Order order = getOrderForPartner(orderId);

    if (order.getOrderStatus() != OrderStatus.PAID) {
      throw new BadRequestException("Order cannot be rejected");
    }

    order.setOrderStatus(OrderStatus.PAYMENT_FAILED);
    return orderRepository.save(order);
  }

  // get pending orders

  @Override
  public List<Order> getPendingOrdersForRestaurants(Long restaurantId) {
    RestaurantPartner partner = authContextService.getRestaurantPartner();
    if(!partner.getRestaurant().getId().equals(restaurantId)) {
      throw  new ForbiddenException("Not authorized for this order");
    }
    return orderRepository
        .findByRestaurantIdAndOrderStatus(restaurantId, OrderStatus.PAYMENT_PENDING);
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

  @Override
  public List<Order> getNewOrders() {
    return List.of();
  }

  private Order getOrderForPartner(Long orderId) {

    RestaurantPartner partner = authContextService.getRestaurantPartner();
    Order order = orderRepository.findById(orderId).orElseThrow(
        () -> new ResourceNotFoundException("No order found with id: " + orderId)
    );

    Restaurant restaurant = restaurantRepository.findById(order.getRestaurantId())
        .orElseThrow(() ->
            new ResourceNotFoundException("No restaurant found with id: " +
                order.getRestaurantId()));

    if (!partner.getRestaurant().getId().equals(restaurant.getId())) {
      throw new ForbiddenException("Order does not belong to your restaurant");
    }
    return order;
  }
}
