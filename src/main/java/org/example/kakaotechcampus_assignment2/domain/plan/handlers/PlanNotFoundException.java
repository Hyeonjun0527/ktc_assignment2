package org.example.kakaotechcampus_assignment2.domain.plan.handlers;

public class PlanNotFoundException extends RuntimeException {
    
    public PlanNotFoundException(String message) {
        super(message);
    }
} 