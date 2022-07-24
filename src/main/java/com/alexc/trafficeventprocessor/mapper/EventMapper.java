package com.alexc.trafficeventprocessor.mapper;

import com.alexc.trafficeventprocessor.dto.EventDTO;
import com.alexc.trafficeventprocessor.entity.Event;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventMapper {
    EventDTO eventToEventDTO(Event event);
    Event eventDTOToEvent(EventDTO event);
}
