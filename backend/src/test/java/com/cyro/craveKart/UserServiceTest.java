package com.cyro.cravekart;


import com.cyro.cravekart.service.UserService;
import com.cyro.cravekart.service.utils.UserServiceUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest {

  @Autowired
  private UserServiceUtil userService;

  @Test
  public void createUserTest() {

    userService.sendMail("coolronitchs@gmail.com",
        "Cravekart test", "hey there! cravekart is live ☺️");



  }
}
