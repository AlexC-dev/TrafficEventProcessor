package com.alexc.trafficeventprocessor.repository;

import com.alexc.trafficeventprocessor.EventTestBase;
import com.alexc.trafficeventprocessor.entity.Event;
import com.alexc.trafficeventprocessor.entity.enums.EventType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {InMemoryEventRepository.class})
class EventRepositoryTest extends EventTestBase {
    private final static int TEST_SPEED = 87;
    private final static int TEST_LIMIT = 50;

    @Autowired
    public EventRepository repository;

    @Test
    void testAddEventResult() {
        // Given
        Event event = createEvent();

        // When
        Event addedEvent = repository.addEvent(event);

        // Then
        assertThat(addedEvent).isEqualTo(event);
    }

    @Test
    void testEventAdded() {
        // Given
        Event event = createEvent();

        // When
        Event addedEvent = repository.addEvent(event);

        // Then
        assertThat(addedEvent).isEqualTo(event);
    }

    private Event createEvent() {
        return createEventEntity(EventType.SPEED, TEST_SPEED, TEST_LIMIT);
    }
}
