package com.cyro.cravekart.controllers.dev;

import com.cyro.cravekart.config.security.AuthContextService;
import com.cyro.cravekart.request.PlaceOrderRequest;
import com.cyro.cravekart.response.OrderResponse;
//import com.cyro.cravekart.response.PlaceOrderResponse;
import com.cyro.cravekart.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

  private final OrderService orderService;
  private final AuthContextService authService;

  @PostMapping("/place")
  public ResponseEntity<OrderResponse> createOrder(@RequestBody PlaceOrderRequest
                                                        request){
      OrderResponse response = orderService.placeOrder(request);
      return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

//  @GetMapping("/user")
//  public ResponseEntity<List<Order>> getAllUsersOrders(){
//
//    Customer customer = authService.getCustomer();
//    if(customer.getId() != null){
//      List<Order> userOrders = orderService.getCustomerOrders(customer.getId());
//      return new ResponseEntity<>(userOrders, HttpStatus.OK);
//    } else return  new  ResponseEntity<>(HttpStatus.BAD_REQUEST);
//  }

}
