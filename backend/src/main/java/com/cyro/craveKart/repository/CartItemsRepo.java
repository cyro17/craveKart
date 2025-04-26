package com.cyro.craveKart.repository;

import com.cyro.craveKart.model.CartItem;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemsRepo extends MongoRepository<CartItem, ObjectId> {
}
