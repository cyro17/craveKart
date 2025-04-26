package com.cyro.craveKart.repository;

import com.cyro.craveKart.model.IngredientCategory;
import org.bson.types.ObjectId;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientsCategoryRepository extends MongoRepository<IngredientCategory, ObjectId> {

    @Query("{ 'restaurant.id': ?0 }")
    List<IngredientCategory> findByRestaurantId(ObjectId restaurantId);

    @Query("{ 'restaurant.id': ?0, 'name': { $regex: ?1, $options: 'i' } }")
    IngredientCategory findByRestaurantIdAndNameIgnoreCase(
            ObjectId restaurantId,
            String name
    );

}
