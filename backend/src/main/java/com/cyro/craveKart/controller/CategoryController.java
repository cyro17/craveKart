package com.cyro.craveKart.controller;

import com.cyro.craveKart.exception.RestaurantException;
import com.cyro.craveKart.exception.UserException;
import com.cyro.craveKart.model.Category;
import com.cyro.craveKart.model.User;
import com.cyro.craveKart.service.CategoryServiceImpl;
import com.cyro.craveKart.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {
    @Autowired
    private CategoryServiceImpl categoryService;

    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/admin/category")
    public ResponseEntity<Category> createCategory(
            @RequestHeader("Authorization") String jwt,
            @RequestBody Category category
    ) throws RestaurantException, UserException{

        User user = userService.findUserProfileByJwtToken(jwt);
        Category createdCategory = categoryService.createCategory(category.getName(), user.getId());
        return  new ResponseEntity<>(createdCategory, HttpStatus.OK);
    }

    @GetMapping("/category/restaurant/{id}")
    public ResponseEntity<List<Category>> getRestaurantsCategory(
            @PathVariable Long id,
            @RequestHeader("Authorization")String jwt) throws RestaurantException, UserException {

        List<Category> categories=categoryService.findCategoryByRestaurantId(id);
        return new ResponseEntity<>(categories,HttpStatus.OK);
    }

}
