package pl.bdygasinski.gameoflife.domain.cell;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import pl.bdygasinski.gameoflife.domain.value.Coordinate2D;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;
import static org.junit.jupiter.api.Assertions.*;

class LiveCellTest {

    private final Coordinate2D givenCoordinate = new Coordinate2D(1, 1);
    private final LiveCell underTest = new LiveCell(givenCoordinate);

    @DisplayName("construction tests")
    @Nested
    class ConstructionTest {

        @DisplayName("Should throw if coordinate is null")
        @Test
        void shouldThrowIfCoordinateIsNull() {
            // When
            Exception result = catchException(() -> new LiveCell(null));

            // Then
            assertThat(result)
                    .isNotNull()
                    .hasMessageContaining("null");
        }

    }

    @DisplayName("die()")
    @Nested
    class DieTest {

        @DisplayName("Should create DeadCell with same Coordinate")
        @Test
        void shouldCreateDeadCellWithSameCoordinate() {
            // When
            DeadCell result = underTest.die();

            // Then
            assertThat(result)
                    .extracting(DeadCell::coordinate2D)
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