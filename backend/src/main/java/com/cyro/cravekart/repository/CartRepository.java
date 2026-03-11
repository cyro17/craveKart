package com.cyro.cravekart.repository;

import com.cyro.cravekart.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

  Optional<Cart> findByCustomerId(Long userId);

  void deleteByCustomerId(Long userId);

}
