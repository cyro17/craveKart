package com.cyro.cravekart.repository;

import com.cyro.cravekart.models.ProcessedEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessedEventRepository extends JpaRepository<ProcessedEvent, Long> {
  boolean existsByEventId(String eventId);
}
