package com.csg.controller;

import com.csg.model.FileProcessorResult;
import com.csg.service.FileProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@Slf4j
@RequestMapping("/csg")
public class FileProcessorController {

    @Autowired
    private FileProcessor processor;

    /**
     * POST endpoint to process a text file.
     * Accepts a file uploaded as multipart/form-data.
     *
     * @param file The uploaded text file
     * @return FileProcessorResult containing count and list of words
     * @throws IOException if reading the file fails
     */

    @PostMapping(value = "/process-file")
    public FileProcessorResult processFile(@RequestParam("file") MultipartFile file) throws IOException {
        try {
            log.info("Processing file: {}", file.getOriginalFilename());
            return processor.process(file.getInputStream());
        } catch (IOException e) {
            log.error("Error processing file '{}'", file.getOriginalFilename(), e);
            throw e;
        }
    }
}
