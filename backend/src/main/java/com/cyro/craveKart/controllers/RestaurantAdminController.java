package com.cyro.cravekart.controllers;

import com.cyro.cravekart.config.security.AuthService;
import com.cyro.cravekart.models.Restaurant;
import com.cyro.cravekart.models.User;
import com.cyro.cravekart.repository.RestaurantRepository;
import com.cyro.cravekart.repository.UserRepository;
import com.cyro.cravekart.request.CreateRestaurantRequest;
import com.cyro.cravekart.response.CreateRestaurantResponse;
import com.cyro.cravekart.service.RestaurantService;
import com.cyro.cravekart.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/restaurant")
@RequiredArgsConstructor
@Slf4j
public class RestaurantAdminController {
  private final RestaurantRepository restaurantRepository;
  private final RestaurantService restaurantService;
  private final UserService userService;
  private final AuthService authService;
  private final UserRepository userRepository;

  @GetMapping("/check")
  public ResponseEntity<String> getHealth() {
    return new ResponseEntity<>( "OK", HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<CreateRestaurantResponse> createRestaurant(
      @RequestBody CreateRestaurantRequest request,
      @RequestHeader("Authorization") String jwtToken) {

    User user = authService.getCurrentAuthUser();
    if (user == null) {
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    log.info("username while creating restaurant :{}", user.getUsername());
    com.cyro.cravekart.response.CreateRestaurantResponse restaurantResponse = restaurantService.createRestaurant(request, user);

    return new ResponseEntity<>(restaurantResponse
        , HttpStatus.CREATED);
  }

  @PutMapping("/{restaurantId}")
  public ResponseEntity<Restaurant> updateRestaurant(
      @PathVariable Long restaurantId,
      @RequestBody CreateRestaurantRequest request){

    if(authService.getCurrentAuthUser() != null) {

      Restaurant updatedRestaurant = restaurantService.updateRestaurant(restaurantId, request);
      return  new ResponseEntity<>(updatedRestaurant, HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
  }

  @DeleteMapping("/{restaurantId}")
  public ResponseEntity<String> deleteRestaurant(@PathVariable Long restaurantId){

    User user = authService.getCurrentAuthUser();
    if( user == null )
      return  new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    List<Restaurant> restaurantsByUserId =
        restaurantService.getRestaurantsByUserId(user.getId());

    for(Restaurant restaurant : restaurantsByUserId) {
        if(restaurant!= null && restaurant.getId().equals(restaurantId)) {
          restaurantService.deleteRestaurant(restaurant.getId());
          return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
        }
      }
    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }
