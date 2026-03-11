package com.cyro.cravekart.service.impl;

import com.cyro.cravekart.models.Address;
import com.cyro.cravekart.models.Customer;
import com.cyro.cravekart.repository.AddressRepository;
import com.cyro.cravekart.repository.CustomerRepository;
import com.cyro.cravekart.request.AddressRequest;
import com.cyro.cravekart.response.AddressResponse;
import com.cyro.cravekart.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

  private final AddressRepository addressRepository;
  private final CustomerRepository customerRepository;
  private final ModelMapper modelMapper;

  @Override
  public AddressResponse createAddress(AddressRequest addressRequest, Long customerId) {
    Customer customer = customerRepository.findById(customerId).orElseThrow(
        () -> new RuntimeException("Customer with id " + customerId + " not found")
    );

    if(Boolean.TRUE.equals(addressRequest.getIsDefault()))
      clearDefaultAddress(customerId);

    Address address = Address.builder()
        .firstName(addressRequest.getFirstName())
        .lastName(addressRequest.getLastName())
        .streetAddress(addressRequest.getStreetAddress())
        .landmark(addressRequest.getLandmark())
        .city(addressRequest.getCity())
        .state(addressRequest.getState())
        .postalCode(addressRequest.getPostalCode())
        .country(addressRequest.getCountry())
        .isDefault(addressRequest.getIsDefault())
        .latitude(addressRequest.getLatitude())
        .longitude(addressRequest.getLongitude())
        .deliveryInstruction(addressRequest.getDeliveryInstruction())
        .customer(customer)
        .build();

    Address savedAddress = addressRepository.save(address);
    return mapToResponse(savedAddress);

  }

  @Override
  public List<AddressResponse> getCustomerAddresses(Long customerId) {
    List<Address> list = addressRepository.findByCustomerId(customerId);
    return list.stream()
            .map(this::mapToResponse)
            .toList();
  }

  @Override
  public AddressResponse getAddressById(Long addressId) {
    Address address = addressRepository.findById(addressId).orElseThrow(
        () -> new RuntimeException("Address with id " + addressId + " not found")
    );
    return mapToResponse(address);
  }

  @Override
  public void deleteAddress(Long addressId) {
    addressRepository.deleteById(addressId);
  }

  @Override
  public AddressResponse setDefaultAddress(Long addressId, Long customerId) {
    clearDefaultAddress(customerId);

    Address address = addressRepository.findById(addressId).orElseThrow(
        () -> new RuntimeException("Address with id " + addressId + " not found")
    );
    address.setIsDefault(Boolean.TRUE);
    Address saved = addressRepository.save(address);
    return mapToResponse(saved);
  }

  private void clearDefaultAddress(Long customerId) {
    List<Address> addresses = addressRepository.findByCustomerId(customerId);
    addresses.forEach(
        add-> add.setIsDefault(Boolean.FALSE)
    );
    addressRepository.saveAll(addresses);
  }

  private  AddressResponse mapToResponse(Address address) {
      return  modelMapper.map(address, AddressResponse.class);
  }
}
