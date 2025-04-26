package com.cyro.craveKart.repository;

import com.cyro.craveKart.model.Notification;
import org.bson.types.ObjectId;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends MongoRepository<Notification, ObjectId> {

    public List<Notification> findByCustomerId(ObjectId userId);
    public List<Notification> findByRestaurantId(ObjectId restaurantId);
}
