package com.cyro.cravekart.config.security;

import com.cyro.cravekart.models.*;
import com.cyro.cravekart.repository.AdminRepository;
import com.cyro.cravekart.repository.CustomerRepository;
import com.cyro.cravekart.repository.DeliveryPartnerRepository;
import com.cyro.cravekart.repository.RestaurantPartnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthContextService {
  private final CustomerRepository customerRepository;
  private final AdminRepository adminRepository;
  private final RestaurantPartnerRepository restaurantPartnerRepository;
  private final DeliveryPartnerRepository deliveryPartnerRepository;

  public User getUser(){
    Authentication authentication =
        SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !authentication.isAuthenticated()
        || authentication instanceof AnonymousAuthenticationToken) {
      return null;
    }

    return (User) authentication.getPrincipal();
  }


  public Customer getCustomer(){
    User user = getUser();
    return  customerRepository.findById(user.getId())
        .orElseThrow(()-> new IllegalStateException("Customer not found"));
  }


  public Admin getAdmin() {
    User user = getUser();
    return adminRepository.findById(user.getId())
        .orElseThrow(() -> new IllegalStateException("Admin not found"));
  }

  public RestaurantPartner getRestaurantPartner() {
    User user = getUser();
    return restaurantPartnerRepository.findById(user.getId())
        .orElseThrow(() -> new IllegalStateException("RestaurantPartner not found"));
  }


  public DeliveryPartner getDeliveryPartner() {
    User user = getUser();
    return deliveryPartnerRepository.findById(user.getId())
        .orElseThrow(() -> new IllegalStateException("DeliveryPartner not found"));
  }

}
