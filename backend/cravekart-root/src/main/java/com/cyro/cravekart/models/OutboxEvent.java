package com.cyro.cravekart.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.*;

@Entity
@Table(name = "outbox_events")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class OutboxEvent {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "event_type", nullable = false)
  private String eventType;

  private String eventType;

  @Column(name = "topic", nullable = false)
  private String topic;

  @Column(name = "payload", nullable = false, columnDefinition = "TEXT")
  private String payload;

  @Column(name = "status", nullable = false)
  @Enumerated(EnumType.STRING)
  private OutboxStatus status = OutboxStatus.PENDING;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt = LocalDateTime.now();

  @Column(name = "processed_at")
  private LocalDateTime processedAt;

  @Column(name = "retry_count", nullable = false)
  private int retryCount = 0;

  public enum OutboxStatus {
    PENDING,
    PUBLISHED,
    FAILED
  };
}
