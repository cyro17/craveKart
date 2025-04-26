package com.cyro.craveKart.service;

import com.cyro.craveKart.config.JwtProvider;
import com.cyro.craveKart.exception.UserException;
import com.cyro.craveKart.model.User;
import com.cyro.craveKart.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public User findUserProfileByJwtToken(String jwt) throws UserException {
        String email = jwtProvider.getEmailFromJwtToken(jwt);
        if(email == null) throw new UserException("Email not present in token");
        System.out.println("email from jwt token -" + email);
        User user = userRepository.findByEmail(email);
        if(user == null ) throw new UserException("user not exists with email - " + email);
        return user;
    }


    @Override
    public User findUserByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);
        if(user == null ) throw  new Exception("user not found !! ");

        return user;
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getPendingRestaurantOwner() {
        return userRepository.findByStatus("PENDING");
    }

    @Override
    public void updatePassword(User user, String newPassword) {
        return;
    }

    @Override
    public void sendPasswordResetEmail(User user) {
        return;
    }
}
