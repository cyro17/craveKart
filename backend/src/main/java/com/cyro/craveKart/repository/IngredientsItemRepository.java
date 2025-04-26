package com.cyro.craveKart.repository;

import com.cyro.craveKart.model.IngredientsItem;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientsItemRepository extends MongoRepository<IngredientsItem, ObjectId> {
    @Query("{ 'restaurant.id': ?0 }")
    List<IngredientsItem> findByRestaurantId(Long restaurantId);


    @Query("{ 'restaurant.id': ?0, 'name': { $regex: ?1, $options: 'i' }, 'category.name': ?2 }")
    public IngredientsItem findByRestaurantIdAndNameIngoreCase(
            ObjectId restaurantId,
            String name,
            String categoryName);
}
