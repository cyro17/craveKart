package com.cyro.cravekart.repository;

import com.cyro.cravekart.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
  List<Address> findByCustomerId(Long customerId);
}
