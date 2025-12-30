package pl.bdygasinski.gameoflife.rest.validation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import pl.bdygasinski.gameoflife.rest.dto.GameStateDto;

import static org.assertj.core.api.Assertions.assertThat;

class MatrixValidatorTest {

    private final MatrixValidator underTest = new MatrixValidator();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @DisplayName("Should fail with invalid input")
    @ParameterizedTest
    @MethodSource("pl.bdygasinski.gameoflife.rest.TestUtils#boardInvalidCases")
    void isValid(String invalidMatrixJson) throws JsonProcessingException {
        // Given
        var givenInvalidMatrix = objectMapper.readValue(invalidMatrixJson, GameStateDto.class).matrix();

        // When
        var result = underTest.isValid(givenInvalidMatrix, null);

        // Then
        assertThat(result)
                .isFalse();
    }

    @DisplayName("Should return true for valid input")
    @ParameterizedTest
    @MethodSource("pl.bdygasinski.gameoflife.rest.TestUtils#boardValidCases")
    void isValid2(String validMatrixJson) throws JsonProcessingException {
        // Given
        var givenInvalidMatrix = objectMapper.readValue(validMatrixJson, GameStateDto.class).matrix();

        // When
        var result = underTest.isValid(givenInvalidMatrix, null);

        // Then
        assertThat(result)
                .isTrue();
    }
}