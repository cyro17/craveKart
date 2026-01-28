package com.cyro.cravekart.controllers;

import com.cyro.cravekart.config.security.AuthContextService;
import com.cyro.cravekart.config.security.AuthService;
import com.cyro.cravekart.models.Order;
import com.cyro.cravekart.models.RestaurantPartner;
import com.cyro.cravekart.models.User;
import com.cyro.cravekart.response.AcceptOrderResponse;
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

//  public ResponseEntity<RestaurantPartnerResponse> getStatus(){
//    RestaurantPartner restaurantPartner = authContextService.getRestaurantPartner();
//    restaurantPartnerService.getStatus(restaurantPartner);
//
//  }



//  @PostMapping("/acceptOrderRequest/{orderRequestId}")
//  public AcceptOrderResponse acceptOrderRequest(@PathVariable Long orderRequestId,
//                                                @RequestBody AcceptOrderDto acceptOrderDto){
//    return restaurantPartnerService.acceptOrder(orderRequestId);
//  }

}
