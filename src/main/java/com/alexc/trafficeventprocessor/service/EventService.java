package com.alexc.trafficeventprocessor.service;

import com.alexc.trafficeventprocessor.dto.EventDTO;

public interface EventService {

    /**
     * Processes the event: stores it, evaluates if it's a violation, stores the violation if applicable
     * @param event
     * @return
     */
    EventDTO processEvent(EventDTO event);
}
