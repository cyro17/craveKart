package com.cyro.cravekart.controllers;


import com.cyro.cravekart.config.security.AuthContextService;
import com.cyro.cravekart.config.security.AuthService;
import com.cyro.cravekart.dto.CartItemDto;
import com.cyro.cravekart.dto.CartItemQuantityDto;
import com.cyro.cravekart.dto.RestaurantDto;
import com.cyro.cravekart.models.CartItem;
import com.cyro.cravekart.models.Order;
import com.cyro.cravekart.models.User;
import com.cyro.cravekart.models.enums.USER_ROLE;
import com.cyro.cravekart.request.AddCartItemRequest;
import com.cyro.cravekart.request.CreateOrderRequest;
import com.cyro.cravekart.response.CartItemResponse;
import com.cyro.cravekart.response.CartResponse;
import com.cyro.cravekart.response.PlaceOrderResponse;
import com.cyro.cravekart.response.RestaurantResponse;
import com.cyro.cravekart.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
@Slf4j
@Secured("ROLE_CUSTOMER")
public class CustomerController {
  public final RestaurantService restaurantService;
  private final CustomerService customerService;
  private final CartService cartService;
  private final CartItemService cartItemService;
  private final OrderService orderService;
  private final AuthContextService  authService;


  @GetMapping("/check")
  public ResponseEntity<String> healthCheck() {

    for (USER_ROLE role : authService.getCustomer().getUser().getRoles()) {
      System.out.println(role);
    }

    return  new ResponseEntity<>("ok",  HttpStatus.OK);
  }

  // Restaurants
  @GetMapping("/restaurants")
  public ResponseEntity<List<RestaurantResponse>> getAllRestaurants(
      @RequestParam(defaultValue = "0") Integer pageOffset,
      @RequestParam(defaultValue = "10", required = false) Integer pageSize
  ) {
    PageRequest pageRequest = PageRequest.of(pageOffset, pageSize);
    return ResponseEntity.ok(restaurantService.getAllRestaurant());
  }

  //cart
  @GetMapping("/carts")
  public ResponseEntity<List<CartItemResponse>> getCartItems(){
    CartResponse cart = cartService.getCart();
    return ResponseEntity.ok(cart.getItems());
  }

  @PostMapping("/cart/addFoodToCart/{foodId}")
  public ResponseEntity<CartResponse> addFoodToCart(@PathVariable Long foodId){
    AddCartItemRequest cartItemRequest = AddCartItemRequest.builder().foodId(foodId).quantity(1).build();
    return ResponseEntity.ok(cartService.addItem(cartItemRequest));

  }

  @PutMapping("/cart/cartItem/incrementCartItemQuantity")
  public ResponseEntity<CartItemResponse> incrementCartItemQuantity(
      @RequestBody CartItemQuantityDto cartItemQuantityDto){

    CartItem cartItemById = cartItemService.getCartItemById(cartItemQuantityDto.getCartItemId());
    CartItemResponse response = cartItemService.incrementCartItemQuantity
        (cartItemById, cartItemQuantityDto.getQuantity());
    return  ResponseEntity.ok(response);

  }

  @PutMapping("/cart/cartItem/decrementCartItemQuantity")
  public ResponseEntity<CartItemResponse> decrementCartItemQuantity(@RequestBody CartItemQuantityDto cartItemQuantityDto){
    CartItem cartItemById = cartItemService.getCartItemById(cartItemQuantityDto.getCartItemId());
    CartItemResponse response = cartItemService.decrementCartItemQuantity(cartItemById, cartItemQuantityDto.getQuantity());
    return new ResponseEntity<>(response, HttpStatus.OK);

  }

  @DeleteMapping("/cart")
  public ResponseEntity<String> deleteCart(){
    cartService.clearCart();
     return  ResponseEntity.ok("cart cleared");
  }

  // orders
  @PostMapping("/order/place")
  public ResponseEntity<PlaceOrderResponse> createOrder(){
    PlaceOrderResponse response = orderService.placeOrder();
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @GetMapping("/order")
  public ResponseEntity<List<Order>> getOrder(){
    Long customerId = authService.getCustomer().getId();
    List<Order> userOrders = orderService.getCustomerOrders(customerId);
    return  new ResponseEntity<>(userOrders, HttpStatus.OK);
  }

  @PostMapping("/order/cancel/{orderId}")
  public ResponseEntity<?> cancelOrder(@PathVariable Long orderId){
    try {
      orderService.cancelOrder(orderId);
    } catch (AccessDeniedException | BadRequestException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(HttpStatus.OK);

  }





}
