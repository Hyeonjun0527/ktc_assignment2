package org.example.kakaotechcampus_assignment2.domain.plan.handlers;

import org.example.kakaotechcampus_assignment2.domain.plan.dto.ErrorResponseDto;
import org.example.kakaotechcampus_assignment2.domain.plan.handlers.constant.PlanErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class PlanExceptionHandler {

    @ExceptionHandler(PlanNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handlePlanNotFound(
            PlanNotFoundException ex, HttpServletRequest request) {
        
        ErrorResponseDto errorResponse = new ErrorResponseDto(
                PlanErrorCode.PLAN_NOT_FOUND.getCode(),
                ex.getMessage() != null ? ex.getMessage() : PlanErrorCode.PLAN_NOT_FOUND.getMessage(),
                request.getRequestURI()
        );
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(InvalidPlanPasswordException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidPassword(
            InvalidPlanPasswordException ex, HttpServletRequest request) {
        
        ErrorResponseDto errorResponse = new ErrorResponseDto(
                PlanErrorCode.INVALID_PLAN_PASSWORD.getCode(),
                ex.getMessage() != null ? ex.getMessage() : PlanErrorCode.INVALID_PLAN_PASSWORD.getMessage(),
                request.getRequestURI()
        );
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationException(
            MethodArgumentNotValidException ex, HttpServletRequest request) {
        
        String message = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        if (message == null) {
            message = PlanErrorCode.VALIDATION_ERROR.getMessage();
        }
        
        ErrorResponseDto errorResponse = new ErrorResponseDto(
                PlanErrorCode.VALIDATION_ERROR.getCode(),
                message,
                request.getRequestURI()
        );
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGlobalException(
            Exception ex, HttpServletRequest request) {
        
        ErrorResponseDto errorResponse = new ErrorResponseDto(
                PlanErrorCode.SYSTEM_ERROR.getCode(),
                PlanErrorCode.SYSTEM_ERROR.getMessage(),
                request.getRequestURI()
        );
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
} 