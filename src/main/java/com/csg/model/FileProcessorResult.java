package com.csg.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Data model representing the result of word processing.
 * Contains the count of words starting with 'M/m' and
 * a list of words longer than 5 characters.
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FileProcessorResult {

    /**
     * Count of words starting with 'M' or 'm'
     */
    private long wordsStartingWithM;

    /**
     * List of words longer than 5 characters
     */
    private List<String> wordsLongerThanFive;
}
