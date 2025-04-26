package com.cyro.craveKart.repository;

import com.cyro.craveKart.model.Order;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends MongoRepository<Order, ObjectId> {
    @org.springframework.data.mongodb.repository.Query("{ 'customer.id': ?0 }")
    List<Order> findAllUserOrders(ObjectId userId);

    @org.springframework.data.mongodb.repository.Query("{ 'restaurant.id': ?0 }")
    List<Order> findOrdersByRestaurantId(ObjectId restaurantId);
}
