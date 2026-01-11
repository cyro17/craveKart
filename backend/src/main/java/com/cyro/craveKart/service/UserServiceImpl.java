package com.cyro.cravekart.service;

import com.cyro.cravekart.exception.UserException;
import com.cyro.cravekart.models.User;
import com.cyro.cravekart.repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;


  @Override
  public User findUserProfileByJwt(String jwt) throws UserException {
    return  null;
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
  public List<User> findAllUsers() {
    return userRepository.findAll();
  }

  @Override
  public List<User> getPendingRestaurantOwner() {
    return null;
  }

  @Override
  public void updatePassword(User user, String newPassword) {

  }
}
