package com.cyro.craveKart.controller;


import com.cyro.craveKart.exception.RestaurantException;
import com.cyro.craveKart.exception.UserException;
import com.cyro.craveKart.model.Restaurant;
import com.cyro.craveKart.model.User;
import com.cyro.craveKart.request.CreateRestaurantRequest;
import com.cyro.craveKart.response.ApiResponse;
import com.cyro.craveKart.service.RestaurantService;
import com.cyro.craveKart.service.RestaurantServiceImpl;
import com.cyro.craveKart.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/restaurants")
public class AdminRestaurantController {

    @Autowired
    private RestaurantServiceImpl restaurantService;

    @Autowired
    private UserServiceImpl userService;


    @GetMapping("/healthCheck")
    public ResponseEntity<User> healthCheck(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserProfileByJwtToken(jwt);
        if(user != null)
            return new ResponseEntity<>(user, HttpStatus.OK);
        else throw new UserException("User not authorized");
    }

    @PostMapping
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody CreateRestaurantRequest req,
                                                       @RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserProfileByJwtToken(jwt);
        Restaurant restaurant = restaurantService.createRestaurant(req, user);
        return ResponseEntity.ok(restaurant);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(@PathVariable Long id, @RequestBody CreateRestaurantRequest req,
                                                       @RequestHeader("Authorization") String jwt) throws RestaurantException, UserException {
        User user = userService.findUserProfileByJwtToken(jwt);
        Restaurant restaurant = restaurantService.updateRestaurant(id, req);
        return ResponseEntity.ok(restaurant);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteRestaurantById(@PathVariable("id") Long restaurantId,
                                                           @RequestHeader("Authorization") String jwt) throws RestaurantException, UserException {
        User user = userService.findUserProfileByJwtToken(jwt);
        restaurantService.deleteRestaurant(restaurantId);
        ApiResponse response = new ApiResponse("Restaurant deleted with id successfully ", true);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Restaurant> updateRestaurantStatus(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) throws RestaurantException, UserException{
        Restaurant restaurant = restaurantService.updateRestaurantStatus(id);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }
}
