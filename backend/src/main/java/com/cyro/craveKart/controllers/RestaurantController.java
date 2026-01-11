package com.cyro.cravekart.controllers;

import com.cyro.cravekart.dto.RestaurantDto;
import com.cyro.cravekart.exception.RestaurantException;
import com.cyro.cravekart.models.Restaurant;
import com.cyro.cravekart.service.RestaurantService;
import com.cyro.cravekart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/restaurant")
public class RestaurantController {

  @Autowired
  private RestaurantService restaurantService;

  @Autowired
  private UserService userService;

  @GetMapping("/search")
  public ResponseEntity<List<Restaurant>> findRestaurantByName(@RequestParam String keyword) {
    List<Restaurant> restaurants = restaurantService.searchRestaurant(keyword);
    return ResponseEntity.ok(restaurants);
  }

  @GetMapping()
  public ResponseEntity<List<Restaurant>> getAllRestaurants(){
    return ResponseEntity.ok(restaurantService.getAllRestaurant());
  }

  @GetMapping("/{id}")
  public Restaurant findRestaurantById(
      @PathVariable Long id) throws RestaurantException {
      return  restaurantService.findRestaurantById(id);
  }

  @PutMapping("/{id}/add-favorites")
  public ResponseEntity<RestaurantDto> addToFavorite (
      @RequestHeader("Authorization") String jwt,
      @PathVariable Long id) throws RestaurantException {



    return  null;
  }
}
