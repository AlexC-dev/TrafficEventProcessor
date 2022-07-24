package com.alexc.trafficeventprocessor.controller;

import com.alexc.trafficeventprocessor.ControllerTestBase;
import com.alexc.trafficeventprocessor.dto.FinesSummaryDTO;
import com.alexc.trafficeventprocessor.service.ViolationService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ViolationController.class)
class ViolationControllerTest extends ControllerTestBase {
    @MockBean
    private ViolationService service;

    @Test
    void test() throws Exception {
        // Given
        FinesSummaryDTO summary = new FinesSummaryDTO(100, 200);
        when(service.getFinesSummary()).thenReturn(summary);

        // When / Then
        mockMvc.perform(get("/api/v1/violation/fines"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paidFines", is(100)))
                .andExpect(jsonPath("$.unpaidFines", is(200)));
    }
}
