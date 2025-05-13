package com.cyro.craveKart.controller;

import com.cyro.craveKart.exception.UserException;
import com.cyro.craveKart.model.User;
import com.cyro.craveKart.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/healthCheck")
    public ResponseEntity<String> healthCheck(){
        return  new ResponseEntity<>("Health check ok !! ", HttpStatus.OK);
    }

    @GetMapping("/profile")
    public ResponseEntity<User> findUserByJwtToken(
            @RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserProfileByJwtToken(jwt);
        user.setPassword(null);
        return  new ResponseEntity<>(user, HttpStatus.OK);
    }
}
