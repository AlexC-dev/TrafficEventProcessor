package com.alexc.trafficeventprocessor.eventprocessor;

import com.alexc.trafficeventprocessor.EventTestBase;
import com.alexc.trafficeventprocessor.entity.Event;
import com.alexc.trafficeventprocessor.entity.enums.EventType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Java6Assertions.assertThat;

public abstract class EventProcessorTestBase extends EventTestBase {
    @Autowired
    protected EventProcessor processor;

    @ParameterizedTest(name = "testEligible: type={0}, eligible={1}")
    @MethodSource("eligibleArgs")
    public void testEligible(EventType eventType, boolean eligible) {
        // Given
        Event event = createEventEntity(eventType, 50, 87);

        // When / Then
        assertThat(processor.isEligible(event)).isEqualTo(eligible);
    }

    @ParameterizedTest(name = "testEligible: type={0}, speed={1}, limit={2}, violation={3}")
    @MethodSource("violationArgs")
    public void testViolation(EventType eventType, int speed, int limit, boolean violation) {
        // Given
        Event event = createEventEntity(eventType, speed, limit);

        // When / Then
        assertThat(processor.isViolation(event)).isEqualTo(violation);
    }

    @ParameterizedTest(name = "testViolation: type={0}, speed={1}, limit={2}, violation={3}")
    @MethodSource("fineArgs")
    public void testFine(EventType eventType, int speed, int limit, int fine) {
        // Given
        Event event = createEventEntity(eventType, speed, limit);

        // When / Then
        assertThat(processor.getFine(event)).isEqualTo(fine);
    }
}
