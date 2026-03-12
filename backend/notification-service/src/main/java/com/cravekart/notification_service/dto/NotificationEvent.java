package com.cravekart.notification_service.dto;

import com.cravekart.core.events.notification.BaseNotificationEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class NotificationEvent {
    private String userId;
    private String email;
    private String phoneNumber;
    private String fcmToken;

    private String subject;
    private String body;
    private Map<String, Object> templateVars;
    private String templateName;

    private NotificationChannel channel;
    private NotificationType type;

    private String referenceId;
    private LocalDateTime createdAt;

    private enum NotificationChannel {
        EMAIL, SMS, PUSH, ALL
    }

    private enum NotificationType {
        ORDER, PROMO, ALERT
    }


}
