package com.cyro.craveKart.controller;

import com.cyro.craveKart.dto.RestaurantDTO;
import com.cyro.craveKart.exception.RestaurantException;
import com.cyro.craveKart.exception.UserException;
import com.cyro.craveKart.model.Restaurant;
import com.cyro.craveKart.model.User;
import com.cyro.craveKart.service.RestaurantService;
import com.cyro.craveKart.service.RestaurantServiceImpl;
import com.cyro.craveKart.service.UserServiceImpl;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantServiceImpl restaurantService;

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("health-check")
    public ResponseEntity<String> healthCheck(){
        return ResponseEntity.ok("ok");
    }

    @GetMapping("/search")
    public ResponseEntity<List<Restaurant>> findRestaurantByName(
            @RequestParam String keyword ){
        List<Restaurant> restaurant = restaurantService.searchRestaurant(keyword);
        return ResponseEntity.ok(restaurant);
    }


    @GetMapping
    public ResponseEntity<List<Restaurant>> getAllRestaurants(){
        List<Restaurant> restaurants = restaurantService.getAllRestaurant();
        return ResponseEntity.ok(restaurants);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> findRestaurantById(@PathVariable ObjectId id)
            throws RestaurantException {
        Restaurant restaurant = restaurantService.findRestaurantById(id);
        return  ResponseEntity.ok(restaurant);
    }

    @PutMapping("/{id}/add-favorites")
    public ResponseEntity<RestaurantDTO> addToFavorite(
            @RequestHeader("Authorization") String jwt,
            @PathVariable ObjectId id
    ) throws RestaurantException, UserException{

        User user = userService.findUserProfileByJwtToken(jwt);
        RestaurantDTO restaurantDTO = restaurantService.addtoFavourites(id, user);
        return ResponseEntity.ok(restaurantDTO);
    }











}
