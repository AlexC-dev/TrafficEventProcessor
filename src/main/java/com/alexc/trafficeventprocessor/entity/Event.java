package com.alexc.trafficeventprocessor.entity;

import com.alexc.trafficeventprocessor.entity.enums.EventType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@EqualsAndHashCode
public class Event {
    private UUID id;
    private LocalDateTime eventDate;
    private EventType eventType;
    private String licensePlate;
    private int speed;
    private int limit;
    private String unity;
    private boolean processed;
}
