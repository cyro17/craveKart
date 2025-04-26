package com.cyro.craveKart.repository;

import com.cyro.craveKart.model.Cart;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CartRepository extends MongoRepository<Cart, ObjectId> {
    Optional<Cart> findByCustomer_Id(ObjectId userId);
}
