package com.demo.backend_accenture.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
        .message(ex.getMessage())
        .statusCode(HttpStatus.NOT_FOUND.value())
        .timestamp(LocalDateTime.now())
        .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
