package com.cyro.cravekart.repository;

import com.cyro.cravekart.models.Customer;
import com.cyro.cravekart.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

  Optional<Customer> findByUser(User user);
  Optional<Customer> findByUserId(Long userId);
}
