package com.alexc.trafficeventprocessor.dto;

import com.alexc.trafficeventprocessor.entity.enums.EventType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@EqualsAndHashCode
@ToString
public class EventDTO {
    private UUID id;
    private LocalDateTime eventDate;
    private EventType eventType;
    private String licensePlate;
    private int speed;
    private int limit;
    private String unity;
    private boolean processed;
}
