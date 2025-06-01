package org.example.kakaotechcampus_assignment2.domain.plan.handlers;

public class InvalidPlanPasswordException extends RuntimeException {
    
    public InvalidPlanPasswordException(String message) {
        super(message);
    }
} 