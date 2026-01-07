package com.cyro.craveKart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cyro.craveKart.model.CartItem;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {


//    CartItem findByFoodIsContaining

}
