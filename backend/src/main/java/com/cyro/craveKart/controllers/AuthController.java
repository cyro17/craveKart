package com.cyro.cravekart.controllers;

import com.cyro.cravekart.models.User;
import com.cyro.cravekart.request.LoginRequestDTO;
import com.cyro.cravekart.request.SignupRequestDTO;
import com.cyro.cravekart.response.LoginResponseDTO;
import com.cyro.cravekart.response.SignupReponse;
import com.cyro.cravekart.config.security.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipalNotFoundException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthService authService;

  @GetMapping("/health-check")
  public ResponseEntity<String> healthCheck() {
    return ResponseEntity.ok("OK");
  }

  @PostMapping("/signup")
  public ResponseEntity<SignupReponse> createUser(
      @Valid @RequestBody SignupRequestDTO reqeust
  ) throws UserPrincipalNotFoundException {
    User registeredUser = authService.register(reqeust);
    SignupReponse response = SignupReponse.builder()
        .username(registeredUser.getUsername())
        .id(registeredUser.getId())
        .build();
    return ResponseEntity.ok(response);
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponseDTO> login(
      @RequestBody LoginRequestDTO loginRequestDTO){
    LoginResponseDTO response = authService.login(loginRequestDTO);
    return ResponseEntity.ok(response);
  }
}
