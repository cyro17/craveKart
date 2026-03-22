package com.cyro.cravekart.service.impl;

import com.cyro.cravekart.config.security.AuthContextService;
import com.cyro.cravekart.exception.BadRequestException;
import com.cyro.cravekart.exception.ResourceNotFoundException;
import com.cyro.cravekart.models.*;
import com.cyro.cravekart.models.enums.OnboardingStatus;
import com.cyro.cravekart.models.enums.RestaurantStatus;
import com.cyro.cravekart.repository.*;
import com.cyro.cravekart.request.USER_STATUS;
import com.cyro.cravekart.response.OrderResponse;
import com.cyro.cravekart.response.RestaurantPartnerResponse;
import com.cyro.cravekart.response.RestaurantResponse;
import com.cyro.cravekart.response.UserResponse;
import com.cyro.cravekart.service.AdminService;
import com.cyro.cravekart.service.UserService;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminServiceImpl implements AdminService {
  private final UserService userService;
  private final UserRepository userRepository;
  private final RestaurantRepository restaurantRepository;
  private final OrderRepository orderRepository;
  private final RestaurantPartnerRepository restaurantPartnerRepository;
  private final AddressRepository addressRepository;
  private final AuthContextService authContextService;

  @Override
  public List<UserResponse> getAllUsers() {
    return userService.findAllUsers();
  }

  @Override
  public void blockUser(Long userId) {

    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

    user.setStatus(USER_STATUS.BLOCKED);
    userRepository.save(user);
  }

  @Override
  public void unblockUser(Long userId) {
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));

    user.setStatus(USER_STATUS.ACTIVE);
    userRepository.save(user);
  }

  // ================= RESTAURANTS =================

  @Override
  public List<RestaurantResponse> getPendingRestaurants() {

    return restaurantRepository.findByStatus(RestaurantStatus.PENDING).stream()
        .map(RestaurantResponse::from)
        .toList();
  }

  @Override
  @Transactional
  public void approveRestaurant(Long restaurantId) {
    Restaurant restaurant =
        restaurantRepository
            .findById(restaurantId)
            .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));

    restaurant.setStatus(RestaurantStatus.APPROVED);
    restaurantRepository.save(restaurant);
  }

  @Override
  public void suspendRestaurant(Long restaurantId) {
    Restaurant restaurant =
        restaurantRepository
            .findById(restaurantId)
            .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));

    restaurant.setStatus(RestaurantStatus.SUSPENDED);
    restaurantRepository.save(restaurant);
  }

  // =================Restaurant PARTNERS =================

  @Override
  public List<RestaurantPartnerResponse> getPendingRestaurantPartners() {
    return restaurantPartnerRepository.findByOnboardingStatus(OnboardingStatus.PENDING).stream()
        .map(RestaurantPartnerResponse::from)
        .toList();
  }

  @Override
  public RestaurantPartnerResponse getPendingRestaurantPartner(Long partnerId) {
    RestaurantPartner partner =
        restaurantPartnerRepository
            .findById(partnerId)
            .orElseThrow(() -> new ResourceNotFoundException("Restaurant partner not found"));
    return RestaurantPartnerResponse.from(partner);
  }

  @Override
  @Transactional
  public void approvePartner(Long partnerId) {
    RestaurantPartner partner =
        restaurantPartnerRepository
            .findById(partnerId)
            .orElseThrow(() -> new ResourceNotFoundException("Partner not found"));

    if (partner.getOnboardingStatus() != OnboardingStatus.PENDING) {
      throw new BadRequestException(
          "Partner is not pending for review, Current status : {}" + partner.getOnboardingStatus());
    }

    OnboardingApplication application = partner.getApplication();
    if (application == null) throw new BadRequestException("No Application found for this partner");

    Address address = this.mapAddress(application, partner);
    addressRepository.save(address);

    Restaurant restaurant = this.mapRestaurant(application, address, partner);
    restaurant.setStatus(RestaurantStatus.APPROVED);
    restaurantRepository.save(restaurant);

    partner.setRestaurant(restaurant);
    partner.setOnboardingStatus(OnboardingStatus.APPROVED);
    partner.setVerified(true);
    partner.setActive(true);
    partner.setApprovedAt(LocalDateTime.now());
    partner.setOnboardingAdmin(authContextService.getAdmin());

    application.setReviewedAt(LocalDateTime.now());

    restaurantPartnerRepository.save(partner);

    log.info("Partner {} APPROVED. Restaurant {} created.", partnerId, restaurant.getId());
  }

  @Transactional
  public void rejectPartner(Long partnerId, String reason) {
    RestaurantPartner partner =
        restaurantPartnerRepository
            .findById(partnerId)
            .orElseThrow(() -> new ResourceNotFoundException("Partner not found"));

    if (partner.getOnboardingStatus() != OnboardingStatus.PENDING) {
      throw new BadRequestException("Partner is not pending review.");
    }

    OnboardingApplication app = partner.getApplication();
    if (app != null) {
      app.setRejectionReason(reason);
      app.setReviewedAt(LocalDateTime.now());
    }

    partner.setOnboardingStatus(OnboardingStatus.REJECTED);
    partner.setRejectionReason(reason);

    restaurantPartnerRepository.save(partner);

    log.info("Partner {} REJECTED. Reason: {}", partnerId, reason);
  }

  @Override
  public void suspendPartner(Long partnerId, String reason) {

    RestaurantPartner partner =
        restaurantPartnerRepository
            .findById(partnerId)
            .orElseThrow(() -> new ResourceNotFoundException("Restaurant Partner not found"));

    if (partner.getOnboardingStatus() != OnboardingStatus.APPROVED) {
      throw new BadRequestException("Restaurant partner not active.");
    }
    partner.setOnboardingStatus(OnboardingStatus.SUSPENDED);
    partner.setActive(false);

    partner.setRejectionReason(reason);

    if (partner.getRestaurant() != null) {
      partner.getRestaurant().setOpen(false);
      restaurantRepository.save(partner.getRestaurant());
    }

    restaurantPartnerRepository.save(partner);
    log.info("Partner {} SUSPENDED. Reason {}", partnerId, reason);
  }

  @Override
  public void reactivatePartner(Long partnerId) {
    RestaurantPartner partner =
        restaurantPartnerRepository
            .findById(partnerId)
            .orElseThrow(() -> new ResourceNotFoundException("Partner not found"));

    if (partner.getOnboardingStatus() != OnboardingStatus.SUSPENDED) {
      throw new BadRequestException("Only SUSPENDED partners can be reactivated.");
    }

    partner.setOnboardingStatus(OnboardingStatus.APPROVED);
    partner.setActive(true);
    partner.setRejectionReason(null);

    if (partner.getRestaurant() != null) {
      partner.getRestaurant().setOpen(true);
      restaurantRepository.save(partner.getRestaurant());
    }

    restaurantPartnerRepository.save(partner);
    log.info("Partner {} REACTIVATED.", partnerId);
  }

  @Override
  public List<RestaurantPartnerResponse> getPartnersByStatus(OnboardingStatus status) {
    List<RestaurantPartner> restaurantPartners =
        status != null
            ? restaurantPartnerRepository.findByOnboardingStatus(status)
            : restaurantPartnerRepository.findAll();

    return restaurantPartners.stream().map(RestaurantPartnerResponse::from).toList();
  }

  // ================= ORDERS =================

  @Override
  public List<OrderResponse> getAllOrders() {

    return orderRepository.findAll().stream().map(OrderResponse::from).toList();
  }

  // UTIL

  private Address mapAddress(OnboardingApplication req, RestaurantPartner partner) {
    Address address = new Address();
    address.setFirstName(partner.getUser().getFirstName());
    address.setCity(req.getCity());
    address.setCountry(req.getCountry());
    address.setPostalCode(req.getPostalCode());
    address.setState(req.getState());
    address.setStreetAddress(req.getStreetAddress());
    return address;
  }

  private Restaurant mapRestaurant(
      OnboardingApplication req, Address address, RestaurantPartner partner) {

    return Restaurant.builder()
        .name(req.getRestaurantName())
        .cuisineType(req.getCuisineType())
        .description(req.getDescription())
        .openingHours(req.getOpeningHours())
        .address(address)
        .restaurantPartner(partner)
        .open(true)
        .build();
  }
}
