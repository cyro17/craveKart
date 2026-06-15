package com.cyro.cravekart.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
    name = "processed_events",
    indexes =
        @Index(name = "idx_processed_events_event_id", columnList = "event_id", unique = true))
public class ProcessedEvent {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "event_id", nullable = false, unique = true, length = 200)
  private String eventId;

  @Column(name = "consumer", nullable = false, unique = true, length = 100)
  private String consumer;

  @Column(name = "processedAt", nullable = false)
  @Builder.Default
  private LocalDateTime processedAt = LocalDateTime.now();
}
