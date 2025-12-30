package pl.bdygasinski.gameoflife.rest.adapter;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.bdygasinski.gameoflife.domain.cell.Cell;
import pl.bdygasinski.gameoflife.domain.cell.CellDataProvider;
import pl.bdygasinski.gameoflife.domain.matrix.FixedSizeArrayMatrix2D;
import pl.bdygasinski.gameoflife.domain.matrix.MatrixDimensions;
import pl.bdygasinski.gameoflife.rest.dto.GameStateDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.bdygasinski.gameoflife.domain.cell.Cell.ALIVE;
import static pl.bdygasinski.gameoflife.domain.cell.Cell.DEAD;


class MatrixMapperTest {

    private final MatrixMapper underTest = new MatrixMapper();

    @DisplayName("Should map to domain")
    @Test
    void shouldMapToDomain() {
        // Given
        var givenRow1 = List.of(true, false, false);
        var givenRow2 = List.of(false, true, true);
        var givenMatrixData = List.of(givenRow1, givenRow2);
        var givenDto = new GameStateDto(givenMatrixData);

        // When
        var result = underTest.toDomain(givenDto);

        // Then
        assertThat(result.toFlatList())
                .containsExactly(ALIVE, DEAD, DEAD, DEAD, ALIVE, ALIVE);
    }

    @DisplayName("Should map to dto")
    @Test
    void shouldMapToDto() {
        // Given
        var cellDataProvider = new CellDataProvider(new Cell[][]{
                {ALIVE, DEAD, DEAD, ALIVE},
                {ALIVE, DEAD, ALIVE, ALIVE}
        });
        var givenMatrixDimensions = new MatrixDimensions(2, 4);
        var givenMatrix = new FixedSizeArrayMatrix2D<>(givenMatrixDimensions, cellDataProvider);

        // When
        var result = underTest.toDto(givenMatrix);

        // Then
        var matrix = result.matrix();
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions
                .assertThat(matrix.getFirst())
                .containsExactly(true, false, false, true);

        softAssertions
                .assertThat(matrix.getLast())
                .containsExactly(true, false, true, true);
        softAssertions.assertAll();
    }
}