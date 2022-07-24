package com.alexc.trafficeventprocessor.service;

import com.alexc.trafficeventprocessor.dto.FinesSummaryDTO;
import com.alexc.trafficeventprocessor.entity.Violation;
import com.alexc.trafficeventprocessor.repository.ViolationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ViolationServiceImpl implements ViolationService {
    private final ViolationRepository repository;

    @Override
    public void createViolation(UUID eventID, int fine) {
        Violation violation = new Violation();
        violation.setId(UUID.randomUUID());
        violation.setEventId(eventID);
        violation.setFine(fine);
        violation.setPaid(false);
        repository.addViolation(violation);
    }

    @Override
    public FinesSummaryDTO getFinesSummary() {
        log.debug("Calculating fines summary");
        Collection<Violation> violations = repository.getAllViolations();
        FinesSummaryDTO finesSummary = new FinesSummaryDTO();
        for (Violation violation : violations) {
            if (violation.isPaid()) {
                finesSummary.setPaidFines(finesSummary.getPaidFines() + violation.getFine());
            } else {
                finesSummary.setUnpaidFines(finesSummary.getUnpaidFines() + violation.getFine());
            }
        }
        log.debug(String.format("Fines summary: %s", finesSummary));
        return finesSummary;
    }

}
