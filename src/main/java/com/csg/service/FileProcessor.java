package com.csg.service;

import com.csg.model.FileProcessorResult;

import java.io.IOException;
import java.io.InputStream;

/**
 * Interface for processing text input streams to generate word statistics.
 */

public interface FileProcessor {

    /**
     * Processes the given InputStream and returns word statistics.
     *
     * @param inputStream the input stream of the text file
     * @return FileProcessorResult containing counts and lists of words as per business rules
     * @throws IOException if reading from the input stream fails
     */

    FileProcessorResult process(InputStream inputStream) throws IOException;
}
