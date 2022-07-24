package com.alexc.trafficeventprocessor.it;

import com.alexc.trafficeventprocessor.ControllerTestBase;
import com.alexc.trafficeventprocessor.dto.EventDTO;
import com.alexc.trafficeventprocessor.entity.enums.EventType;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ProcessEventIT extends ControllerTestBase {
    private static final LocalDateTime TEST_EVENT_DATE = LocalDateTime.parse("2022-02-09T00:25:20.529");
    private static final String TEST_LICENSE_PLATE = "ABC-123";

    @Test
    void testProcessedViolationSpeed() throws Exception {
        postEvent(EventType.SPEED, 87, 50);
        verifyFines(0, 50);
    }

    @Test
    void testProcessedNoViolationSpeed() throws Exception {
        postEvent(EventType.SPEED, 35, 50);
        verifyFines(0, 0);
    }

    @Test
    void testProcessedViolationRedLight() throws Exception {
        postEvent(EventType.RED_LIGHT, 87, 50);
        verifyFines(0, 100);
    }

    @Test
    void testProcessedNoViolationRedLight() throws Exception {
        postEvent(EventType.RED_LIGHT, 0, 50);
        verifyFines(0, 0);
    }

    @Test
    void testProcessedViolationMultipleSpeed() throws Exception {
        postEvent(EventType.SPEED, 87, 60);
        postEvent(EventType.SPEED, 30, 50);
        postEvent(EventType.SPEED, 50, 50);
        postEvent(EventType.SPEED, 200, 120);
        verifyFines(0, 100);
    }

    @Test
    void testProcessedViolationMultipleMixed() throws Exception {
        postEvent(EventType.SPEED, 87, 60);
        postEvent(EventType.SPEED, 30, 50);
        postEvent(EventType.RED_LIGHT, 0, 40);
        postEvent(EventType.SPEED, 50, 50);
        postEvent(EventType.RED_LIGHT, 50, 50);
        postEvent(EventType.SPEED, 200, 120);
        verifyFines(0, 200);
    }

    private void verifyFines(int paid, int unpaid) throws Exception {
        mockMvc.perform(get("/api/v1/violation/fines"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paidFines", is(paid)))
                .andExpect(jsonPath("$.unpaidFines", is(unpaid)));
    }

    private void postEvent(EventType type, int speed, int limit) throws Exception {
        String eventJson = createEventJson(type, speed, limit);
        mockMvc.perform(post("/api/v1/event").content(eventJson).contentType(MediaType.APPLICATION_JSON));
    }

    private String createEventJson(EventType type, int speed, int limit) throws JsonProcessingException {
        EventDTO event = new EventDTO();
        event.setId(UUID.randomUUID());
        event.setEventDate(TEST_EVENT_DATE);
        event.setLicensePlate(TEST_LICENSE_PLATE);
        event.setEventType(type);
        event.setSpeed(speed);
        event.setLimit(limit);
        event.setUnity("km/h");
        event.setProcessed(false);
        return objectMapper.writeValueAsString(event);
    }
}
