package com.alexc.trafficeventprocessor.eventprocessor;

import com.alexc.trafficeventprocessor.entity.Event;
import com.alexc.trafficeventprocessor.entity.enums.EventType;
import org.springframework.stereotype.Component;

@Component
public class RedLightEventProcessor implements EventProcessor {
    @Override
    public boolean isEligible(Event event) {
        return event.getEventType() == EventType.RED_LIGHT;
    }

    @Override
    public boolean isViolation(Event event) {
        return event.getSpeed() > 0;
    }

    @Override
    public int getFine(Event event) {
        return 100;
    }
}
