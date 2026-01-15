package com.cyro.cravekart.service;

import com.cyro.cravekart.config.security.JwtUtil;
import com.cyro.cravekart.exception.UserException;
import com.cyro.cravekart.models.User;
import com.cyro.cravekart.repository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final JwtUtil jwtUtil;
  private final PasswordEncoder passwordEncoder;

  @Override
  public List<User> findAllUsers() {
    return userRepository.findAll();
  }


  @Override
  public User findUserProfileByJwt(String jwt) throws UserException {
    String username = jwtUtil.getUsernameFromToken(jwt);
    return userRepository.findByUsername(username).orElseThrow(
        () -> new UsernameNotFoundException("Username not found")
    );
  }

  @Override
  public User findUserByEmail(String email) throws UserException {
    User user = userRepository.findByEmail(email);
    if(user != null) {
      return user;
    }
    throw new UserException("User not found with email :  " + email);
  }

  @Override
  public List<User> getPendingRestaurantOwner() {
    return null;
  }

  @Override
  public void updatePassword(User user, String newPassword) {
    user.setPassword(passwordEncoder.encode(newPassword));
    userRepository.save(user);
  }

  @Override
  public List<User> saveAll(List<User> users) {
    return userRepository.saveAll(users);
  }

  @Override
  public boolean removeByUserId(Long userId) {
    userRepository.deleteById(userId);
    return true;
  }
}
