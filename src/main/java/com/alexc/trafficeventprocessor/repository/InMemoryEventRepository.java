package com.alexc.trafficeventprocessor.repository;

import com.alexc.trafficeventprocessor.entity.Event;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryEventRepository implements EventRepository {
    private final Map<UUID, Event> events = new ConcurrentHashMap<>();

    @Override
    public Event addEvent(Event event) {
        events.put(event.getId(), event);
        return event;
    }
}
