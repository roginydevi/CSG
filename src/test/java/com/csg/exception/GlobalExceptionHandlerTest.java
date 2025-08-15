package com.csg.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        exceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void testHandleIOException() {
        IOException ex = new IOException("File not found");

        ResponseEntity<Map<String, String>> response = exceptionHandler.handleIOException(ex);

        assertThat(response.getStatusCodeValue()).isEqualTo(400);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().get("error"))
                .isEqualTo("File processing error: File not found");
    }

    @Test
    void testHandleFileProcessingException() {
        FileProcessingException ex = new FileProcessingException("Custom file error");

        ResponseEntity<Map<String, String>> response = exceptionHandler.handleFileProcessingException(ex);

        assertThat(response.getStatusCodeValue()).isEqualTo(400);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().get("error"))
                .isEqualTo("Custom file error");
    }

    @Test
    void testHandleGeneralException() {
        Exception ex = new Exception("Unexpected failure");

        ResponseEntity<Map<String, String>> response = exceptionHandler.handleException(ex);

        assertThat(response.getStatusCodeValue()).isEqualTo(500);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().get("error"))
                .isEqualTo("Unexpected error: Unexpected failure");
    }
}
