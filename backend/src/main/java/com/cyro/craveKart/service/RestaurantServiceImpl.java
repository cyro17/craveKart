package com.cyro.cravekart.service;

import com.cyro.cravekart.dto.RestaurantDto;
import com.cyro.cravekart.exception.RestaurantException;
import com.cyro.cravekart.models.Address;
import com.cyro.cravekart.models.Restaurant;
import com.cyro.cravekart.models.User;
import com.cyro.cravekart.repository.AddressRepository;
import com.cyro.cravekart.repository.RestaurantRepository;
import com.cyro.cravekart.repository.UserRepository;
import com.cyro.cravekart.request.CreateRestaurantRequest;
import com.cyro.cravekart.response.CreateRestaurantResponse;
import com.cyro.cravekart.response.RestaurantResponse;
import com.cyro.cravekart.response.UserResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class RestaurantServiceImpl implements RestaurantService {
  private final KeyValueRepository keyValueRepository;

  private final AddressRepository addressRepository;
  private final  RestaurantRepository restaurantRepository;
  private final UserRepository userRepository;



  @Override
  public CreateRestaurantResponse createRestaurant(CreateRestaurantRequest req, User user) {
    Address address=new Address();
    address.setCity(req.getAddress().getCity());
    address.setCountry(req.getAddress().getCountry());
    address.setPostalCode(req.getAddress().getPostalCode());
    address.setState(req.getAddress().getState());
    address.setStreetAddress(req.getAddress().getStreetAddress());
    Address savedAddress = addressRepository.save(address);

    Restaurant restaurant=new Restaurant();
    restaurant.setAddress(savedAddress);
    restaurant.setName(req.getName());
    restaurant.setOpeningHours(req.getOpeningHours());
    restaurant.setContactInfo(req.getContactInfo());
    restaurant.setCuisineType(req.getCuisineType());
    restaurant.setDescription(req.getDescription());
    restaurant.setOwner(user);
    restaurant.setImages(req.getImages());
    restaurant.setRegistrationDate(LocalDateTime.now());
    restaurant.setOpen(true);
    Restaurant savedRestaurant = restaurantRepository.save(restaurant);

    return CreateRestaurantResponse.builder()
        .id(savedRestaurant.getId())
        .username(user.getUsername())
        .name(savedRestaurant.getName())
        .build();
  }

  @Override
  public RestaurantResponse updateRestaurant(Long restaurantId,
                                     CreateRestaurantRequest updatedRestaurant) throws RestaurantException {
    Restaurant restaurantById = restaurantRepository
        .findById(restaurantId).orElseThrow(
            ()-> new RestaurantException("Restaurant with id " + restaurantId + " not found")
        );

    log.info("udpate request for restaurant",  restaurantById.getName());

    if(restaurantById.getCuisineType() != null ){
      restaurantById.setCuisineType(updatedRestaurant.getCuisineType());
    }
    if(restaurantById.getDescription() != null ){
      restaurantById.setDescription(updatedRestaurant.getDescription());
    }
    Restaurant savedRestaurant = restaurantRepository.save(restaurantById);
    return  RestaurantResponse.from(savedRestaurant);
  }

  @Override
  public void deleteRestaurant(Long restaurantId) throws RestaurantException {
    Restaurant restaurant = restaurantRepository
            .findById(restaurantId).orElseThrow(
        () -> new RestaurantException
            ("Restaurant not found with id :" + restaurantId)
    );
    restaurantRepository.delete(restaurant);
  }

  @Override
  public List<RestaurantResponse> getAllRestaurant() {
    List<Restaurant> restaurants = restaurantRepository.findAll();
    return  restaurants.stream()
        .map(RestaurantResponse::from)
        .toList();
  }

  @Override
  public List<RestaurantResponse> searchRestaurant(String keyword) {

    return  restaurantRepository.findBySearchQuery(keyword).stream()
        .map(RestaurantResponse::from)
        .toList();
  }

  @Override
  @Cacheable(key =  "#restaurantID", value = "restaurantById")
  public RestaurantResponse findRestaurantById(Long restaurantID)
      throws RestaurantException {
    Restaurant restaurant = restaurantRepository.findById(restaurantID)
        .orElseThrow(() -> new RestaurantException("Restaurant not found with id : " + restaurantID));

    return RestaurantResponse.from(restaurant);
  }

  @Override
  @Cacheable(value = "")
  public List<RestaurantResponse> getRestaurantsByUserId(Long userId) throws RestaurantException {
    List<Restaurant> restaurants = restaurantRepository.findByOwnerId(userId);
    return restaurants.stream()
        .map(RestaurantResponse::from)
        .toList();
  }

  @Override
  public RestaurantDto addToFavorites(Long restaurantId,
                                      User user) throws RestaurantException {
    Restaurant restaurant = restaurantRepository
        .findById(restaurantId).orElseThrow(() ->
            new RestaurantException("Restaurant not found with id : " + restaurantId));

    Set<Restaurant> favorites = user.getFavorites();
    boolean isFavoured  = favorites.contains(restaurant);

    if(isFavoured) {
      favorites.remove(restaurant);
    }else {
      favorites.add(restaurant);
    }

    userRepository.save(user);

    RestaurantDto dto = new RestaurantDto();
    dto.setId(restaurant.getId());
    dto.setTitle(restaurant.getName());
    dto.setDescription(restaurant.getDescription());
    dto.setImages(restaurant.getImages());
    return dto;
  }

  @Override
  public RestaurantResponse updateRestaurantStatus(Long id) throws RestaurantException {
    Restaurant restaurantById = restaurantRepository.findById(id).orElseThrow(
        ()->  new RestaurantException("Restaurant not found with id : " + id)
    );
    restaurantById.setOpen(!restaurantById.isOpen());
    Restaurant savedRestaurant = restaurantRepository.save(restaurantById);

    return  RestaurantResponse.from(savedRestaurant);
  }
}
