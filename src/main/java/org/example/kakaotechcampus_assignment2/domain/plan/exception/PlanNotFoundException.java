package org.example.kakaotechcampus_assignment2.domain.plan.exception;

public class PlanNotFoundException extends RuntimeException {
    public PlanNotFoundException(String message) {
        super(message);
    }
} 