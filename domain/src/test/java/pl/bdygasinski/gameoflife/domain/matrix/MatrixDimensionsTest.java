package pl.bdygasinski.gameoflife.domain.matrix;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;

class MatrixDimensionsTest {


    @DisplayName("Should throw if rows or columns are <= 0")
    @ParameterizedTest
    @CsvSource({
            "-1,1",
            "0,1",
            "1,-1",
            "1,0"
    })
    void shouldThrowIfRowsOrColumnsAreNotPositive(int rows, int columns) {
        // When
        Exception result = catchException(() -> new MatrixDimensions(rows, columns));

        // Then
        assertThat(result)
                .isNotNull()
                .hasMessageContaining(String.valueOf(rows))
                .hasMessageContaining(String.valueOf(columns));
    }
}