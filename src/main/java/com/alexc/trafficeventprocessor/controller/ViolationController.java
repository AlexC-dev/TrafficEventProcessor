package com.alexc.trafficeventprocessor.controller;

import com.alexc.trafficeventprocessor.dto.FinesSummaryDTO;
import com.alexc.trafficeventprocessor.service.ViolationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/violation")
@RequiredArgsConstructor
public class ViolationController {
    private final ViolationService violationService;

    /**
     * Returns a summary of fines stored in the application
     * @return
     */
    @GetMapping("fines")
    public FinesSummaryDTO getFinesSummary() {
        return violationService.getFinesSummary();
    }
}
