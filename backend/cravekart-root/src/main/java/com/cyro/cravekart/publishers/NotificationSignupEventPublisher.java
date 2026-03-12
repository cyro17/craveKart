package com.cyro.cravekart.events.notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
@RequiredArgsConstructor
public class NotificationSignupEventPublisher {
    private final KafkaTemplate<String, Object> kafkaTemplate;


    public void publishUserSignup(UserSignupEvent userSignupEvent) {
        userSignupEvent.setCreatedAt(LocalDateTime.now());
        publish("notification-topic", userSignupEvent.getUserId(), userSignupEvent );

    }

    private void publish(String topic, String key, BaseNotificationEvent event){
        CompletableFuture<SendResult<String, Object>> future =
                kafkaTemplate.send(topic, key, event);

        future.whenComplete((res, ex)-> {
            if(ex != null){
                log.error("Failed to publish | topic : {} | key : {} | error: {}",
                        topic, key, ex);
            } else {
                log.info("Successfully published | topic : {} | key : {} | offset ={}",
                        topic, key, res.getRecordMetadata().offset());
            }
        });


    }
}
