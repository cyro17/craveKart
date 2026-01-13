package com.cyro.cravekart.service;

import com.cyro.cravekart.exception.UserException;
import com.cyro.cravekart.models.User;

import java.util.List;

public interface UserService {
  public User findUserProfileByJwt(String jwt) throws UserException;

  public User findUserByEmail(String email) throws UserException;

  public List<User> findAllUsers();

  public List<User> getPendingRestaurantOwner();

  void updatePassword(User user, String newPassword);

  List<User> saveAll(List<User> users);
}
