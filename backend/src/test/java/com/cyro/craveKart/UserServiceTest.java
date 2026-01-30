package com.cyro.cravekart;


import com.cyro.cravekart.config.security.AuthContextService;
import com.cyro.cravekart.models.Customer;
import com.cyro.cravekart.models.User;
import com.cyro.cravekart.repository.CustomerRepository;
import com.cyro.cravekart.service.CustomerService;
import com.cyro.cravekart.service.UserService;
import com.cyro.cravekart.service.utils.UserServiceUtil;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
@SpringBootTest
public class UserServiceTest {

  @Autowired
  private AuthContextService authContextService;

  @Autowired
  private CustomerRepository customerRepository;

  @Test
  @Transactional
  public void createUserTest() {
//    String username = authContextService.getCustomer().getUser().getUsername();
//    log.info("Creating user {}", username);

    Authentication authentication =
        SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !authentication.isAuthenticated()
        || authentication instanceof AnonymousAuthenticationToken) {
      System.out.println("Authentication is null");
    }
    User principal = (User) authentication.getPrincipal();
    System.out.println(principal.getUsername());
  }
}
