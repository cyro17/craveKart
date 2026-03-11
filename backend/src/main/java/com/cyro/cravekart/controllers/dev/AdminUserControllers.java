package com.cyro.cravekart.controllers.dev;


import com.cyro.cravekart.config.security.AuthContextService;
import com.cyro.cravekart.models.enums.USER_ROLE;
import com.cyro.cravekart.repository.UserRepository;
import com.cyro.cravekart.response.UserResponse;
import com.cyro.cravekart.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Secured("ROLE_ADMIN")
public class AdminUserControllers {
  private final UserService userService;
  private final UserRepository userRepository;
  private final AuthContextService authService;

  @GetMapping("/check")
  public ResponseEntity<String> healthCheck(){
    for (USER_ROLE role : authService.getCustomer().getUser().getRoles()) {
      System.out.println(role);
    }
    return ResponseEntity.ok("OK");
  }

  @GetMapping("/users")
  public ResponseEntity<List<UserResponse>> getAllUsers(){
    List<UserResponse> users = userService.findAllUsers();
    return  new  ResponseEntity<>(users, HttpStatus.CREATED);
  }

  @GetMapping("/users/{id}")
  public ResponseEntity<UserResponse> getUserById(@PathVariable long id){
    return  new ResponseEntity<>(userService.getByUserId(id),  HttpStatus.OK);
  }

  @GetMapping("/users/email")
  public ResponseEntity<UserResponse> getUserByEmail(@RequestParam String email){
    return new ResponseEntity<>(userService.getUserByEmail(email), HttpStatus.OK);
  }

  @DeleteMapping("/users/{user_id}")
  public ResponseEntity<String> deleteUser(@PathVariable("user_id") Long user_id){
    boolean bool = userService.removeByUserId(user_id);
    if(bool)return  new ResponseEntity<>("User successfully deleted", HttpStatus.ACCEPTED);

    return  new ResponseEntity<>( HttpStatus.UNAUTHORIZED);
  }







}
