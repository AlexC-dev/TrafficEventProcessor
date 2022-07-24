package com.alexc.trafficeventprocessor.service;

import com.alexc.trafficeventprocessor.dto.FinesSummaryDTO;

import java.util.UUID;

public interface ViolationService {

    /**
     * Creates and stores a violation
     * @param eventID
     * @param fine
     */
    void createViolation(UUID eventID, int fine);

    /**
     * Returns a summary of paid and unpaid fines based on the fines in the repository
     * @return
     */
    FinesSummaryDTO getFinesSummary();
}
