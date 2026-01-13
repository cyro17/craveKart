package com.cyro.cravekart;

import com.cyro.cravekart.config.security.AuthService;
import com.cyro.cravekart.models.Restaurant;
import com.cyro.cravekart.models.User;
import com.cyro.cravekart.models.enums.USER_ROLE;
import com.cyro.cravekart.repository.RestaurantRepository;
import com.cyro.cravekart.repository.UserRepository;
import com.cyro.cravekart.service.RestaurantService;
import com.cyro.cravekart.service.RestaurantServiceImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.annotation.Commit;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
@Commit
class CraveKartApplicationTests {

	@Autowired
	private RestaurantService restaurantService;
	@Autowired
	private AuthService authService;
	@Autowired
	private UserRepository userRepository;

	@Test
	void contextLoads() {

	}
}
















