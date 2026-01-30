package com.cyro.cravekart.controllers;

import com.cyro.cravekart.config.security.AuthContextService;
import com.cyro.cravekart.models.Address;
import com.cyro.cravekart.models.Customer;
import com.cyro.cravekart.models.User;
import com.cyro.cravekart.request.AddressRequest;
import com.cyro.cravekart.request.CreateAddressRequest;
import com.cyro.cravekart.request.LoginRequest;
import com.cyro.cravekart.request.SignupRequest;
import com.cyro.cravekart.response.LoginResponse;
import com.cyro.cravekart.response.SignupReponse;
import com.cyro.cravekart.config.security.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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



//  @PutMapping
//  public ResponseEntity<String> updatePassword(
//      @RequestBody LoginRequestDTO loginRequestDTO
//  ){
//    authService.updatePassword()
//
//
//  }


}
