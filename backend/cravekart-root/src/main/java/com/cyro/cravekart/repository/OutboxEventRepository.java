package com.cyro.cravekart.repository;

import com.cyro.cravekart.models.OutboxEvent;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OutboxEventRepository extends JpaRepository<OutboxEvent, Long> {

  @Query(
"""
    SELECT e from OutboxEvent e where e.status = 'PENDING' order by e.createdAt asc limit 50
""")
  List<OutboxEvent> findPendingEvents();

  List<OutboxEvent> findByStatusAndRetryCountLessThan(
      OutboxEvent.OutboxStatus status, int maxRetries);
}
