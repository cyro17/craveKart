package com.cyro.cravekart.config.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfiguration {
  public static final String ORDER_CREATED = "order-created";
  public static final String PAYMENT_INITIATED = "payment-initiated";
  public static final String PAYMENT_SUCCESS = "payment-success";
  public static final String PAYMENT_FAILED = "payment-failed";
  public static final String PAYMENT_CANCELLED = "payment-cancelled";


  @Bean
  public NewTopic orderCreatedTopic() {
    return TopicBuilder.name(ORDER_CREATED)
        .partitions(3)
        .replicas(1)
        .build();
  }

  @Bean
  public NewTopic paymentInitiatedTopic() {
    return TopicBuilder.name(PAYMENT_INITIATED)
        .partitions(3)
        .replicas(1)
        .build();
  }

  @Bean
  public NewTopic paymentSuccessTopic() {
    return TopicBuilder.name(PAYMENT_SUCCESS)
        .partitions(3)
        .replicas(1)
        .build();
  }

  @Bean
  public NewTopic paymentFailedTopic() {
    return TopicBuilder.name(PAYMENT_FAILED)
        .partitions(3)
        .replicas(1)
        .build();
  }

  @Bean
  public NewTopic paymentCancelledTopic() {
    return TopicBuilder.name(PAYMENT_CANCELLED)
        .partitions(3)
        .replicas(1)
        .build();
  }
}
