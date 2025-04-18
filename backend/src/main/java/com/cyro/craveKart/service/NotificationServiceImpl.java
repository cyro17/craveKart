package com.cyro.craveKart.service;

import com.cyro.craveKart.model.Notification;
import com.cyro.craveKart.model.Order;
import com.cyro.craveKart.model.Restaurant;
import com.cyro.craveKart.model.User;
import com.cyro.craveKart.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public Notification sendOrderStatusNotification(Order order) {
        Notification notification = new Notification();
        notification.setMessage("your order is "+order.getOrderStatus()+ " order id is - "+order.getId());
        notification.setCustomer(order.getCustomer());
        notification.setSentAt(new Date());

        return notificationRepository.save(notification);
    }

    @Override
    public void sendRestaurantNotification(Restaurant restaurant, String message) {
        // TODO Auto-generated method stub

    }

    @Override
    public void sendPromotionalNotification(User user, String message) {
        // TODO Auto-generated method stub

    }

    @Override
    public List<Notification> findUsersNotification(Long userId) {
        // TODO Auto-generated method stub
        return notificationRepository.findByCustomerId(userId);
    }
}
