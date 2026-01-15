package com.cyro.cravekart.controllers;


import com.cyro.cravekart.request.AdminCreateFoodRequest;
import com.cyro.cravekart.service.AdminFoodService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/admin/foods")
@RequiredArgsConstructor
public class AdminFoodController {
  private final AdminFoodService adminFoodService;

  @PostMapping
  public ResponseEntity<?> createFood(@Valid @RequestBody AdminCreateFoodRequest request){
    return new ResponseEntity<>(adminFoodService.createFood(request), HttpStatus.CREATED);
  }

}
