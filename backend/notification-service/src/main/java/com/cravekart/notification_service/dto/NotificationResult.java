package com.cravekart.notification_service.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Builder
@Data
public class NotificationResult {
    private boolean success;
    private String channel;
    private String recipient;
    private String messageId;
    private String errorMessage;
    private LocalDateTime sentAt;

    public static NotificationResult success(String channel,
                                             String recipient,
                                             String messageId){
        return NotificationResult.builder()
                .success(true)
                .channel(channel)
                .recipient(recipient)
                .messageId(messageId)
                .sentAt(LocalDateTime.now())
                .build();
    }

    public static NotificationResult failure(String channel,
                                             String recipient,
                                             String errorMessage){
        return NotificationResult.builder()
                .success(false)
                .channel(channel)
                .recipient(recipient)
                .messageId(errorMessage)
                .sentAt(LocalDateTime.now())
                .build();
    }
}
