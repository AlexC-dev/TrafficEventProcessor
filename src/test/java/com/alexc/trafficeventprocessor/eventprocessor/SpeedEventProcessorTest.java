package com.alexc.trafficeventprocessor.eventprocessor;

import com.alexc.trafficeventprocessor.entity.enums.EventType;
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

@SpringBootTest(classes={SpeedEventProcessor.class})
public class SpeedEventProcessorTest extends EventProcessorTestBase {
    private static final EventType ELIGIBLE_EVENT_TYPE = EventType.SPEED;
    private static final EventType NOT_ELIGIBLE_EVENT_TYPE = EventType.RED_LIGHT;

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
                // Speed is above limit: violation
                Arguments.of(ELIGIBLE_EVENT_TYPE, 87, 50, true),
                Arguments.of(ELIGIBLE_EVENT_TYPE, -400, -500, true), // No validation for negative values
                Arguments.of(NOT_ELIGIBLE_EVENT_TYPE, 87, 50, true), // eventType is ignored
                // Speed is below or equal to limit: not violation
                Arguments.of(ELIGIBLE_EVENT_TYPE, 35, 50, false),
                Arguments.of(ELIGIBLE_EVENT_TYPE, -20, 50, false), // Speed is negative
                Arguments.of(ELIGIBLE_EVENT_TYPE, 50, 50, false), // Speed = Limit
                Arguments.of(ELIGIBLE_EVENT_TYPE, -600, -300, false), // No validation for negative values
                Arguments.of(NOT_ELIGIBLE_EVENT_TYPE, 20, 50, false) // eventType is ignored
        );
    }

    // eventType, speed, limit, fine
    private static Stream<Arguments> fineArgs() {
        return Stream.of(
                // Fine is 50 regardless of other args
                Arguments.of(ELIGIBLE_EVENT_TYPE, 87, 50, 50),
                Arguments.of(ELIGIBLE_EVENT_TYPE, 0, -2, 50),
                Arguments.of(NOT_ELIGIBLE_EVENT_TYPE, 600, 0, 50)
        );
    }

}
