package pl.bdygasinski.gameoflife.domain.cell;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import pl.bdygasinski.gameoflife.domain.value.Coordinate2D;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;

class DeadCellTest {

    private final Coordinate2D givenCoordinate = new Coordinate2D(1, 1);
    private final DeadCell underTest = new DeadCell(givenCoordinate);

    @DisplayName("construction tests")
    @Nested
    class ConstructionTest {

        @DisplayName("Should throw if coordinate is null")
        @Test
        void shouldThrowIfCoordinateIsNull() {
            // When
            Exception result = catchException(() -> new DeadCell(null));

            // Then
            assertThat(result)
                    .isNotNull()
                    .hasMessageContaining("null");
        }

    }

    @DisplayName("resurrect()")
    @Nested
    class ResurrectTest {

        @DisplayName("Should create LiveCell with same Coordinate")
        @Test
        void shouldCreateLiveCellWithSameCoordinate() {
            // When
            LiveCell result = underTest.resurrect();

            // Then
            assertThat(result)
                    .extracting(LiveCell::coordinate2D)
                    .isEqualTo(givenCoordinate);
        }
    }

    @DisplayName("coordinate2D()")
    @Nested
    class Coordinate2DTest {

        @DisplayName("Should return output same as an input")
        @Test
        void shouldReturnOutputSameAsAnInput() {
            // When
            Coordinate2D result = underTest.coordinate2D();

            // Then
            assertThat(result)
                    .isEqualTo(givenCoordinate);
        }
    }
}