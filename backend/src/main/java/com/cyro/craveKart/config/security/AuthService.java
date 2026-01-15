package com.cyro.cravekart.config.security;

import com.cyro.cravekart.models.User;
import com.cyro.cravekart.repository.RestaurantRepository;
import com.cyro.cravekart.repository.UserRepository;
import com.cyro.cravekart.request.LoginRequestDTO;
import com.cyro.cravekart.request.SignupRequestDTO;
import com.cyro.cravekart.request.USER_STATUS;
import com.cyro.cravekart.response.LoginResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final RestaurantRepository restaurantRepository;
  private final JwtUtil jwtUtil;

  public User register(SignupRequestDTO request) {

    if(userRepository.existsByUsername(request.getUsername())){
      throw new RuntimeException("Username already exists");
    }

    if(userRepository.existsByEmail(request.getEmail())){
      throw new RuntimeException("Email already exists");
    }

    User user = User.builder()
        .username(request.getUsername())
        .firstName(request.getFirstName())
        .lastName(request.getLastName())
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .roles(List.of(request.getRole()))
        .status(USER_STATUS.ACTIVE)
        .build();
    return  userRepository.save(user);
  }

  public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
    try {
      Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              loginRequestDTO.getUsername(), loginRequestDTO.getPassword())
      );
        User user = (User) authentication.getPrincipal();
        log.info("User: " + user.getUsername());
        String token = jwtUtil.generateToken(user);
        return LoginResponseDTO.builder().jwt(token).id(user.getId()).build();

    } catch (Exception e) {
      log.error("Login failed", e);
      throw new AuthorizationDeniedException("Invalid username or password");
    }
  }



  public boolean registerAll(List<SignupRequestDTO> requestDTOS) {
    List<User> userList = new ArrayList<>();
    for(SignupRequestDTO user : requestDTOS){
      if(userRepository.existsByUsername(user.getUsername())){
        throw new RuntimeException("Username already exists");
      }

      if(userRepository.existsByEmail(user.getEmail())){
        throw new RuntimeException("Email already exists");
      }

      User newUser = User.builder()
          .username(user.getUsername())
          .firstName(user.getFirstName())
          .lastName(user.getLastName())
          .email(user.getEmail())
          .password(passwordEncoder.encode(user.getPassword()))
          .roles(List.of(user.getRole()))
          .status(USER_STATUS.ACTIVE)
          .build();

      User savedUser = userRepository.save(newUser);

    }
    return true;
  }

  public User getCurrentAuthUser(){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !authentication.isAuthenticated()
        || authentication instanceof AnonymousAuthenticationToken) {
      return null;
    }
    return (User) authentication.getPrincipal();
    }
}






















