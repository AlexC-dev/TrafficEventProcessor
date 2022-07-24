package com.alexc.trafficeventprocessor.service;

import com.alexc.trafficeventprocessor.EventTestBase;
import com.alexc.trafficeventprocessor.dto.EventDTO;
import com.alexc.trafficeventprocessor.entity.Event;
import com.alexc.trafficeventprocessor.entity.enums.EventType;
import com.alexc.trafficeventprocessor.eventprocessor.EventProcessor;
import com.alexc.trafficeventprocessor.mapper.EventMapperImpl;
import com.alexc.trafficeventprocessor.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {EventServiceImpl.class, EventMapperImpl.class})
class EventServiceTest extends EventTestBase {
    @Autowired
    public EventService service;
    @MockBean
    public EventRepository repository;
    @MockBean
    public ViolationService violationService;
    @MockBean
    public EventProcessor eventProcessor;

    @BeforeEach
    void prepareMocks() {
        when(repository.addEvent(any(Event.class))).then(returnsFirstArg());
    }

    @Test
    void testProcessEventViolationAdded() {
        // Given
        EventDTO event = createEventDTO(EventType.SPEED, 87, 50);
        prepareMockEventProcessor(true, true, TEST_FINE);

        // When
        service.processEvent(event);

        // Then
        verify(violationService, times(1)).createViolation(event.getId(), TEST_FINE);
    }

    @Test
    void testProcessEventNotViolationNotAdded() {
        // Given
        EventDTO event = createEventDTO(EventType.SPEED, 35, 50);
        prepareMockEventProcessor(true, false, TEST_FINE);

        // When
        service.processEvent(event);

        // Then
        verify(violationService, times(0)).createViolation(any(UUID.class), anyInt());
    }

    @Test
    void testProcessEventAdded() {
        // Given
        EventDTO event = createEventDTO(EventType.SPEED, 35, 50);
        prepareMockEventProcessor(true, false, TEST_FINE);

        // When
        service.processEvent(event);

        // Then
        verify(repository, times(1)).addEvent(any(Event.class));
    }

    @Test
    void testProcessEventResultProcessedTrue() {
        // Given
        EventDTO event = createEventDTO(EventType.SPEED, 87, 50);
        prepareMockEventProcessor(true, false, TEST_FINE);

        // When
        EventDTO processedEvent = service.processEvent(event);

        // Then
        assertThat(processedEvent.isProcessed()).isTrue();
    }

    @Test
    void testProcessEventProcessorNotFound() {
        // Given
        EventDTO event = createEventDTO(EventType.SPEED, 87, 50);
        prepareMockEventProcessor(false, false, TEST_FINE);

        // When
        EventDTO processedEvent = service.processEvent(event);

        // Then
        assertThat(processedEvent.isProcessed()).isFalse();
    }

    private void prepareMockEventProcessor(boolean eligible, boolean violation, int fine) {
        when(eventProcessor.isEligible(any(Event.class))).thenReturn(eligible);
        when(eventProcessor.isViolation(any(Event.class))).thenReturn(violation);
        when(eventProcessor.getFine(any(Event.class))).thenReturn(fine);
    }
}
