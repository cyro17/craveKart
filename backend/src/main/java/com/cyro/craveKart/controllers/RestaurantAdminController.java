package com.cyro.cravekart.controllers;


import com.cyro.cravekart.models.Restaurant;
import com.cyro.cravekart.models.User;
import com.cyro.cravekart.request.CreateRestaurantRequest;
import com.cyro.cravekart.response.CreateRestaurantResponse;
import com.cyro.cravekart.service.RestaurantService;
import com.cyro.cravekart.service.RestaurantServiceImpl;
import com.cyro.cravekart.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/restaurant")
@RequiredArgsConstructor
@Slf4j
public class RestaurantAdminController {

  private final RestaurantServiceImpl restaurantService;
  private final UserService userService;

  @GetMapping("/health")
  public ResponseEntity<?> getHealth() {
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<CreateRestaurantResponse> createRestaurant(
      @RequestBody CreateRestaurantRequest request,
      @RequestHeader("Authorization") String jwtToken) {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null ||
        !authentication.isAuthenticated() ||
        authentication.getPrincipal() instanceof String) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    User user = (User) authentication.getPrincipal();
    log.info("username while creating restaurant", user.getUsername());
    com.cyro.cravekart.response.CreateRestaurantResponse restaurantResponse = restaurantService.createRestaurant(request, user);

    return new ResponseEntity<>(restaurantResponse
        , HttpStatus.CREATED);
  }

  @PutMapping("/{restaurantId}")
  public ResponseEntity<Restaurant> updateRestaurant(
      @PathVariable Long restaurantId,
      @RequestBody CreateRestaurantRequest request){

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    log.info("user trying to update restaurant " + restaurantId);
    if(authentication != null || authentication.isAuthenticated() ||
    authentication.getPrincipal() instanceof User) {

      Restaurant updatedRestaurant = restaurantService.updateRestaurant(restaurantId, request);
      return  new ResponseEntity<>(updatedRestaurant, HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
  }

  @DeleteMapping("/{restaurantId}")
  public ResponseEntity<String> deleteRestaurant(
      @PathVariable Long restaurantId
  ){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if(authentication != null || authentication.isAuthenticated() ||
        authentication.getPrincipal() instanceof User) {
      User user = (User) authentication.getPrincipal();
      List<Restaurant> restaurantsByUserId = restaurantService.getRestaurantsByUserId(user.getId());
      for(Restaurant restaurant : restaurantsByUserId) {
        if(restaurant!= null) {
          restaurantService.deleteRestaurant(restaurant.getId());
          return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
        }
      }
    }
    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
  }
















}
