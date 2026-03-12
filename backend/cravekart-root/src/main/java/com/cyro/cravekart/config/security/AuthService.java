package com.cyro.cravekart.config.security;

import com.cravekart.core.events.notification.BaseNotificationEvent;
import com.cravekart.core.events.notification.UserSignupEvent;
import com.cyro.cravekart.publishers.NotificationSignupEventPublisher;

import com.cyro.cravekart.exception.ConflictException;
import com.cyro.cravekart.exception.ResourceNotFoundException;
import com.cyro.cravekart.exception.UnauthorizedException;
import com.cyro.cravekart.models.*;
import com.cyro.cravekart.repository.*;
import com.cyro.cravekart.request.*;
import com.cyro.cravekart.response.LoginResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AuthService {
  private final AddressRepository addressRepository;
  private final DeliveryPartnerRepository deliveryPartnerRepository;
  private final DeliveryRepository deliveryRepository;
  private final RestaurantPartnerRepository restaurantPartnerRepository;
  private final CustomerRepository customerRepository;
  private final AdminRepository adminRepository;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final RestaurantRepository restaurantRepository;
  private final JwtUtil jwtUtil;
  private final ModelMapper modelMapper;
  private final AuthContextService authContextService;
  private final NotificationSignupEventPublisher notificationSignupEventPublisher;

  public User register(SignupRequest request) {

    if(userRepository.existsByUsername(request.getUsername())){
      throw new ConflictException("Username already exists");
    }

    if(userRepository.existsByEmail(request.getEmail())){
      throw new ConflictException("Email already exists");
    }


    User user = modelMapper.map(request, User.class);
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setRoles(List.of(request.getRole()));
    user.setStatus(USER_STATUS.ACTIVE);
    user.setContact(request.getContactInfo());

    User savedUser = userRepository.save(user);

    switch (request.getRole()) {
      case ADMIN:
        Admin admin = new Admin();
        admin.setUser(user);
        adminRepository.save(admin);
        break;
      case RESTAURANT_PARTNER:
        RestaurantPartner restaurantPartner = new RestaurantPartner();
        restaurantPartner.setUser(user);
        restaurantPartnerRepository.save(restaurantPartner);
        break;
      case DELIVERY_PARTNER:
        DeliveryPartner deliveryPartner = new DeliveryPartner();
        deliveryPartner.setUser(user);
        deliveryPartnerRepository.save(deliveryPartner);
        break;
      default:
        Customer customer = new Customer();
        customer.setUser(user);
        customerRepository.save(customer);
        break;
    }

    try {
      UserSignupEvent event = UserSignupEvent.builder()
              .userId(savedUser.getId().toString())
              .username(savedUser.getUsername())
              .email(savedUser.getEmail())
              .firstName(savedUser.getFirstName())
              .lastName(savedUser.getLastName())
              .role(savedUser.getRoles().toString())
              .channel(BaseNotificationEvent.NotificationChannel.EMAIL)
              .build();
      notificationSignupEventPublisher.publishUserSignup(event);
      log.info("User signup event published for : {}", savedUser.getUsername());

    } catch (Exception ex) {
      log.error("Failed to publish UserSignupEvent for {} : {}", savedUser.getUsername(), ex.getMessage() );
    }

    return  savedUser;
  }


  // login service

  public LoginResponse login(LoginRequest loginRequestDTO) {
    try {
      Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              loginRequestDTO.getUsername(), loginRequestDTO.getPassword())
      );
        User user = (User) authentication.getPrincipal();
        log.info("User: " + user.getUsername());
        String token = jwtUtil.generateToken(user);
        return LoginResponse.builder().jwt(token).id(user.getId()).build();

    } catch (Exception e) {
      log.error("Login failed", e);
      throw new UnauthorizedException("Invalid username or password");
    }
  }

  // signin request

  public LoginResponse signin(SigninRequest signinRequest){
    User userRequest = userRepository.findByEmail(signinRequest.getEmail()).orElseThrow(
        () -> new ResourceNotFoundException("User not found")
    );

    try{
      Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              userRequest.getUsername(), signinRequest.getPassword()
          )
      );

      User user = (User) authentication.getPrincipal();
      log.info("User: " + user.getUsername());
      String token = jwtUtil.generateToken(user);
      return LoginResponse.builder().jwt(token).id(user.getId()).build();

    } catch (Exception e) {
      log.error("Login failed", e);
      throw new UnauthorizedException("Invalid username or password");
    }
  }


  public boolean registerAll(List<SignupRequest> requestDTOS) {
    List<User> userList = new ArrayList<>();
    for(SignupRequest user : requestDTOS){
      if(userRepository.existsByUsername(user.getUsername())){
        throw new ConflictException("Username already exists");
      }

      if(userRepository.existsByEmail(user.getEmail())){
        throw new ConflictException("Email already exists");
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

      userRepository.save(newUser);

    }
    return true;
  }

}






















