package org.example.kakaotechcampus_assignment2.domain.plan.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PlanExceptionHandler {

    @ExceptionHandler(PlanNotFoundException.class)
    public ResponseEntity<String> handlePlanNotFound(PlanNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidPlanPasswordException.class)
    public ResponseEntity<String> handleInvalidPassword(InvalidPlanPasswordException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
} 