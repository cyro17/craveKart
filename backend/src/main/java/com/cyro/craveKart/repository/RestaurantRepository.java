package com.cyro.craveKart.repository;

import com.cyro.craveKart.model.Restaurant;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface RestaurantRepository extends MongoRepository<Restaurant, ObjectId> {

    @Query("{ $or: [ { 'name': { $regex: ?0, $options: 'i' } }, { 'cuisineType': { $regex: ?0, $options: 'i' } } ] }")
    List<Restaurant> findBySearchQuery(String query);

    Restaurant findByOwnerId(ObjectId userId);

}
