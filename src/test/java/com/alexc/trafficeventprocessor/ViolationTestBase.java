package com.alexc.trafficeventprocessor;

import com.alexc.trafficeventprocessor.entity.Violation;

import java.util.UUID;

public abstract class ViolationTestBase {
    protected static final UUID TEST_VIOLATION_EVENT_ID = UUID.fromString("d9bb7458-d5d9-4de7-87f7-7f39edd51d18");

    protected boolean verifyViolation(Violation violation, UUID eventId, int fine, boolean paid) {
        return eventId.equals(violation.getEventId())
                && violation.getFine() == fine
                && violation.isPaid() == paid;
    }

    protected Violation createViolation(int fine, boolean paid) {
        Violation violation = new Violation();
        violation.setId(UUID.randomUUID());
        violation.setEventId(TEST_VIOLATION_EVENT_ID);
        violation.setFine(fine);
        violation.setPaid(paid);
        return violation;
    }
}
