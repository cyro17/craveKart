package com.cyro.cravekart.controllers;

import com.cyro.cravekart.config.security.AuthService;
import com.cyro.cravekart.exception.OrderException;
import com.cyro.cravekart.models.Order;
import com.cyro.cravekart.models.PaymentResponse;
import com.cyro.cravekart.models.User;
import com.cyro.cravekart.request.CreateOrderRequest;
import com.cyro.cravekart.response.PlaceOrder;
import com.cyro.cravekart.service.OrderService;
import com.cyro.cravekart.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

  private final OrderService orderService;
  private final AuthService  authService;

  @PostMapping("/place")
  public ResponseEntity<PlaceOrder> createOrder(){
      PlaceOrder response = orderService.placeOrder();
      return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @GetMapping("/user")
  public ResponseEntity<List<Order>> getAllUsersOrders(){
    User user = authService.getCurrentAuthUser();
    if(user.getId() != null){
      List<Order> userOrders = orderService.getUserOrders(user.getId());
      return new ResponseEntity<>(userOrders, HttpStatus.OK);
    } else return  new  ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }

}
