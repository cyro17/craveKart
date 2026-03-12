package com.cravekart.core.events.notification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class BaseNotificationEvent {

    private String userId;
    private String email;
    private String phoneNumber;
    private String fcmToken;

    private LocalDateTime createdAt;

    private NotificationChannel channel;

    public enum NotificationChannel {
        EMAIL, SMS, PUSH, ALL
    }



}
