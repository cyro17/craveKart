package com.cyro.craveKart.controller;

import com.cyro.craveKart.exception.RestaurantException;
import com.cyro.craveKart.exception.UserException;
import com.cyro.craveKart.model.Food;
import com.cyro.craveKart.model.Restaurant;
import com.cyro.craveKart.model.User;
import com.cyro.craveKart.request.CreateFoodRequest;
import com.cyro.craveKart.response.ApiResponse;
import com.cyro.craveKart.service.FoodServiceImpl;
import com.cyro.craveKart.service.RestaurantServiceImpl;
import com.cyro.craveKart.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/food")
public class AdminFoodController {

    @Autowired
    private FoodServiceImpl foodService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private RestaurantServiceImpl restaurantService;

    @PostMapping
    public ResponseEntity<Food> createFood(@RequestBody CreateFoodRequest request,
                                           @RequestHeader("Authorization") String jwt)
            throws UserException, RestaurantException {

        User user = userService.findUserProfileByJwtToken(jwt);

        Restaurant restaurant = restaurantService.findRestaurantById(request.getRestaurantId());
        Food food = foodService.createFood(request, request.getCategory(), restaurant);
        return  new ResponseEntity<>(food, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteFood(@PathVariable Long id,
                                                  @RequestHeader("Authorization") String jwt)
            throws UserException, RestaurantException {

        User user = userService.findUserProfileByJwtToken(jwt);
        ApiResponse response = new ApiResponse();
        response.setMessage("food deleted");

        foodService.deleteFood(id);
        return  new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Food> updateFoodAvailabilityStatus(@PathVariable Long id,
                                                                    @RequestHeader("Authorization") String jwt)
            throws UserException {

        User user = userService.findUserProfileByJwtToken(jwt);
        Food food = foodService.updateAvalibilityStatus(id);

        return new ResponseEntity<>(food, HttpStatus.OK);
    }






}
