package com.cyro.craveKart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cyro.craveKart.model.OrderItem;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
