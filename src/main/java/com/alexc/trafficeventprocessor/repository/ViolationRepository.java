package com.alexc.trafficeventprocessor.repository;

import com.alexc.trafficeventprocessor.entity.Violation;

import java.util.Collection;

public interface ViolationRepository {

    /**
     * Stores the violation
     * @param violation
     * @return
     */
    Violation addViolation(Violation violation);

    /**
     * Returns all stored violations
     * @return
     */
    Collection<Violation> getAllViolations();
}
