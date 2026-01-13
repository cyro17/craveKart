package com.cyro.cravekart.controllers;


import com.cyro.cravekart.config.security.AuthService;
import com.cyro.cravekart.models.User;
import com.cyro.cravekart.repository.UserRepository;
import com.cyro.cravekart.service.UserService;
import com.cyro.cravekart.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminControllers {
  private final UserService userService;
  private final UserRepository userRepository;

  @GetMapping("/check")
  public ResponseEntity<String> healthCheck(){
    return ResponseEntity.ok("OK");
  }

  @GetMapping("/users")
  public ResponseEntity<List<User>> getAllUsers(){
    List<User> users = userService.findAllUsers();
    return  new  ResponseEntity<>(users, HttpStatus.CREATED);
  }


}
