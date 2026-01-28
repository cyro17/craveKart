package com.cyro.cravekart.controllers.dev;

import com.cyro.cravekart.config.security.AuthContextService;
import com.cyro.cravekart.models.Admin;
import com.cyro.cravekart.models.Restaurant;
import com.cyro.cravekart.request.CreateRestaurantRequest;
import com.cyro.cravekart.response.CreateRestaurantResponse;
import com.cyro.cravekart.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/restaurant")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminRestaurantControllers {

  private final RestaurantService restaurantService;
  private final AuthContextService authContextService;

  @PostMapping
  public ResponseEntity<CreateRestaurantResponse> createRestaurant(
      @RequestBody CreateRestaurantRequest request) {

    Admin admin = authContextService.getAdmin();
    if (admin == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

////    log.info("username while creating restaurant :{}", admin.getUser().getUsername());
//    com.cyro.cravekart.response.CreateRestaurantResponse restaurantResponse = restaurantService.createRestaurant(request, user);

    return new ResponseEntity<>(restaurantResponse
        , HttpStatus.CREATED);
  }



}
