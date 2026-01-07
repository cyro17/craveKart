package com.cyro.cravekart;

import com.cyro.cravekart.models.User;
import com.cyro.cravekart.models.enums.USER_ROLE;
import com.cyro.cravekart.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import java.util.Optional;

@SpringBootTest
class CraveKartApplicationTests {

	@Autowired
	private UserRepository userRepository;

	@Test
	@Transactional
	void contextLoads() {



	}

	@Test
	@Transactional
	@Commit
	void testCreateUser() {

		User user1 = User.builder()
				.fullName("Ronit Raj")
				.email("ronraj@mail.com")
				.password("ronit123")
				.role(USER_ROLE.ADMIN)
				.status("ACTIVE").build();

		userRepository.save(user1);
	}
	
	@Test
	@Transactional
	@Commit
	void deleteUser(){
		Optional<User> user = userRepository.findById(552L);
		user.ifPresent(u-> userRepository.delete(u));

	}
}
