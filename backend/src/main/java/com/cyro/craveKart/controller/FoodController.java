package com.cyro.craveKart.controller;

import com.cyro.craveKart.exception.UserException;
import com.cyro.craveKart.model.Food;
import com.cyro.craveKart.model.User;
import com.cyro.craveKart.service.FoodServiceImpl;
import com.cyro.craveKart.service.RestaurantService;
import com.cyro.craveKart.service.UserService;
import com.cyro.craveKart.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food")
public class FoodController {

    @Autowired
    private FoodServiceImpl foodService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/search")
    public ResponseEntity<List<Food>> searchFood(@RequestParam String name,
                                           @RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserProfileByJwtToken(jwt);
        List<Food> foods = foodService.searchFood(name);
        return  new ResponseEntity<>(foods, HttpStatus.CREATED);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Food>> searchRestaurantFood(
            @RequestParam boolean veg,
            @RequestParam boolean seasonal,
            @RequestParam boolean nonVeg,
            @RequestParam(required = false) String food_category,
            @PathVariable Long restaurantId,
            @RequestHeader("Authorization") String jwt) throws UserException {

        User user = userService.findUserProfileByJwtToken(jwt);
        List<Food> foods = foodService.getRestaurantsFood(restaurantId, veg, nonVeg, seasonal, food_category);
        return  new ResponseEntity<>(foods, HttpStatus.CREATED);
    }
}
