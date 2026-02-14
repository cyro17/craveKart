package com.cyro.cravekart;

import com.cyro.cravekart.config.security.AuthService;
import com.cyro.cravekart.models.FoodCategory;
import com.cyro.cravekart.models.IngredientItem;
import com.cyro.cravekart.models.Restaurant;
import com.cyro.cravekart.repository.IngredientItemRepository;
import com.cyro.cravekart.repository.RestaurantRepository;
import com.cyro.cravekart.repository.UserRepository;
import com.cyro.cravekart.request.CreateIngredientItemRequest;
import com.cyro.cravekart.response.RestaurantMenuResponse;
import com.cyro.cravekart.response.RestaurantResponse;
import com.cyro.cravekart.service.OrderService;
import com.cyro.cravekart.service.RestaurantService;
import jakarta.transaction.Transactional;
import net.minidev.json.JSONUtil;
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

	@Autowired
	private RestaurantRepository restaurantRepository;

	@Test
	@Transactional
	@Commit
	void contextLoads() {
//		Restaurant restaurant = restaurantRepository.findBySlugWithCategoriesAndFoods("Sakura Sushi").get();
//		List<FoodCategory> foodCategories = restaurant.getFoodCategories();
//		System.out.println(foodCategories);
//
//		restaurant.getFoodCategories().forEach(
//				cat-> {
//					System.out.println(cat.getFoods());
//				}
//		);

		RestaurantMenuResponse restaurantMenu = restaurantService.getRestaurantMenu(1L);
//		System.out.println(restaurant.getFoodCategories().get(0).getFoods().size());
		System.out.println(restaurantMenu);
  }
}
















