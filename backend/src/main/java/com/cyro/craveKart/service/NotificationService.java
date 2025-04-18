package com.cyro.craveKart.service;

import com.cyro.craveKart.model.Notification;
import com.cyro.craveKart.model.Order;
import com.cyro.craveKart.model.Restaurant;
import com.cyro.craveKart.model.User;

import java.util.List;

public interface NotificationService {

    public Notification sendOrderStatusNotification(Order order);
    public void sendRestaurantNotification(Restaurant restaurant, String message);
    public void sendPromotionalNotification(User user, String message);

    public List<Notification> findUsersNotification(Long userId);
}
