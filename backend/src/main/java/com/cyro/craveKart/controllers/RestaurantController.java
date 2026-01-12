package com.cyro.cravekart.controllers;

import com.cyro.cravekart.config.security.AuthService;
import com.cyro.cravekart.dto.RestaurantDto;
import com.cyro.cravekart.exception.RestaurantException;
import com.cyro.cravekart.models.Restaurant;
import com.cyro.cravekart.models.User;
import com.cyro.cravekart.repository.UserRepository;
import com.cyro.cravekart.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
  public ResponseEntity<List<Restaurant>> findRestaurantByName(
      @RequestParam String restaurantName) {
    List<Restaurant> restaurants = restaurantService.searchRestaurant(restaurantName);
    for(Restaurant restaurant : restaurants){
      log.info(restaurant.getName());
    }
    return ResponseEntity.ok(restaurants);
  }

  @GetMapping()
  public ResponseEntity<List<Restaurant>> getAllRestaurants(){
    return ResponseEntity.ok(restaurantService.getAllRestaurant());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Restaurant> findRestaurantById(@PathVariable Long id)
      throws RestaurantException {
    return ResponseEntity.ok(restaurantService.findRestaurantById(id));
  }

  @PutMapping("/addFav/{restaurantId}")
  public ResponseEntity<RestaurantDto> addToFavorite (
      @PathVariable Long restaurantId) throws RestaurantException {

    Long userId = authService.ifAuthenticated();
    User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    RestaurantDto restaurant = restaurantService.addToFavorites(restaurantId, user);
    return new ResponseEntity<>(restaurant, HttpStatus.OK);
  }
}
