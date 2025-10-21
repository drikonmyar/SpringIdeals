package com.ideal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleResourceNotFoundException(ResourceNotFoundException ex){
        Map<String, Object> exception  = new HashMap<>();
        exception.put("timestamp", LocalDateTime.now());
        exception.put("status", HttpStatus.NOT_FOUND.value());
        exception.put("error", HttpStatus.NOT_FOUND);
        exception.put("message", ex.getMessage());
        return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoChangeException.class)
    public ResponseEntity<Map<String, Object>> handleNoChangeException(NoChangeException ex){
        Map<String, Object> exception  = new HashMap<>();
        exception.put("timestamp", LocalDateTime.now());
        exception.put("status", HttpStatus.BAD_REQUEST.value());
        exception.put("error", HttpStatus.BAD_REQUEST);
        exception.put("message", ex.getMessage());
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InsufficientPermissionException.class)
    public ResponseEntity<Map<String, Object>> handleInsufficientPermissionException(InsufficientPermissionException ex){
        Map<String, Object> exception  = new HashMap<>();
        exception.put("timestamp", LocalDateTime.now());
        exception.put("status", HttpStatus.FORBIDDEN.value());
        exception.put("error", HttpStatus.FORBIDDEN);
        exception.put("message", ex.getMessage());
        return new ResponseEntity<>(exception, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex){
        Map<String, Object> exception  = new HashMap<>();
        exception.put("timestamp", LocalDateTime.now());
        exception.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        exception.put("error", HttpStatus.INTERNAL_SERVER_ERROR);
        exception.put("message", ex.getMessage());
        return new ResponseEntity<>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
