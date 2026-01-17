package com.cyro.cravekart.controllers;

import com.cyro.cravekart.models.User;
import com.cyro.cravekart.request.LoginRequestDTO;
import com.cyro.cravekart.request.SignupRequestDTO;
import com.cyro.cravekart.response.LoginResponse;
import com.cyro.cravekart.response.SignupReponse;
import com.cyro.cravekart.config.security.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthService authService;

  @GetMapping("/check")
  public ResponseEntity<String> healthCheck() {
    return ResponseEntity.ok("OK");
  }


  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(
      @RequestBody LoginRequestDTO loginRequestDTO){
    LoginResponse response = authService.login(loginRequestDTO);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/signup")
  public ResponseEntity<SignupReponse> createUser(
      @Valid @RequestBody SignupRequestDTO reqeust
  ) {
    User registeredUser = authService.register(reqeust);
    SignupReponse response = SignupReponse.builder()
        .username(registeredUser.getUsername())
        .id(registeredUser.getId())
        .build();
    return ResponseEntity.ok(response);
  }


}
