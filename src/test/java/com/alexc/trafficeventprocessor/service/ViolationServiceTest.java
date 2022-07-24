package com.alexc.trafficeventprocessor.service;

import com.alexc.trafficeventprocessor.ViolationTestBase;
import com.alexc.trafficeventprocessor.dto.FinesSummaryDTO;
import com.alexc.trafficeventprocessor.entity.Violation;
import com.alexc.trafficeventprocessor.repository.ViolationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {ViolationServiceImpl.class})
class ViolationServiceTest extends ViolationTestBase {
    @MockBean
    public ViolationRepository repository;
    @Autowired
    public ViolationService service;

    @Test
    void testAddViolationToRepository() {
        // Given
        Violation violation = createViolation(50, false);
        when(repository.addViolation(any(Violation.class))).then(returnsFirstArg());

        // When
        service.createViolation(violation.getEventId(), violation.getFine());

        // Then
        verify(repository, times(1)).addViolation(argThat(arg->verifyViolation(arg, violation.getEventId(), violation.getFine(), violation.isPaid())));
    }

    @Test
    void testGetFinesSummaryNotNull() {
        // When
        FinesSummaryDTO summary = service.getFinesSummary();
        // Then
        assertThat(summary).isNotNull();
    }

    @Test
    void testAddViolationsThenFinesSummaryPaidFines() {
        // Given
        Collection<Violation> violations = new ArrayList<>();
        violations.add(createViolation(50, true));
        violations.add(createViolation(100, false));
        when(repository.addViolation(any(Violation.class))).then(returnsFirstArg());
        when(repository.getAllViolations()).thenReturn(violations);

        // When
        violations.forEach(violation -> service.createViolation(violation.getEventId(), violation.getFine()));
        FinesSummaryDTO summary = service.getFinesSummary();

        // Then
        assertThat(summary.getPaidFines()).isEqualTo(50);
    }

    @Test
    void testFinesSummaryPaidFines() {
        // Given
        Collection<Violation> violations = new ArrayList<>();
        violations.add(createViolation(50, true));
        violations.add(createViolation(100, false));
        when(repository.getAllViolations()).thenReturn(violations);

        // When
        FinesSummaryDTO summary = service.getFinesSummary();

        // Then
        assertThat(summary.getPaidFines()).isEqualTo(50);
    }

    @Test
    void testFinesSummaryUnpaidFines() {
        // Given
        Collection<Violation> violations = new ArrayList<>();
        violations.add(createViolation(50, true));
        violations.add(createViolation(100, false));
        when(repository.getAllViolations()).thenReturn(violations);

        // When
        FinesSummaryDTO summary = service.getFinesSummary();

        // Then
        assertThat(summary.getUnpaidFines()).isEqualTo(100);
    }

    @Test
    void testAddViolationsThenFinesSummaryUnpaidFines() {
        // Given
        Collection<Violation> violations = new ArrayList<>();
        violations.add(createViolation(50, true));
        violations.add(createViolation(100, false));
        when(repository.addViolation(any(Violation.class))).then(returnsFirstArg());
        when(repository.getAllViolations()).thenReturn(violations);

        // When
        violations.forEach(violation -> service.createViolation(violation.getEventId(), violation.getFine()));
        FinesSummaryDTO summary = service.getFinesSummary();

        // Then
        assertThat(summary.getUnpaidFines()).isEqualTo(100);
    }
}
