package com.cyro.cravekart.controllers;

import com.cyro.cravekart.config.security.AuthContextService;
import com.cyro.cravekart.config.security.AuthService;
import com.cyro.cravekart.models.Order;
import com.cyro.cravekart.models.RestaurantPartner;
import com.cyro.cravekart.models.User;
import com.cyro.cravekart.response.AcceptOrderResponse;
import com.cyro.cravekart.response.RestaurantOrderSummary;
import com.cyro.cravekart.response.RestaurantPartnerResponse;
import com.cyro.cravekart.service.RestaurantPartnerService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/api/restaurant/partner")
@Secured("ROLE_RESTAURANT_PARTNER")
@RequiredArgsConstructor
public class RestaurantPartnerController {
  private final RestaurantPartnerService restaurantPartnerService;
  private final AuthContextService authContextService;

  @PostMapping("/apply")
  public ResponseEntity<?> applyForPartnership(){
    RestaurantPartner restaurantPartner = authContextService.getRestaurantPartner();
    restaurantPartnerService.apply(restaurantPartner);

    return ResponseEntity.status(HttpStatus.ACCEPTED)
        .body("Applied for restaurant partnership");
  }

  @GetMapping("/me")
  public ResponseEntity<RestaurantPartnerResponse> getPartnerProfile() throws AccessDeniedException, BadRequestException {
    RestaurantPartner restaurantPartner = authContextService.getRestaurantPartner();
    RestaurantPartner partner = restaurantPartnerService.getById(restaurantPartner.getId());
    return null;
  }

//  Order Queues

  @GetMapping("/orders/new")
  public ResponseEntity<List<RestaurantOrderSummary>> getNewOrders(){
    return  ResponseEntity.ok(restaurantPartnerService.getNewOrders());
  }

  // active orders

  @GetMapping("/orders/active")
  public ResponseEntity<List<RestaurantOrderSummary>> getActiveOrders(){
    return  ResponseEntity.ok(restaurantPartnerService.getActiveOrders());
  }

  // order history

  @GetMapping("/orders/history")
  public ResponseEntity<List<RestaurantOrderSummary>> getHistoryOrders(){
    return ResponseEntity.ok(restaurantPartnerService.getOrderHistory());
  }

//  Order LifeCycle

  @GetMapping("/orders/{orderId}/accept")
  public ResponseEntity<RestaurantOrderSummary> acceptOrder(
          @PathVariable Long orderId
  ) {
    return ResponseEntity.ok(restaurantPartnerService.acceptOrder(orderId));
  }

  @PostMapping("/orders/{orderId}/reject")
  public ResponseEntity<String> rejectOrder(
          @PathVariable Long orderId,
          @RequestParam(required = false) String reason
  ){
    restaurantPartnerService.rejectOrder(orderId, reason);
    return ResponseEntity.ok("Order rejected. Customer has been notified");

  }

  @PostMapping("/orders/{orderId}/prepare")
  public ResponseEntity<RestaurantOrderSummary> markPreparing(@PathVariable Long orderId){
    return ResponseEntity.ok(restaurantPartnerService.markPreparing(orderId));
  }

  @PostMapping("/orders/{orderId}/ready")
  public ResponseEntity<RestaurantOrderSummary> markReadyForPickup(@PathVariable Long orderId) {
    return ResponseEntity.ok(
            restaurantPartnerService.markReadyForPickUp(orderId));
  }


}
