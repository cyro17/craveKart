package com.cyro.cravekart.controllers;

import com.cyro.cravekart.config.security.AuthContextService;
import com.cyro.cravekart.models.Address;
import com.cyro.cravekart.models.Customer;
import com.cyro.cravekart.models.User;
import com.cyro.cravekart.request.*;
import com.cyro.cravekart.response.LoginResponse;
import com.cyro.cravekart.response.SignupReponse;
import com.cyro.cravekart.config.security.AuthService;
import com.cyro.cravekart.response.UserResponse;
import com.cyro.cravekart.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthService authService;
  private final AuthContextService authContextService;
  private final ModelMapper modelMapper;
  private final UserService userService;

  @GetMapping("/check")
  public ResponseEntity<String> healthCheck() {
    return ResponseEntity.ok("OK");
  }

  @GetMapping("/profile/me/{userId}")
  public ResponseEntity<UserResponse> getProfile(@PathVariable Long userId) {
    UserResponse user = userService.getByUserId(userId);
    if(user == null) return ResponseEntity.notFound().build();
    return   ResponseEntity.ok(user);

  }


  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(
      @RequestBody LoginRequest loginRequestDTO){
    LoginResponse response = authService.login(loginRequestDTO);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/signin")
  public  ResponseEntity<LoginResponse> signin(
      @RequestBody SigninRequest signinRequest
  ){
    LoginResponse response = authService.signin(signinRequest);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/signup")
  public ResponseEntity<SignupReponse> createUser(
      @Valid @RequestBody SignupRequest reqeust
  ) {
    User registeredUser = authService.register(reqeust);
    SignupReponse response = SignupReponse.builder()
        .username(registeredUser.getUsername())
        .id(registeredUser.getId())
        .build();
    return ResponseEntity.ok(response);
  }


}
