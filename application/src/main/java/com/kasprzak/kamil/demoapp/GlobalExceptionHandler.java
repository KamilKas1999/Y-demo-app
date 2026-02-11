package com.kasprzak.kamil.demoapp;

import com.kasprzak.kamil.demoapp.exception.ErrorResponse;
import com.kasprzak.kamil.demoapp.user.exceptions.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handle(RuntimeException ex) {
        ErrorResponse response = new ErrorResponse(500, ex.getMessage());
        return ResponseEntity.status(500).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handle(Exception ex) {
        ErrorResponse response = new ErrorResponse(500, ex.getMessage());
        return ResponseEntity.status(500).body(response);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handle(UserNotFoundException ex) {
        ErrorResponse response = new ErrorResponse(404, ex.getMessage());
        return ResponseEntity.status(404)
                .body(response);
    }
}
