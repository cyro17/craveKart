package com.cyro.craveKart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cyro.craveKart.model.Notification;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

	public List<Notification> findByCustomerId(Long userId);
	public List<Notification> findByRestaurantId(Long restaurantId);

}
