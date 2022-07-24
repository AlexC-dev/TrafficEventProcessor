package com.alexc.trafficeventprocessor.repository;

import com.alexc.trafficeventprocessor.entity.Event;

public interface EventRepository {
    /**
     * Stores the event
     * @param event
     * @return
     */
    Event addEvent(Event event);
}
