package com.cyro.craveKart.repository;

import com.cyro.craveKart.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {

    List<User> findByStatus(String status);

    User findByEmail(String username);
}
