package com.cyro.cravekart.service;

import com.cyro.cravekart.exception.UserException;
import com.cyro.cravekart.models.User;
import com.cyro.cravekart.response.UserResponse;

import java.util.List;

public interface UserService {

  public List<User> findAllUsers();
  public User getByUserId(Long userId);
  public User getUserByEmail(String email);
  void updatePassword(User user, String newPassword);
  boolean removeByUserId(Long userId);


//  public User findUserProfileByJwt(String jwt) throws UserException;

//  public User findUserByEmail(String email) throws UserException;

//  public List<User> getPendingRestaurantOwner();

//  List<User> saveAll(List<User> users);


}
