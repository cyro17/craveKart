package com.cyro.cravekart.repository;

import com.cyro.cravekart.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
