package com.cyro.craveKart.repository;

import com.cyro.craveKart.model.OrderItem;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderItemRepository extends MongoRepository<OrderItem, ObjectId> {

}
