package com.alexc.trafficeventprocessor.controller;

import com.alexc.trafficeventprocessor.dto.EventDTO;
import com.alexc.trafficeventprocessor.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/event")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    /**
     * Processes the event: stores it, evaluates if it's a violation, stores the violation if applicable
     * @param event
     * @return
     */
    @PostMapping
    public EventDTO postEvent(@RequestBody EventDTO event) {
        return eventService.processEvent(event);
    }
}
