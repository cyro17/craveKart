package com.cyro.cravekart.service;

import com.cyro.cravekart.config.security.JwtUtil;
import com.cyro.cravekart.exception.UserException;
import com.cyro.cravekart.models.User;
import com.cyro.cravekart.models.enums.USER_ROLE;
import com.cyro.cravekart.repository.UserRepository;
import com.cyro.cravekart.response.UserResponse;
import com.cyro.cravekart.service.utils.UserServiceUtil;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.cache.interceptor.SimpleKey;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Data
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final JwtUtil jwtUtil;
  private final PasswordEncoder passwordEncoder;
  private final JavaMailSender  javaMailSender;
  private final UserServiceUtil userServiceUtil;


  @Override
//  @Cacheable(value = "allUsers")
//  @Transactional
  public List<UserResponse> findAllUsers() {
    return  userRepository.findAll().stream()
        .map(UserResponse::from)
        .toList();
  }


  @Override
  @Cacheable(key = "#userId", value = "usersById")
  public UserResponse getByUserId(Long userId) {
    log.warn("🚨 DB HIT userId={}", userId);
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found"));
    return  UserResponse.from(user);
  }

  @Override
  @Cacheable(key = "#email", value = "usersByEmail")
  public UserResponse getUserByEmail(String email) {
    log.warn("🚨 DB HIT userId={}", email);
    User user =  userRepository.findByEmail(email)
        .orElseThrow(()-> new RuntimeException("User with email " + email + " not found"));
    return   UserResponse.from(user);
  }

  @Override
  @Caching(evict = {
      @CacheEvict(key= "#user.id", value = "usersById"),
      @CacheEvict( key = "#user.email", value = "usersByEmail"),
//      @CacheEvict(value = "allUsers", allEntries = true)
  })
  public void updatePassword(User user, String newPassword) {
    user.setPassword(passwordEncoder.encode(newPassword));
    userRepository.save(user);
  }

  @Override
  @Caching(evict = {
      @CacheEvict(value = "usersById", key= "#userId"),
//      @CacheEvict(value = "allUsers", allEntries = true)
  })
  public boolean removeByUserId(Long userId) {
    userRepository.deleteById(userId);
    return true;
  }



  @Override
  public void sendPasswordResetEmail(User user) {
    String token = userServiceUtil.generateRandomToken();
    Date date = userServiceUtil.calculateExpiryDate();

  }


//  @Override
//  @Cacheable(value = "usersByEmail", key = "#email")
//  public User getByEmail(String email) {
//    return userRepository.findByEmail(email);
//  }
//
//  @Override
//  public User findUserProfileByJwt(String jwt) throws UserException {
//    String username = jwtUtil.getUsernameFromToken(jwt);
//    return userRepository.findByUsername(username).orElseThrow(
//        () -> new UsernameNotFoundException("Username not found")
//    );
//  }



//  @Override
//  public List<User> getPendingRestaurantOwner() {
//    return null;
//  }



//  @Override
//  public List<User> saveAll(List<User> users) {
//    return userRepository.saveAll(users);
//  }


}
