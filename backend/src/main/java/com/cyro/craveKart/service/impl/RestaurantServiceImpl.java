package com.cyro.cravekart.service.impl;

import com.cyro.cravekart.config.security.AuthContextService;
import com.cyro.cravekart.dto.RestaurantDto;
import com.cyro.cravekart.exception.RestaurantException;
import com.cyro.cravekart.models.*;
import com.cyro.cravekart.repository.*;
import com.cyro.cravekart.request.CreateRestaurantRequest;
import com.cyro.cravekart.request.OnboardRestaurantRequest;
import com.cyro.cravekart.response.CreateRestaurantResponse;
import com.cyro.cravekart.response.OnboardRestaurantResponse;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

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
  private final RestaurantRepository restaurantRepository;
  private final UserRepository userRepository;
  private final RestaurantPartnerService restaurantPartnerService;
  private final ModelMapper modelMapper;
  private final AuthContextService authContextService;


  // ======================= CREATE ====================================


  @Override
  @Caching(evict = {
      @CacheEvict(value = "restaurantsAll", allEntries = true),
      @CacheEvict(value = "restaurantByKeyword", allEntries = true)
  })
  public OnboardRestaurantResponse onboardRestaurant(
      OnboardRestaurantRequest req, Long restaurantPartnerId) {

    // restaurant partner
    RestaurantPartner partner = restaurantPartnerRepository.findById(restaurantPartnerId).orElseThrow(
        () -> new RestaurantException("Restaurant partner not found")
    );

    if(partner.getRestaurant() != null) {
      throw new RestaurantException("Partner already has a restaurant");
    }

    Address address = mapAddress(req);

    Restaurant restaurant = modelMapper.map(req, Restaurant.class);
    restaurant.setAddress(address);
    restaurant.setRestaurantPartner(partner);
    restaurant.setOpen(true);

    Restaurant savedRestaurant = restaurantRepository.save(restaurant);

    partner.setRestaurant(savedRestaurant);
    partner.setVerified(true);
    partner.setActive(true);

    restaurantPartnerRepository.save(partner);

    return OnboardRestaurantResponse.builder()
        .id(savedRestaurant.getId())
        .partnerName(partner.getUser().getUsername())
        .name(restaurant.getName())
        .build();
  }


  @Override
  public CreateRestaurantResponse createRestaurant(CreateRestaurantRequest req) throws RestaurantException {

    // check if restaurant exists
    boolean exists = restaurantRepository.existsByNameIgnoreCase(req.getName());
    if(exists) throw new RestaurantException("Restaurant already exists");

    Address address = modelMapper.map(req.getAddressRequest(), Address.class);
    addressRepository.save(address);

    Restaurant restaurant = modelMapper.map(req, Restaurant.class);
    restaurant.setAddress(address);

    Restaurant savedRestaurant = restaurantRepository.save(restaurant);

    return CreateRestaurantResponse.builder()
        .id(savedRestaurant.getId())
        .name(savedRestaurant.getName())
        .assigned(false)
        .build();
  }

  public boolean assignPartner(Long restaurantId, Long partnerId) throws RestaurantException {
    Restaurant restaurant = getRestaurantOrThrow(restaurantId);
    if(restaurant.getRestaurantPartner() != null) throw   new RestaurantException("Restaurant already assigned");

    RestaurantPartner partner = restaurantPartnerRepository.findById(partnerId).orElseThrow(
        () -> new RestaurantException("Partner not found")
    );

    restaurant.setRestaurantPartner(partner);
    restaurant.setOpen(true);

    partner.setRestaurant(restaurant);
    partner.setActive(true);
    partner.setVerified(true);

    restaurantRepository.save(restaurant);
    RestaurantPartner savedPartner = restaurantPartnerRepository.save(partner);
    if(savedPartner != null) {return true;}
    return  false;

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

  private Address mapAddress(OnboardRestaurantRequest req) {
    Address address = new Address();
    address.setCity(req.getAddressRequest().getCity());
    address.setCountry(req.getAddressRequest().getCountry());
    address.setPostalCode(req.getAddressRequest().getPostalCode());
    address.setState(req.getAddressRequest().getState());
    address.setStreetAddress(req.getAddressRequest().getStreetAddress());
    return addressRepository.save(address);
  }

}
