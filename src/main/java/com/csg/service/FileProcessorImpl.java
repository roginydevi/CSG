package com.csg.service;

import com.csg.exception.FileProcessingException;
import com.csg.model.FileProcessorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Stream;

/**
 * Efficient implementation of FileProcessor.
 * Processes an InputStream to:
 * - Count words starting with 'M/m'
 * - Collect words longer than 5 characters
 * Uses parallel streams for faster processing and thread-safe aggregation.
 */
@Slf4j
@Service
public class FileProcessorImpl implements FileProcessor {

    /**
     * Processes the input stream and returns word statistics.
     *
     * @param inputStream Input stream of the text file
     * @return FileProcessorResult containing count of 'M/m' words and list of long words
     * @throws IOException if reading the input stream fails
     */
    @Override
    public FileProcessorResult process(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            log.error("Input stream is null");
            throw new FileProcessingException("Input stream is null");
        }

        log.info("Starting file processing...");

        LongAdder countM = new LongAdder();
        List<String> longerThanFive = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            reader.lines().forEach(line -> {
                Stream.of(line.split("\\W+"))
                        .parallel()
                        .filter(w -> !w.isEmpty())
                        .forEach(w -> {
                            if (w.toLowerCase().startsWith("m")) countM.increment();
                            if (w.length() > 5) synchronized (longerThanFive) {
                                longerThanFive.add(w);
                            }
                        });
            });
        } catch (IOException e) {
            log.error("Error reading input stream", e);
            throw e;
        }

        log.info("File processed successfully: wordsStartingWithM={}, wordsLongerThanFive={}",
                countM.sum(), longerThanFive.size());

        return new FileProcessorResult(countM.sum(), longerThanFive);
    }
}
