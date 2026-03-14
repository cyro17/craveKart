package com.cyro.cravekart.publishers;

import com.cravekart.core.events.notification.OrderConfirmedEvent;
import com.cyro.cravekart.config.kafka.KafkaTopicConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


@Component
@Slf4j
@RequiredArgsConstructor
public class OrderEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishOrderPaid(OrderConfirmedEvent event){
        String key = event.getOrderId().toString();

        kafkaTemplate.send(KafkaTopicConfiguration.NOTIF_ORDER_PAID,
                key,
                event)
                .whenComplete((result, ex)->{
                    if(ex != null ){
                        log.error("Failed to publish order confirmed event | orderId = {}, error = {} ",
                                event.getOrderId(), ex.getMessage());
                    }else {
                        log.info("Published notif.order.paid for orderId= {} | offset = {} ",
                                event.getOrderId(),
                                result.getRecordMetadata().offset());
                    }
                });
    }
}
