package com.cyro.craveKart.repository;

import com.cyro.craveKart.model.Category;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CategoryRepository extends MongoRepository<Category, ObjectId> {
    public List<Category> findByRestaurantId(ObjectId id);
}