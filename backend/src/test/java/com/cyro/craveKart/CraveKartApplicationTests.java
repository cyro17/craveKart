package com.cyro.cravekart;

import com.cyro.cravekart.config.security.AuthService;
import com.cyro.cravekart.models.IngredientItem;
import com.cyro.cravekart.models.Restaurant;
import com.cyro.cravekart.repository.IngredientItemRepository;
import com.cyro.cravekart.repository.RestaurantRepository;
import com.cyro.cravekart.repository.UserRepository;
import com.cyro.cravekart.request.CreateIngredientItemRequest;
import com.cyro.cravekart.response.RestaurantResponse;
import com.cyro.cravekart.service.OrderService;
import com.cyro.cravekart.service.RestaurantService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import java.util.List;

@SpringBootTest
@Transactional
@Commit
class CraveKartApplicationTests {

	@Autowired
	private RestaurantService restaurantService;

	@Autowired
	private RestaurantRepository repository;
	@Autowired
	private AuthService authService;
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private IngredientItemRepository  ingredientItemRepository;

	@Autowired
	private OrderService orderService;

	@Test
	@Transactional
	@Commit
	void contextLoads() {
//		List<RestaurantResponse> restaurants = restaurantService.getAllRestaurant();
//		System.out.println(restaurants);

	}
}
















