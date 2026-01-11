package com.cyro.cravekart.controllers;


import com.cyro.cravekart.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/users")
public class UserController {

  @Autowired
  private UserServiceImpl userService;


}
