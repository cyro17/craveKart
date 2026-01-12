package com.cyro.cravekart.controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminControllers {

  @GetMapping("/health-check")
  public ResponseEntity<String> healthCheck(){
    return ResponseEntity.ok("OK");
  }
}
