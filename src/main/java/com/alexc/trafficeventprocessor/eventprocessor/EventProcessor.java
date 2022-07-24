package com.alexc.trafficeventprocessor.eventprocessor;

import com.alexc.trafficeventprocessor.entity.Event;

public interface EventProcessor {

    /**
     * Returns true if the event is eligible to be processed by this processor.
     * @param event
     * @return
     */
    boolean isEligible(Event event);

    /**
     * Returns true if the event is a violation.
     * Note: Called only if isEligible is true
     * @param event
     * @return
     */
    boolean isViolation(Event event);

    /**
     * Returns the fine for the specified event. <br>
     * Note: Called only if isEligible and isViolation are true
     * @param event
     * @return
     */
    int getFine(Event event);
}
