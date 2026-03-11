package com.cyro.cravekart.service;

import com.cyro.cravekart.models.Order;
import com.cyro.cravekart.models.OrderItem;
import com.cyro.cravekart.models.PaymentResponse;
import com.cyro.cravekart.models.User;
import com.cyro.cravekart.request.CreateOrderRequest;
import org.springframework.stereotype.Service;

@Service
public interface OrderItemService {

  public OrderItem createOrderItem(OrderItem orderItem);

}
