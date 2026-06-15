package com.cyro.cravekart.repository;

import com.cyro.cravekart.models.Cart;
import io.lettuce.core.dynamic.annotation.Param;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

  @Query(
      "SELECT c FROM Cart c "
          + "LEFT JOIN FETCH c.items ci "
          + "LEFT JOIN FETCH ci.food f "
          + "LEFT JOIN FETCH f.restaurant r "
          + "WHERE c.customer.id = :customerId")
  Optional<Cart> findByCustomerIdWithItems(@Param("customerId") Long customerId);

  Optional<Cart> findByCustomerId(Long userId);

  void deleteByCustomerId(Long userId);
}
