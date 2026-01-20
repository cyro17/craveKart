package com.cyro.cravekart.controllers;

import com.cyro.cravekart.config.security.AuthService;
import com.cyro.cravekart.dto.RestaurantDto;
import com.cyro.cravekart.exception.RestaurantException;
import com.cyro.cravekart.models.Restaurant;
import com.cyro.cravekart.models.User;
import com.cyro.cravekart.repository.UserRepository;
import com.cyro.cravekart.response.RestaurantResponse;
import com.cyro.cravekart.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurant")
@RequiredArgsConstructor
@Slf4j
public class RestaurantController {

  private final RestaurantService restaurantService;
  private final AuthService authService;
  private final UserRepository userRepository;

  @GetMapping("/check")
  public ResponseEntity<String> healthCheck(){
    return  new  ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping("/search")
  public ResponseEntity<List<RestaurantResponse>> findRestaurantByName(
      @RequestParam String restaurantName) {
    List<RestaurantResponse> restaurants = restaurantService.searchRestaurant(restaurantName);
    return ResponseEntity.ok(restaurants);
  }

  @GetMapping
  public ResponseEntity<List<RestaurantResponse>> getAllRestaurants(){
    return ResponseEntity.ok(restaurantService.getAllRestaurant());
  }

  @GetMapping("/{id}")
  public ResponseEntity<RestaurantResponse> findRestaurantById(@PathVariable Long id)
      throws RestaurantException {
    return ResponseEntity.ok(restaurantService.getRestaurantById(id));
  }

  @PutMapping("/addFav/{restaurantId}")
  public ResponseEntity<RestaurantDto> addToFavorite (
      @PathVariable Long restaurantId) throws RestaurantException {

    User user = authService.getCurrentAuthUser();
    if(user == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    RestaurantDto restaurant = restaurantService.addToFavorites(restaurantId, user);
    return new ResponseEntity<>(restaurant, HttpStatus.OK);
  }
}
