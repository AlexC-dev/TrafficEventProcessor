package com.alexc.trafficeventprocessor.repository;

import com.alexc.trafficeventprocessor.ViolationTestBase;
import com.alexc.trafficeventprocessor.entity.Violation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {InMemoryViolationRepository.class})
class ViolationRepositoryTest extends ViolationTestBase {
    private static final int TEST_FINE = 50;
    private static final boolean TEST_PAID = false;

    @Autowired
    public ViolationRepository repository;

    @Test
    void testAddViolation() {
        // Given
        Violation violation = createViolation();

        // When
        Violation addedViolation = repository.addViolation(violation);

        // Then
        assertThat(addedViolation).isEqualTo(violation);
    }

    @Test
    void testAddViolationThenGetViolations() {
        // Given
        Violation violation = createViolation();

        // When
        Violation addedViolation = repository.addViolation(violation);
        Collection<Violation> violations = repository.getAllViolations();

        //Then
        assertThat(violations).contains(violation);
    }

    private Violation createViolation() {
        return createViolation(TEST_FINE, TEST_PAID);
    }
}
