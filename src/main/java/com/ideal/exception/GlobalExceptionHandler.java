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

    private static final String TIMESTAMP = "timestamp";
    private static final String STATUS = "status";
    private static final String ERROR = "error";
    private static final String MESSAGE = "message";

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleResourceNotFoundException(ResourceNotFoundException ex){
        Map<String, Object> exception  = new HashMap<>();
        exception.put(TIMESTAMP, LocalDateTime.now());
        exception.put(STATUS, HttpStatus.NOT_FOUND.value());
        exception.put(ERROR, HttpStatus.NOT_FOUND);
        exception.put(MESSAGE, ex.getMessage());
        return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoChangeException.class)
    public ResponseEntity<Map<String, Object>> handleNoChangeException(NoChangeException ex){
        Map<String, Object> exception  = new HashMap<>();
        exception.put(TIMESTAMP, LocalDateTime.now());
        exception.put(STATUS, HttpStatus.BAD_REQUEST.value());
        exception.put(ERROR, HttpStatus.BAD_REQUEST);
        exception.put(MESSAGE, ex.getMessage());
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InsufficientPermissionException.class)
    public ResponseEntity<Map<String, Object>> handleInsufficientPermissionException(InsufficientPermissionException ex){
        Map<String, Object> exception  = new HashMap<>();
        exception.put(TIMESTAMP, LocalDateTime.now());
        exception.put(STATUS, HttpStatus.FORBIDDEN.value());
        exception.put(ERROR, HttpStatus.FORBIDDEN);
        exception.put(MESSAGE, ex.getMessage());
        return new ResponseEntity<>(exception, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UserAlreadyExists.class)
    public ResponseEntity<Map<String, Object>> handleUserAlreadyExistsException(UserAlreadyExists ex){
        Map<String, Object> exception  = new HashMap<>();
        exception.put(TIMESTAMP, LocalDateTime.now());
        exception.put(STATUS, HttpStatus.BAD_REQUEST.value());
        exception.put(ERROR, HttpStatus.BAD_REQUEST);
        exception.put(MESSAGE, ex.getMessage());
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex){
        Map<String, Object> exception  = new HashMap<>();
        exception.put(TIMESTAMP, LocalDateTime.now());
        exception.put(STATUS, HttpStatus.INTERNAL_SERVER_ERROR.value());
        exception.put(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        exception.put(MESSAGE, ex.getMessage());
        return new ResponseEntity<>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
