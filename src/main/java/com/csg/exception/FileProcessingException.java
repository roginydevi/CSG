package com.csg.exception;

/**
 * Exception thrown when an error occurs during file processing.
 * Using a custom exception allows centralized handling via
 * {@code @ControllerAdvice} and consistent error responses.
 */
public class FileProcessingException extends RuntimeException {

    /**
     * Constructs a new FileProcessingException with the specified detail message.
     *
     * @param message descriptive message explaining the cause of the exception
     */ 
    public FileProcessingException(String message) {
        super(message);
    }
}
