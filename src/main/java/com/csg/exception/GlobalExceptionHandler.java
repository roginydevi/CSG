package com.csg.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler to manage application-wide exceptions.
 * <p>
 * This class centralizes exception handling for controllers using {@code @ControllerAdvice}.
 * It converts exceptions into consistent HTTP responses with appropriate status codes
 * and error messages.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles IOExceptions, typically thrown during file processing operations.
     *
     * @param ex the IOException thrown
     * @return ResponseEntity containing error details with HTTP 400 Bad Request
     */
    @ExceptionHandler(IOException.class)
    public ResponseEntity<Map<String, String>> handleIOException(IOException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "File processing error: " + ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles custom FileProcessingExceptions thrown by the application.
     *
     * @param ex the FileProcessingException thrown
     * @return ResponseEntity containing error details with HTTP 400 Bad Request
     */
    @ExceptionHandler(FileProcessingException.class)
    public ResponseEntity<Map<String, String>> handleFileProcessingException(FileProcessingException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles all other uncaught exceptions as internal server errors.
     *
     * @param ex the Exception thrown
     * @return ResponseEntity containing error details with HTTP 500 Internal Server Error
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleException(Exception ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Unexpected error: " + ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
