package com.cyro.craveKart.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cyro.craveKart.model.Cart;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

	 Optional<Cart> findByCustomer_Id(Long userId);
}
