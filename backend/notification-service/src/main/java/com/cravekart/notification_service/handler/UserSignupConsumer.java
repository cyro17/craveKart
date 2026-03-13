package com.cravekart.notification_service.handler;


import com.cravekart.core.events.notification.UserSignupEvent;
import com.cravekart.notification_service.dto.NotificationEvent;
import com.cravekart.notification_service.dto.NotificationResult;
import com.cravekart.notification_service.service.EmailNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserSignupConsumer {

    private final EmailNotificationService emailNotificationService;

    @KafkaListener(
            topics = "notification-topic",
            groupId = "notif-group",
            containerFactory = "notificationListenerFactory"
    )
    public void consume(
        UserSignupEvent event
    ){
        log.info("Received USER_SIGNUP event | userId: {} | email: {} | ",
                event.getUserId(), event.getEmail());

        try{
            NotificationEvent notificationEvent = NotificationEvent.builder()
                    .userId(event.getUserId())
                    .email(event.getEmail())
                    .subject("Welcome to Cravekart, " + event.getFirstName() + "!\uD83C\uDF89")
                    .templateName("welcome-email")
                    .body("Welcome to cravekart, start ordering...")
                    .templateVars(Map.of(
                            "firstName", event.getFirstName(),
                            "email", event.getEmail(),
                            "username", event.getUsername(),
                            "role", formatRole(event.getRole())
                    ))

                    .build();

            NotificationResult result = emailNotificationService.send(notificationEvent);
            if(result.isSuccess()){
                log.info("Welcome email sent | to: {} | messageId: {}",
                        event.getEmail(), result.getMessageId());
            } else {
                log.error("Failed to send welcome email | to: {} | reason: {}",
                        event.getEmail(), result.getErrorMessage());
            }


        } catch (Exception e) {
            log.error("Failed to process USER_SIGNUP event for userId {}: {}",
                    event.getUserId(), e.getMessage());
            throw e;
        }

    }

    private String formatRole(String role){
        if(role == null) return  "Customer";
        return switch (role){
            case "RESTAURANT_PARTNER"-> "Restaurant Partner";
            case "DELIVERY_PARTNER"-> "Delivery Partner";
            case "ADMIN"-> "Admin";
            default -> "Customer";

        };
    }
}
