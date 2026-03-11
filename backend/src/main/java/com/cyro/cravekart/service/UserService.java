package com.cyro.cravekart.service;

import com.cyro.cravekart.exception.UserException;
import com.cyro.cravekart.models.User;
import com.cyro.cravekart.response.UserResponse;

import java.util.List;

public interface UserService {

  public List<UserResponse> findAllUsers();

  public UserResponse getByUserId(Long userId);

  public UserResponse getUserByEmail(String email);

  void updatePassword(User user, String newPassword);

  boolean removeByUserId(Long userId);

  public void sendPasswordResetEmail(User user);


}
