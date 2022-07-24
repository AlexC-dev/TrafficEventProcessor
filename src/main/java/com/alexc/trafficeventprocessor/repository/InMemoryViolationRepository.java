package com.alexc.trafficeventprocessor.repository;

import com.alexc.trafficeventprocessor.entity.Violation;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryViolationRepository implements ViolationRepository {

    private final Map<UUID, Violation> violations = new ConcurrentHashMap<>();

    @Override
    public Violation addViolation(Violation violation) {
        violations.put(violation.getId(), violation);
        return violation;
    }

    @Override
    public Collection<Violation> getAllViolations() {
        return violations.values();
    }
}
