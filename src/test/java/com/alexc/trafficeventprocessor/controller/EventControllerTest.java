package com.alexc.trafficeventprocessor.controller;

import com.alexc.trafficeventprocessor.ControllerTestBase;
import com.alexc.trafficeventprocessor.dto.EventDTO;
import com.alexc.trafficeventprocessor.service.EventService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(EventController.class)
class EventControllerTest extends ControllerTestBase {
    @MockBean
    private EventService service;

    @Test
    void testProcessed() throws Exception {
        // Given
        EventDTO processedEvent = new EventDTO();
        processedEvent.setProcessed(true);
        EventDTO unprocessedEvent = new EventDTO();
        unprocessedEvent.setProcessed(false);
        String unprocessedEventJson = objectMapper.writeValueAsString(unprocessedEvent);
        when(service.processEvent(any(EventDTO.class))).thenReturn(processedEvent);

        // When / Then
        mockMvc.perform(post("/api/v1/event").content(unprocessedEventJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.processed", is(true)));
    }
}
