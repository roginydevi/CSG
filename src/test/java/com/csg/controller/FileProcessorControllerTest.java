package com.csg.controller;

import com.csg.model.FileProcessorResult;
import com.csg.service.FileProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class FileProcessorControllerTest {

    @Mock
    private FileProcessor processor;

    @InjectMocks
    private FileProcessorController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testProcessFile_Success() throws IOException {
        String content = "Melbourne Melbourne map magic";
        MockMultipartFile file = new MockMultipartFile("file", "test.txt",
                "text/plain", content.getBytes());

        FileProcessorResult mockResult = new FileProcessorResult(4, Arrays.asList("Melbourne", "Melbourne"));
        when(processor.process(any())).thenReturn(mockResult);

        FileProcessorResult result = controller.processFile(file);

        assertEquals(4, result.getWordsStartingWithM());
        assertEquals(2, result.getWordsLongerThanFive().size());

        verify(processor, times(1)).process(any());
    }

    @Test
    void testProcessFile_IOException() throws IOException {
        MockMultipartFile file = new MockMultipartFile("file", "test.txt",
                "text/plain", "data".getBytes());

        when(processor.process(any())).thenThrow(new IOException("Test IO error"));

        try {
            controller.processFile(file);
        } catch (IOException e) {
            assertEquals("Test IO error", e.getMessage());
        }

        verify(processor, times(1)).process(any());
    }
}
