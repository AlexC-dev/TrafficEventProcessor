package com.alexc.trafficeventprocessor.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode
public class Violation {
    private UUID id;
    private UUID eventId;
    private int fine;
    private boolean paid;
}
