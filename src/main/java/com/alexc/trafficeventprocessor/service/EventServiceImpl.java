package com.alexc.trafficeventprocessor.service;

import com.alexc.trafficeventprocessor.dto.EventDTO;
import com.alexc.trafficeventprocessor.entity.Event;
import com.alexc.trafficeventprocessor.eventprocessor.EventProcessor;
import com.alexc.trafficeventprocessor.mapper.EventMapper;
import com.alexc.trafficeventprocessor.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventServiceImpl implements EventService {
    private final EventMapper mapper;
    private final EventRepository repository;
    private final ViolationService violationService;

    private final List<EventProcessor> processors;

    @Override
    public EventDTO processEvent(EventDTO event) {
        log.debug(String.format("Processing event: %s", event));
        Event eventEntity = mapper.eventDTOToEvent(event);
        Optional<EventProcessor> processor = processors.stream()
                .filter(eventProcessor -> eventProcessor.isEligible(eventEntity))
                .findFirst();
        boolean hasProcessor = processor.isPresent();

        if (hasProcessor && processor.get().isViolation(eventEntity)) {
            log.debug("Event is violation");
            violationService.createViolation(eventEntity.getId(), processor.get().getFine(eventEntity));
        }

        eventEntity.setProcessed(hasProcessor);

        Event addedEvent = repository.addEvent(eventEntity);
        if (!hasProcessor) {
            log.error(String.format("Could not find processor for event type: %s", event.getEventType()));
        }
        return mapper.eventToEventDTO(addedEvent);
    }

}
