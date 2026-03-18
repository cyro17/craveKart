package com.cyro.cravekart.service.impl;

import com.cyro.cravekart.config.security.AuthContextService;
import com.cyro.cravekart.dto.*;
import com.cyro.cravekart.exception.ResourceNotFoundException;
import com.cyro.cravekart.models.Address;
import com.cyro.cravekart.models.Customer;
import com.cyro.cravekart.models.User;
import com.cyro.cravekart.repository.AddressRepository;
import com.cyro.cravekart.repository.CustomerRepository;
import com.cyro.cravekart.request.AddCartItemRequest;
import com.cyro.cravekart.request.AddressRequest;
import com.cyro.cravekart.response.AddressResponse;
import com.cyro.cravekart.response.CartResponse;
import com.cyro.cravekart.response.OrderResponse;
import com.cyro.cravekart.service.*;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CustomerServiceImpl implements CustomerService {
  private final AddressRepository addressRepository;
  private final CustomerRepository customerRepository;
  private final FoodService foodService;
  private final RestaurantService restaurantService;
  private final AuthContextService authService;
  private final CartService cartService;
  private final ModelMapper modelMapper;
  private final AddressService addressService;

  @Override
  public CartResponse addFoodToCart(Long foodId) {
    AddCartItemRequest cartItemRequest =
        AddCartItemRequest.builder().foodId(foodId).quantity(1).build();
    return cartService.addItem(cartItemRequest);
  }

  @Override
  public CartItemDto incrementCartItemQuantity(Long cartItemId, Long cartId, Integer quantity) {

    return null;
  }

  @Override
  public CartItemDto decrementCartItemQuantity(Long cartItemId, Long cartId, Integer quantity) {
    return null;
  }

  @Override
  public CartDto removeCartItemFromCart(Long cartItemId, Long cartId) {
    return null;
  }

  @Override
  public void deletecart(Long cartId) {}

  @Override
  public OrderResponse placeOrder() {
    return null;
  }

  @Override
  public OrderResponse cancelOrder(Long OrderId) {
    return null;
  }

  @Override
  public Page<CustomerOrderDTO> getAllMyOrders(PageRequest pageRequest) {
    return null;
  }

  @Override
  public CustomerDto getMyProfile() {
    User customer = authService.getCustomer().getUser();
    List<Address> customerAddresses = addressRepository.findByCustomerId(customer.getId());
    return CustomerDto.builder()
        .customerName(customer.getFirstName())
        .customerEmail(customer.getEmail())
        .customerPhone(customer.getContact().getMobile())
        .build();
  }

  @Override
  public CartDto getCartById(Long cartId) {
    return null;
  }

  @Override
  public Page<RestaurantDto> getAllNearByRestaurants(PageRequest pageRequest) {
    return null;
  }

  @Override
  public AddressResponse saveAddress(AddressRequest request) {
    Customer user = authService.getCustomer();

    Customer customer =
        customerRepository
            .findById(user.getId())
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "Customer with id " + user.getId() + " not found"));
    return addressService.createAddress(request, customer.getId());
  }

  @Override
  public List<AddressResponse> getUserAddress() {
    Customer user = authService.getCustomer();
    return addressService.getCustomerAddresses(user.getId());
  }
}
