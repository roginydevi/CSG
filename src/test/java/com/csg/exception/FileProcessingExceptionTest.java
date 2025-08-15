package com.csg.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FileProcessingExceptionTest {

    @Test
    void testExceptionMessage() {
        FileProcessingException ex = new FileProcessingException("File error");
        assertEquals("File error", ex.getMessage());
    }
}

