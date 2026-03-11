package com.cyro.cravekart.service;

import com.cyro.cravekart.models.Event;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EventService {

  public Event createEvent(Event event, Long restaurantId );

  public List<Event> findAllEvents();

  public List<Event> findRestaurantsEvent(Long id);

  public void deleteEvent(Long id);

  public Event findById(Long id);


}
