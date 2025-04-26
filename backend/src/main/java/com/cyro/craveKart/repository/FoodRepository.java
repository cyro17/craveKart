package com.cyro.craveKart.repository;

import com.cyro.craveKart.model.Food;
import org.bson.types.ObjectId;

import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends MongoRepository<Food, ObjectId> {

    List<Food> findByRestaurantId(ObjectId restaurantId);
    @Query("{ $or: [ "
            + "{ 'name': { $regex: ?0, $options: 'i' } }, "
            + "{ 'foodCategory.name': { $regex: ?0, $options: 'i' } } ], "
            + "'restaurant': { $ne: null } }")
    List<Food> searchByNameOrCategory(String keyword);
}
