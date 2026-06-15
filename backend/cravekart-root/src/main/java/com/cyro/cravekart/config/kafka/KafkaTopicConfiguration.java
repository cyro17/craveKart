package com.cyro.cravekart.config.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@ConditionalOnProperty(name = "app.kafka.enabled", havingValue = "true")
public class KafkaTopicConfiguration {
  public static final String ORDER_CREATED = "order-created";
  public static final String PAYMENT_INITIATED = "payment-initiated";
  public static final String PAYMENT_SUCCESS = "payment-success";
  public static final String PAYMENT_FAILED = "payment-failed";
  public static final String PAYMENT_CANCELLED = "payment-cancelled";
  public static final String NOTIF_SIGNUP = "notif-signup";
  public static final String NOTIF_ORDER_PAID = "notif.order-paid";

  /**
   * Produced by OutboxScheduler keyed by restaurantId. All orders for the same restaurant land on
   * the same partition → ordered delivery.
   */
  public static final String ORDER_CONFIRMED = "order-confirmed";

  @Bean
  public NewTopic orderCreatedTopic() {
    return topic(ORDER_CREATED, 3);
  }

  @Bean
  public NewTopic paymentInitiatedTopic() {
    return topic(PAYMENT_INITIATED, 3);
  }

  @Bean
  public NewTopic paymentSuccessTopic() {
    return topic(PAYMENT_SUCCESS, 3);
  }

  @Bean
  public NewTopic paymentFailedTopic() {
    return topic(PAYMENT_FAILED, 3);
  }

  @Bean
  public NewTopic paymentCancelledTopic() {
    return topic(PAYMENT_CANCELLED, 3);
  }

  @Bean
  public NewTopic notifSignupTopic() {
    return topic(NOTIF_SIGNUP, 3);
  }

  @Bean
  public NewTopic notifOrderPaidTopic() {
    return topic(NOTIF_ORDER_PAID, 1);
  }

  @Bean
  public NewTopic orderConfirmedTopic() {
    return topic(ORDER_CONFIRMED, 3);
  }

  private NewTopic topic(String name, int partitions) {
    return TopicBuilder.name(name).partitions(partitions).replicas(1).build();
  }
}
