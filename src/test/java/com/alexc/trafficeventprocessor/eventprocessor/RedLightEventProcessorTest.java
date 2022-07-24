package com.alexc.trafficeventprocessor.eventprocessor;

import com.alexc.trafficeventprocessor.entity.enums.EventType;
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

@SpringBootTest(classes={RedLightEventProcessor.class})
public class RedLightEventProcessorTest extends EventProcessorTestBase {
    private static final EventType ELIGIBLE_EVENT_TYPE = EventType.RED_LIGHT;
    private static final EventType NOT_ELIGIBLE_EVENT_TYPE = EventType.SPEED;

    // eventType, eligible
    private static Stream<Arguments> eligibleArgs() {
        return Stream.of(
                Arguments.of(ELIGIBLE_EVENT_TYPE, true),
                Arguments.of(NOT_ELIGIBLE_EVENT_TYPE, false)
        );
    }

    // eventType, speed, limit, violation
    private static Stream<Arguments> violationArgs() {
        return Stream.of(
                // Speed is above 0: violation
                Arguments.of(ELIGIBLE_EVENT_TYPE, 87, 50, true),
                Arguments.of(ELIGIBLE_EVENT_TYPE, 87, 500, true), // Limit is ignored
                Arguments.of(ELIGIBLE_EVENT_TYPE, 40, -600, true),
                Arguments.of(ELIGIBLE_EVENT_TYPE, 50, 50, true), // Speed = Limit
                Arguments.of(NOT_ELIGIBLE_EVENT_TYPE, 87, 50, true), // eventType is ignored
                // Speed is below or equal to 0: not violation
                Arguments.of(ELIGIBLE_EVENT_TYPE, 0, 50, false),
                Arguments.of(ELIGIBLE_EVENT_TYPE, 0, -60, false),  // Limit is ignored
                Arguments.of(ELIGIBLE_EVENT_TYPE, -20, 50, false),
                Arguments.of(ELIGIBLE_EVENT_TYPE, -600, -300, false), // No validation for negative values
                Arguments.of(NOT_ELIGIBLE_EVENT_TYPE, 0, 50, false) // eventType is ignored
        );
    }

    // eventType, speed, limit, fine
    private static Stream<Arguments> fineArgs() {
        return Stream.of(
                // Fine is 100 regardless of other args
                Arguments.of(ELIGIBLE_EVENT_TYPE, 87, 50, 100),
                Arguments.of(ELIGIBLE_EVENT_TYPE, 0, -2, 100),
                Arguments.of(NOT_ELIGIBLE_EVENT_TYPE, 600, 0, 100)
        );
    }

}
