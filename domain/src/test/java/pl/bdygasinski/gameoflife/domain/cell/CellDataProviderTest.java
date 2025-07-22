package pl.bdygasinski.gameoflife.domain.cell;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;

class CellDataProviderTest {

    @DisplayName("Should throw if input is null")
    @Test
    void shouldThrowIfInputIsNull() {
        // When
        var result = catchException(() -> new CellDataProvider(null));

        // Then
        assertThat(result)
                .isNotNull()
                .hasMessageContaining("null");
    }

    @DisplayName("Should return input")
    @Test
    void provide() {
        // Given
        var givenCells = new Cell[][]{
                {Cell.DEAD, Cell.ALIVE},
                {Cell.DEAD, Cell.ALIVE},
                {Cell.ALIVE, Cell.ALIVE}
        };
        var underTest = new CellDataProvider(givenCells);

        // When
        var result = underTest.provide();

        // Then
        assertThat(result)
                .isEqualTo(givenCells);
    }

    @DisplayName("Should return new array of cells")
    @Test
    void testClone() {
        // Given
        var givenCells = new Cell[][]{
                {Cell.DEAD, Cell.ALIVE},
                {Cell.DEAD, Cell.ALIVE},
                {Cell.ALIVE, Cell.ALIVE}
        };
        var underTest = new CellDataProvider(givenCells);

        // When
        var result = underTest.clone();

        // Then
        assertThat(result)
                .isEqualTo(givenCells)
                .isNotSameAs(givenCells);

        result[0][0] = Cell.ALIVE;

        assertThat(result)
                .isNotEqualTo(givenCells);

        assertThat(givenCells[0][0])
                .isEqualTo(Cell.DEAD);
    }
}