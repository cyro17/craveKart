package com.cyro.cravekart.service;

import com.cyro.cravekart.exception.RestaurantException;
import com.cyro.cravekart.models.Address;
import com.cyro.cravekart.models.Restaurant;
import com.cyro.cravekart.models.User;
import com.cyro.cravekart.repository.AddressRepository;
import com.cyro.cravekart.repository.RestaurantRepository;
import com.cyro.cravekart.request.CreateRestaurantRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RestaurantServiceImpl implements RestaurantService {

  @Autowired
  private AddressRepository addressRepository;
  @Autowired
  private RestaurantRepository restaurantRepository;

  @Override
  public Restaurant createRestaurant(CreateRestaurantRequest req, User user) {
    Address address=new Address();
    address.setCity(req.getAddress().getCity());
    address.setCountry(req.getAddress().getCountry());
    address.setFullName(req.getAddress().getFullName());
    address.setPostalCode(req.getAddress().getPostalCode());
    address.setState(req.getAddress().getState());
    address.setStreetAddress(req.getAddress().getStreetAddress());
    Address savedAddress = addressRepository.save(address);

    Restaurant restaurant=new Restaurant();
    restaurant.setAddress(savedAddress);
    restaurant.setName(req.getName());
    restaurant.setOpeningHours(req.getOpeningHours());
    restaurant.setContactInfo(req.getContactInformation());
    restaurant.setCuisineType(req.getCuisineType());
    restaurant.setDescription(req.getDescription());
    restaurant.setOwner(user);
    restaurant.setImages(req.getImages());

    Restaurant savedRestaurant = restaurantRepository.save(restaurant);
    return savedRestaurant;
  }

  @Override
  public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updatedRestaurant) throws RestaurantException {
    Restaurant restaurantById = findRestaurantById(restaurantId);
    return restaurantById;
  }

  @Override
  public void deleteRestaurant(Long restaurantId) throws RestaurantException {
    Restaurant restaurantById = findRestaurantById(restaurantId);
    if(restaurantById != null) {
      restaurantRepository.delete(restaurantById);
      return;
    }
    throw new RestaurantException("Restaurant not found with id :" + restaurantId);
  }

  @Override
  public List<Restaurant> getAllRestaurant() {
    return restaurantRepository.findAll();
  }

  @Override
  public List<Restaurant> searchRestaurant(String keyword) {
    return restaurantRepository.findBySearchQuery(keyword);
  }

  @Override
  public Restaurant findRestaurantById(Long id) throws RestaurantException {
    Restaurant restaurant = restaurantRepository.findById(id)
        .orElseThrow(() -> new RestaurantException("Restaurant not found with id : " + id));
    return restaurant;
  }

  @Override
  public Restaurant getRestaurantsByUserId(Long userId) throws RestaurantException {
    return restaurantRepository.findByOwnerId(userId);
  }

  @Override
  public Restaurant updateRestaurantStatus(Long id) throws RestaurantException {
    Restaurant restaurantById = findRestaurantById(id);
    restaurantById.setOpen(!restaurantById.isOpen());
    return restaurantRepository.save(restaurantById);
  }
}
