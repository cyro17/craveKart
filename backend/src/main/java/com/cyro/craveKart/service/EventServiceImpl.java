package com.cyro.cravekart.service;

import com.cyro.cravekart.models.Event;

import java.util.List;

public class EventServiceImpl  implements EventService {

  @Override
  public Event createEvent(Event event, Long restaurantId) {
    return null;
  }

  @Override
  public List<Event> findAllEvents() {
    return List.of();
  }

  @Override
  public List<Event> findRestaurantsEvent(Long id) {
    return List.of();
  }

  @Override
  public void deleteEvent(Long id) {

  }

  @Override
  public Event findById(Long id) {
    return null;
  }
}
