## Requirements

To create an indexing process for a search system. This application processes a file and does the following:
- Counts and returns the NUMBER of words (i.e. Strings) that start with "M" or "m"
- Returns all the words longer than 5 characters

## Tech Stack
* Java 17
* Spring Boot 3.5.0
* Lombok
* JUnit 5 and mockito
* Maven
* JaCoCo Code Coverage
* Logging using SLF4J
* Spring Exception handling 
* Spring Profiles for specifying logging
* Rest Assured Integration Testcase

## Features

* Consistent JSON response format for both errors and successes
* In-memory processing for speed and scalability
* MultipartFile upload for streaming for large files.
* 83%+ Model Coverage and 93%+ Service Coverage
* Detailed Exception Handling - Handles all exceptions with clean and clear responses
* Structures Error responses -  All exceptions returns consistent JSON error structure
* Code Coverage - Achieved 91% code coverage(HTML report Generated)

## Performance Optimisation 

To ensure low latency and high throughput in file processing:

* In-memory streaming used instead of writing large files to disk.
* Buffered I/O applied to reduce system calls and improve read speed.
* Lazy parsing for files to process records on the fly without full file load.
* Parallel processing option available for large datasets.
* Thread-safe design with synchronisation for shared resources
* Use of synchronized list, LongAdder and parallel stream processing for faster performance.

### API Endpoint

POST : http://localhost:8080/csg/process-file

## Sample Request

Content-Type: multipart/form-data
file: index.txt

## Expected Response
`
{{
"wordsStartingWithM": 8,
"wordsLongerThanFive": [
"mountain",
"microprocessor",
"Monkey",
]
}`

### Build and Run
./mvn spring-boot:run

### Tests
./mvn clean test 

### Coverage Report

./mvn clean test jacoco:report

### Package 

mvn clean package


