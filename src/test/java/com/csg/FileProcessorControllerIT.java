package com.csg;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;

@SpringBootTest
@AutoConfigureMockMvc
class FileProcessorControllerIT {

    @Autowired
    private MockMvc mockMvc;

    private static final String API_PATH = "/csg/process-file";

    @Test
    void testGetArtistInfo() throws IOException {
        RestAssuredMockMvc.mockMvc(mockMvc);

        String fileContent = "Monkey\nMango\nApple\nBanana\nMelon";

        MockMultipartFile file = new MockMultipartFile(
                "file", "test.txt", MediaType.TEXT_PLAIN_VALUE,
                fileContent.getBytes()
        );

        given()
                .multiPart("file", "test.txt", file.getBytes())
                .when()
                .post(API_PATH)
                .then()
                .statusCode(200);
    }

    @Test
    void testMissingFile() {
        RestAssuredMockMvc.mockMvc(mockMvc);

        given()
                .when()
                .post(API_PATH)
                .then()
                .statusCode(500);
    }

    // 3️⃣ Unsupported file type
    @Test
    void testDifferentFileType() throws IOException {
        RestAssuredMockMvc.mockMvc(mockMvc);

        MockMultipartFile file = new MockMultipartFile(
                "file", "data.pdf", "application/pdf",
                "dummy content".getBytes()
        );

        given()
                .multiPart("file", "data.pdf", file.getBytes())
                .when()
                .post(API_PATH)
                .then()
                .statusCode(200);
    }


}
