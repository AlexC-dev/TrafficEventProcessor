package com.alexc.trafficeventprocessor;

import com.alexc.trafficeventprocessor.dto.EventDTO;
import com.alexc.trafficeventprocessor.entity.Event;
import com.alexc.trafficeventprocessor.entity.enums.EventType;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class EventTestBase {
    protected static final LocalDateTime TEST_EVENT_DATE = LocalDateTime.parse("2022-02-09T00:25:20.529");
    protected static final String TEST_LICENSE_PLATE = "ABC-123";
    protected static final int TEST_FINE = 50;

    protected EventDTO createEventDTO(EventType type, int speed, int limit) {
        EventDTO event = new EventDTO();
        event.setId(UUID.randomUUID());
        event.setEventDate(TEST_EVENT_DATE);
        event.setLicensePlate(TEST_LICENSE_PLATE);
        event.setEventType(type);
        event.setSpeed(speed);
        event.setLimit(limit);
        event.setUnity("km/h");
        event.setProcessed(false);
        return event;
    }

    protected Event createEventEntity(EventType type, int speed, int limit) {
        Event event = new Event();
        event.setId(UUID.randomUUID());
        event.setEventDate(TEST_EVENT_DATE);
        event.setLicensePlate(TEST_LICENSE_PLATE);
        event.setEventType(type);
        event.setSpeed(speed);
        event.setLimit(limit);
        event.setUnity("km/h");
        event.setProcessed(false);
        return event;
    }
}
