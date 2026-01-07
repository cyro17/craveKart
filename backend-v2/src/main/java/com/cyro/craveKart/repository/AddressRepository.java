package com.cyro.craveKart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cyro.craveKart.model.Address;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

}
