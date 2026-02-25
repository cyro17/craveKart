package com.cyro.cravekart.config.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.ExponentialBackOffWithMaxRetries;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JacksonJsonDeserializer;
import org.springframework.kafka.support.serializer.JacksonJsonSerializer;

import java.util.HashMap;
import java.util.Map;
@Slf4j
@Configuration
public class KafkaConsumerConfiguration {

  @Bean
  public Map<String, Object> consumerProperties() {
    Map<String, Object> props = new HashMap<>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
    props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JacksonJsonDeserializer.class);
    props.put(ConsumerConfig.GROUP_ID_CONFIG, "cravekart-group");
    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
    props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
    props.put(JacksonJsonDeserializer.TRUSTED_PACKAGES, "com.cyro.cravekart.*");

    props.put(JacksonJsonDeserializer.USE_TYPE_INFO_HEADERS, true);

    return  props;
  }

  @Bean
  public ConsumerFactory<String, Object> consumerFactory() {
    return  new DefaultKafkaConsumerFactory<>(consumerProperties());
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory(
      ConsumerFactory<String, Object> consumerFactory,
      DefaultErrorHandler kafkaErrorHandler
  ) {
    ConcurrentKafkaListenerContainerFactory<String, Object> factory =
        new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory());
    factory.setCommonErrorHandler(kafkaErrorHandler);

    return factory;
  }

  @Bean
  public DefaultErrorHandler kafkaErrorHandler(
      KafkaTemplate<String, Object> kafkaTemplate
  ){

    DeadLetterPublishingRecoverer deadLetterPublishingRecoverer =
        new DeadLetterPublishingRecoverer(kafkaTemplate,
        (record, ex) -> {
          log.error("Message sent to DLT. Topic: {}, Error : {}",
              record.topic(), ex.getMessage());

          return new TopicPartition(record.topic() + ".DLT",
              record.partition());
        });

    ExponentialBackOffWithMaxRetries backoff = new ExponentialBackOffWithMaxRetries(3);
    backoff.setInitialInterval(1_000L);
    backoff.setMultiplier(2);
    backoff.setMaxInterval(10_000L);

    return  new DefaultErrorHandler(deadLetterPublishingRecoverer, backoff);
  }
}
