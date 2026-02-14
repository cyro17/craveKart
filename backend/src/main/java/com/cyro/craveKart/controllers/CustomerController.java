package com.cyro.cravekart.controllers;


import com.cyro.cravekart.config.security.AuthContextService;
import com.cyro.cravekart.dto.CartItemQuantityDto;
import com.cyro.cravekart.models.*;
import com.cyro.cravekart.models.enums.USER_ROLE;
import com.cyro.cravekart.request.AddCartItemRequest;
import com.cyro.cravekart.request.CreateAddressRequest;
import com.cyro.cravekart.response.*;
import com.cyro.cravekart.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
@Slf4j
public class CustomerController {
  public final RestaurantService restaurantService;
  private final CustomerService customerService;
  private final CartService cartService;
  private final CartItemService cartItemService;
  private final OrderService orderService;
  private final AuthContextService  authService;
  private final FoodCategoryService foodCategoryService;


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
      @RequestParam(defaultValue = "1") int pageNo,
      @RequestParam(defaultValue = "6", required = false) int pageSize
  ) {
    return ResponseEntity.ok(restaurantService.getAllRestaurant(pageNo, pageSize));
  }

  @GetMapping("/restaurants/filter")
  public ResponseEntity<List<RestaurantResponse>> getRestaurantsByFilters(
    @RequestParam(required = false) String city,
    @RequestParam(required = false) String cuisine,
    @RequestParam(required = false) Double rating,
    @RequestParam(required = false, defaultValue = "name_asc") String sort,
    @RequestParam(required = false, defaultValue = "0") int page,
    @RequestParam(required = false, defaultValue = "10") int size
  ){

    List<RestaurantResponse> restaurants = restaurantService.getRestaurantsByFilter(city, cuisine, rating, sort, page, size);
    return ResponseEntity.ok(restaurants);
  }

  @GetMapping("restaurants/{id}")
  public ResponseEntity<RestaurantResponse> getRestaurantById(
      @PathVariable Long id
  ){
    RestaurantResponse response = restaurantService.getRestaurantById(id);
    return ResponseEntity.ok(response);
  }

//  FOOD API

  @GetMapping("restaurants/{id}/menu")
  public ResponseEntity<RestaurantMenuResponse> getMenu(@PathVariable Long id){
    return new ResponseEntity<>(restaurantService.getRestaurantMenu(id), HttpStatus.OK);
  }

  //cart
  @GetMapping("/cart")
  public ResponseEntity<CartResponse> getCart(){
    CartResponse cart = cartService.getCart();
    return ResponseEntity.ok(cart);
  }

  @PostMapping("/cart/add/{foodId}")
  public ResponseEntity<CartResponse> addFoodToCart(@PathVariable Long foodId){
    AddCartItemRequest cartItemRequest = AddCartItemRequest.builder().foodId(foodId).quantity(1).build();
    return ResponseEntity.ok(cartService.addItem(cartItemRequest));

  }

  @PutMapping("/cart/cartItem/inc/{cartItemId}")
  public ResponseEntity<CartResponse> incrementCartItemQuantity(
      @PathVariable Long cartItemId){


    CartResponse response = cartItemService.incrementCartItemQuantity(cartItemId);
    return  ResponseEntity.ok(response);
  }

  @PutMapping("/cart/cartItem/dec/{cartItemId}")
  public ResponseEntity<CartResponse> decrementCartItemQuantity(@PathVariable Long cartItemId){

    CartResponse response = cartItemService.decrementCartItemQuantity(cartItemId);
    return new ResponseEntity<>(response, HttpStatus.OK);

  }

  @DeleteMapping("/cart/cartItem/{id}")
  public ResponseEntity<CartResponse> removeCartItem(@PathVariable Long id){

    CartResponse cartResponse = cartItemService.removeCartItemFromCart(id);
    return  ResponseEntity.ok(cartResponse);

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

  @PostMapping("/address")
  public ResponseEntity<Address> saveAddress(@RequestBody CreateAddressRequest createAddressRequest){
    Address address = customerService.saveAddress(createAddressRequest);
    return new ResponseEntity<>(address, HttpStatus.CREATED);
  }





}
