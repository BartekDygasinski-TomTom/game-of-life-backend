package pl.bdygasinski.gameoflife.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import pl.bdygasinski.gameoflife.rest.dto.GameStateDto;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class GameStateControllerIT {

    private static final String GAMESTATES_ENDPOINT_URI = ApiVersion.V1 + "/gamestates";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RequestMappingHandlerMapping handlerMapping;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @DisplayName("Should have correct base path")
    @Test
    void shouldHaveCorrectBasePath() {
        String fullPath = ApiVersion.V1 + "/boards/next";

        RestAssuredMockMvc
                .given()
                .contentType("application/json")
                .body("{}")
                .when()
                .post(fullPath)
                .then()
                .statusCode(not(404));
    }

    @DisplayName("nextStep")
    @Nested
    class NextStepTest {

        @DisplayName("Should create new board from matrix")
        @ParameterizedTest(name = "Case {index}")
        @MethodSource("pl.bdygasinski.gameoflife.rest.TestUtils#boardValidCases")
        void shouldCreateBoardFromMatrix(String requestBody, String expectedBody) throws IOException {
            var actualResponse = RestAssuredMockMvc
                            .given()
                                .contentType(ContentType.JSON)
                                .body(requestBody)
                            .when()
                                .post(GAMESTATES_ENDPOINT_URI + "/next")
                            .then()
                                .statusCode(200)
                                .extract()
                                .asString();

            var expected = objectMapper.readValue(expectedBody, GameStateDto.class);
            var actual = objectMapper.readValue(actualResponse, GameStateDto.class);

            assertThat(actual)
                    .isEqualTo(expected);
        }

        @DisplayName("Should return 400 when request body is not valid")
        @ParameterizedTest(name = "Case {index}")
        @MethodSource("pl.bdygasinski.gameoflife.rest.TestUtils#boardInvalidCases")
        void shouldReturnBadRequestForNotValidInput(String requestBody) throws IOException {
            RestAssuredMockMvc
                    .given()
                        .contentType(ContentType.JSON)
                        .body(requestBody)
                    .when()
                        .post(GAMESTATES_ENDPOINT_URI + "/next")
                    .then()
                        .statusCode(400)
                        .contentType(ContentType.JSON)
                        .body("timestamp", notNullValue())
                        .body("statusCode", equalTo(400))
                        .body("message", not(emptyString()))
                        .body("fieldErrors", notNullValue())
                        .body("requestUri", equalTo(GAMESTATES_ENDPOINT_URI + "/next"));
        }

        @DisplayName("Should return 400 when request body is malformed or invalid JSON")
        @ParameterizedTest(name = "Invalid payload: {0}")
        @ValueSource(strings = {
                "",                             // empty body
                "null",                         // literal null string
                "{\"matrix\": }",               // invalid JSON syntax
                "{\"matrix\": [true false]}",   // missing comma
                "[true, false]",                // JSON array instead of object
                "\"just a string\"",            // JSON string instead of object
                "123",                          // JSON number instead of object
                "true"                          // JSON boolean instead of object
        })
        void shouldReturnBadRequestForMalformedPayloads(String invalidBody) {
            RestAssuredMockMvc
                    .given()
                        .contentType(ContentType.JSON)
                        .body(invalidBody)
                    .when()
                        .post(GAMESTATES_ENDPOINT_URI + "/next")
                    .then()
                        .statusCode(400)
                        .contentType(ContentType.JSON)
                        .body("timestamp", notNullValue())
                        .body("statusCode", equalTo(400))
                        .body("message", not(emptyString()))
                        .body("requestUri", equalTo(GAMESTATES_ENDPOINT_URI + "/next"));
        }
    }
}