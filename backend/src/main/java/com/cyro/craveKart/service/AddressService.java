package com.cyro.cravekart.service;

import com.cyro.cravekart.models.Address;
import com.cyro.cravekart.request.AddressRequest;
import com.cyro.cravekart.response.AddressResponse;

import java.util.List;

public interface AddressService {

  AddressResponse createAddress(AddressRequest addressRequest, Long customerId  );
  List<AddressResponse> getCustomerAddresses(Long customerId);

  AddressResponse getAddressById(Long addressId);
  void deleteAddress(Long addressId);

  AddressResponse setDefaultAddress(Long addressId, Long customerId);

}
