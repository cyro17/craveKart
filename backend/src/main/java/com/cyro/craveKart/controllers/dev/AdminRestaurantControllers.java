package com.cyro.cravekart.controllers.dev;

import com.cyro.cravekart.config.security.AuthContextService;
import com.cyro.cravekart.exception.RestaurantException;
import com.cyro.cravekart.request.CreateRestaurantRequest;
import com.cyro.cravekart.response.CreateRestaurantResponse;
import com.cyro.cravekart.response.RestaurantResponse;
import com.cyro.cravekart.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/restaurant")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminRestaurantControllers {

  private final RestaurantService restaurantService;
  private final AuthContextService authContextService;

  @GetMapping("/check")
  public ResponseEntity<String> healthCheck() {
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<RestaurantResponse>> getAllRestaurants(){
    List<RestaurantResponse> allRestaurant = restaurantService.getAllRestaurant();
    return new ResponseEntity<>(allRestaurant, HttpStatus.OK);
  }

  // CREATE
  @PostMapping
  public ResponseEntity<CreateRestaurantResponse> createRestaurant(
      @RequestBody CreateRestaurantRequest request) {

    CreateRestaurantResponse response = restaurantService.createRestaurant(request);
    return new ResponseEntity(response, HttpStatus.CREATED);
  }

  // ASSIGN PARTNER
  @PostMapping("/{restaurantId}/assign-partner/{partnerId}")
  public ResponseEntity<String> assignPartner(
      @PathVariable Long restaurantId,
      @PathVariable Long partnerId
  ){
    boolean assigned = restaurantService.assignPartner(restaurantId, partnerId);
    if(assigned) {
      return new ResponseEntity<>(HttpStatus.OK);
    }
    return new ResponseEntity(HttpStatus.BAD_REQUEST);
  }

  // UPDATE
  @PutMapping("/{restaurantId}")
  public ResponseEntity<CreateRestaurantResponse> updateRestaurant(
      @PathVariable Long restaurantId,
      @RequestBody CreateRestaurantRequest request
  ){
    try{
      restaurantService.updateRestaurant(restaurantId, request);
      return new ResponseEntity(HttpStatus.OK);
    } catch (RestaurantException e){
      return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
  }

  // UPDATE STATUS
  @PatchMapping("/{restaurantId}/status")
  public ResponseEntity<CreateRestaurantResponse> updateRestaurantStatus(
      @PathVariable Long restaurantId
  ){
    restaurantService.updateRestaurantStatus(restaurantId);
    return new ResponseEntity(HttpStatus.OK);
  }

  // DELETE
  @DeleteMapping("/{restaurantId}")
  public ResponseEntity<String> deleteRestaurant(
      @PathVariable Long restaurantId
  ){
    try{
      restaurantService.deleteRestaurant(restaurantId);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (RestaurantException e) {
      return  new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
  }

}