package com.cyro.craveKart.service;

import com.cyro.craveKart.exception.UserException;
import com.cyro.craveKart.model.User;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserService {

    public User findUserProfileByJwtToken(String jwt) throws UserException;

    public User findUserByEmail(String email) throws Exception;

    public List<User> findAllUsers();

    public List<User> getPendingRestaurantOwner();

    void updatePassword(User user, String newPassword);

    void sendPasswordResetEmail(User user);
}


