package com.cyro.cravekart.controllers;

import com.cyro.cravekart.config.security.AuthContextService;
import com.cyro.cravekart.models.User;
import com.cyro.cravekart.request.*;
import com.cyro.cravekart.response.LoginResponse;
import com.cyro.cravekart.config.security.AuthService;
import com.cyro.cravekart.response.SignupResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthService authService;
  private final AuthContextService authContextService;

  @GetMapping("/check")
  public ResponseEntity<String> healthCheck() {
    return ResponseEntity.ok("OK");
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(
      @RequestBody LoginRequest loginRequestDTO){
    LoginResponse response = authService.login(loginRequestDTO);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/signin")
  public ResponseEntity<LoginResponse> login(
      @RequestBody LoginEmailRequest loginEmailRequest
  ){
    LoginResponse loginResponse = authService.loginViaEmail(loginEmailRequest);
    return ResponseEntity.ok(loginResponse);
  }

  @PostMapping("/signup")
  public ResponseEntity<SignupResponse> createUser(
      @Valid @RequestBody SignupRequest reqeust
  ) {
    User registeredUser = authService.register(reqeust);
    SignupResponse response = SignupResponse.builder()
        .username(registeredUser.getUsername())
        .id(registeredUser.getId())
        .build();
    return ResponseEntity.ok(response);
  }



//  @PutMapping
//  public ResponseEntity<String> updatePassword(
//      @RequestBody LoginRequestDTO loginRequestDTO
//  ){
//    authService.updatePassword()
//
//
//  }


}
