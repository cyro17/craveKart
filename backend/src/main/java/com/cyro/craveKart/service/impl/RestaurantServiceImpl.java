package com.cyro.cravekart.service.impl;

import com.cyro.cravekart.dto.RestaurantDto;
import com.cyro.cravekart.exception.RestaurantException;
import com.cyro.cravekart.models.*;
import com.cyro.cravekart.repository.*;
import com.cyro.cravekart.request.CreateRestaurantRequest;
import com.cyro.cravekart.response.CreateRestaurantResponse;
import com.cyro.cravekart.response.RestaurantResponse;
import com.cyro.cravekart.service.RestaurantPartnerService;
import com.cyro.cravekart.service.RestaurantService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class RestaurantServiceImpl implements RestaurantService {
  private final CustomerRepository customerRepository;
  private final RestaurantPartnerRepository restaurantPartnerRepository;

  private final AddressRepository addressRepository;
  private final  RestaurantRepository restaurantRepository;
  private final UserRepository userRepository;
  private final RestaurantPartnerService restaurantPartnerService;
  private final ModelMapper modelMapper;


  // ======================= CREATE ====================================

  @Override
  @Caching(evict = {
      @CacheEvict(value = "restaurantsAll", allEntries = true),
      @CacheEvict(value = "restaurantByKeyword", allEntries = true)
  })
  public CreateRestaurantResponse createRestaurant(
      CreateRestaurantRequest req, RestaurantPartner partner) {
    // restaurant partner
    // save address
    Address address = mapAddress(req);

    // create restaurant
    Restaurant restaurant = Restaurant.builder()
        .name(req.getName())
        .description(req.getDescription())
        .cuisineType(req.getCuisineType())
        .openingHours(req.getOpeningHours())
        .contactInfo(req.getContactInfo())
        .images(req.getImages())
        .address(address)
        .restaurantPartner(partner)
        .registrationDate(LocalDateTime.now())
        .open(true)
        .build();

    Restaurant savedRestaurant = restaurantRepository.save(restaurant);

    return CreateRestaurantResponse.builder()
        .id(savedRestaurant.getId())
        .partnerName(partner.getUser().getUsername())
        .name(restaurant.getName())
        .build();
  }


//  ===========================READ ===========================================

  @Override
  @Cacheable(value = "restaurants")
  public List<RestaurantResponse> getAllRestaurant() {
    return  restaurantRepository.findAll()
        .stream()
        .map(RestaurantResponse::from)
        .toList();
  }

  @Override
  @Cacheable(value = "restaurantById", key = "#id")
  public RestaurantResponse getRestaurantById(Long id)
      throws RestaurantException {

    Restaurant restaurant = getRestaurantOrThrow(id);
    log.info("getRestaurantById restaurant {}", restaurant.getName());
    return RestaurantResponse.from(restaurant);
  }

  @Override
  public Restaurant getRestaurantByPartnerId(Long partnerId) throws RestaurantException {
    return  restaurantRepository.findByRestaurantPartner(partnerId);
  }

  @Override
  @Cacheable(value = "restaurantByKeyword", key = "#keyword")
  public List<RestaurantResponse> searchRestaurant(String keyword) {
    return restaurantRepository
        .findBySearchQuery(keyword)
        .stream()
        .map(RestaurantResponse::from)
        .toList();
  }

//  ===================== update ========================

  @Override
  public RestaurantResponse updateRestaurant(
      Long restaurantId, CreateRestaurantRequest req)
      throws RestaurantException {

    Restaurant restaurant = getRestaurantOrThrow(restaurantId);

    log.info("udpate request for restaurant",  restaurant.getName());

    if (req.getCuisineType() != null) {
      restaurant.setCuisineType(req.getCuisineType());
    }
    if (req.getDescription() != null) {
      restaurant.setDescription(req.getDescription());
    }
    if (req.getOpeningHours() != null) {
      restaurant.setOpeningHours(req.getOpeningHours());
    }
    if (req.getContactInfo() != null) {
      restaurant.setContactInfo(req.getContactInfo());
    }

    return  RestaurantResponse.from(restaurantRepository.save(restaurant));
  }


  @Override
  public RestaurantDto addToFavorites(Long restaurantId,
                                      Customer customer)
      throws RestaurantException {

    Restaurant restaurant = getRestaurantOrThrow(restaurantId);
    Set<Restaurant> favorites = customer.getFavoriteRestaurants();
    boolean isFavoured  = favorites.contains(restaurant);

    if(isFavoured) {
      favorites.remove(restaurant);
    }else {
      favorites.add(restaurant);
    }

    customerRepository.save(customer);

    return modelMapper.map(restaurant,  RestaurantDto.class);
  }

  @Override
  public RestaurantResponse updateRestaurantStatus(Long id) throws RestaurantException {
    Restaurant restaurant = getRestaurantOrThrow(id);
    restaurant.setOpen(!restaurant.isOpen());

    restaurantRepository.save(restaurant);

    return  RestaurantResponse.from(restaurant);
  }


  // ================= DELETE =================

  @Override
  public void deleteRestaurant(Long restaurantId) throws RestaurantException {
    Restaurant restaurant = getRestaurantOrThrow(restaurantId);
    restaurantRepository.delete(restaurant);
  }





//  ============================ UTIL ===================================


  public Restaurant getRestaurantById_util(Long id) throws RestaurantException {
    return   restaurantRepository.findById(id).orElseThrow(
        ()->  new RestaurantException("Restaurant not found with id : " + id)
    );
  }

  private Restaurant getRestaurantOrThrow(Long id) {
    return restaurantRepository.findById(id)
        .orElseThrow(() ->
            new RestaurantException("Restaurant not found with id : " + id));
  }

  private Address mapAddress(CreateRestaurantRequest req) {
    Address address = new Address();
    address.setCity(req.getAddress().getCity());
    address.setCountry(req.getAddress().getCountry());
    address.setPostalCode(req.getAddress().getPostalCode());
    address.setState(req.getAddress().getState());
    address.setStreetAddress(req.getAddress().getStreetAddress());
    return addressRepository.save(address);
  }

}
