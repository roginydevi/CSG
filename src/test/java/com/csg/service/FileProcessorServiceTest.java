package com.csg.service;

import com.csg.exception.FileProcessingException;
import com.csg.model.FileProcessorResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileProcessorImplTest {

    private FileProcessorImpl fileProcessor;

    @BeforeEach
    void setUp() {
        fileProcessor = new FileProcessorImpl();
    }

    // -----------------------------
    // Normal Case: standard input
    // -----------------------------
    @Test
    void testProcess_normalInput() throws IOException {
        String text = "Monkey mango melon\nMangoes are marvelous\ncat dog";
        InputStream input = new ByteArrayInputStream(text.getBytes());

        FileProcessorResult result = fileProcessor.process(input);

        assertNotNull(result);
        // Words starting with M/m: Monkey, mango, melon, Mangoes, marvelous
        assertEquals(5, result.getWordsStartingWithM());

        List<String> longWords = result.getWordsLongerThanFive();
        assertTrue(longWords.contains("Monkey"));
        assertTrue(longWords.contains("Mangoes"));
        assertTrue(longWords.contains("marvelous"));
    }

    // -----------------------------
    // Edge Case: empty input
    // -----------------------------
    @Test
    void testProcess_emptyInput() throws IOException {
        InputStream input = new ByteArrayInputStream("".getBytes());

        FileProcessorResult result = fileProcessor.process(input);

        assertEquals(0, result.getWordsStartingWithM());
        assertTrue(result.getWordsLongerThanFive().isEmpty());
    }

    // -----------------------------
    // Edge Case: input with only short words
    // -----------------------------
    @Test
    void testProcess_onlyShortWords() throws IOException {
        String text = "cat dog sun map men mat";
        InputStream input = new ByteArrayInputStream(text.getBytes());

        FileProcessorResult result = fileProcessor.process(input);

        // Words starting with M/m: map, men, mat
        assertEquals(3, result.getWordsStartingWithM());
        // No word longer than 5 characters
        assertTrue(result.getWordsLongerThanFive().isEmpty());
    }

    // -----------------------------
    // Exception Case: null input
    // -----------------------------
    @Test
    void testProcess_nullInput() {
        FileProcessingException exception = assertThrows(FileProcessingException.class, () -> {
            fileProcessor.process(null);
        });

        assertEquals("Input stream is null", exception.getMessage());
    }

    // -----------------------------
    // Large Input: test parallel stream safety
    // -----------------------------
    @Test
    void testProcess_largeInput_parallelSafety() throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            sb.append("Monkey mango melon melon Monkey\n");
        }
        InputStream input = new ByteArrayInputStream(sb.toString().getBytes());

        FileProcessorResult result = fileProcessor.process(input);

        // 5 words starting with M/m per line * 1000 lines
        assertEquals(5000, result.getWordsStartingWithM());
        assertTrue(result.getWordsLongerThanFive().size() > 0);
    }

    // -----------------------------
    // Input with special characters
    // -----------------------------
    @Test
    void testProcess_withSpecialCharacters() throws IOException {
        String text = "Monkey, mango! melon? Mangoes; marvelous: cat";
        InputStream input = new ByteArrayInputStream(text.getBytes());

        FileProcessorResult result = fileProcessor.process(input);

        assertEquals(5, result.getWordsStartingWithM());
        List<String> longWords = result.getWordsLongerThanFive();
        assertTrue(longWords.contains("Monkey"));
        assertTrue(longWords.contains("Mangoes"));
        assertTrue(longWords.contains("marvelous"));
    }

    // -----------------------------
    // Input with mixed case
    // -----------------------------
    @Test
    void testProcess_mixedCaseWords() throws IOException {
        String text = "monkey Mango MELON";
        InputStream input = new ByteArrayInputStream(text.getBytes());

        FileProcessorResult result = fileProcessor.process(input);

        assertEquals(3, result.getWordsStartingWithM());
    }



    // -----------------------------
    // Input with null or whitespace words
    // -----------------------------
    @Test
    void testProcess_withWhitespaceWords() throws IOException {
        String text = "   Monkey   mango  \n  ";
        InputStream input = new ByteArrayInputStream(text.getBytes());

        FileProcessorResult result = fileProcessor.process(input);

        assertEquals(2, result.getWordsStartingWithM());
        List<String> longWords = result.getWordsLongerThanFive();
        assertTrue(longWords.contains("Monkey"));
    }
}
