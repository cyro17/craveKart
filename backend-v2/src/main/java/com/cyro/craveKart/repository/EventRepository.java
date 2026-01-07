package com.cyro.craveKart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cyro.craveKart.model.Events;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Events, Long>{

	public List<Events> findEventsByRestaurantId(Long id);
}
