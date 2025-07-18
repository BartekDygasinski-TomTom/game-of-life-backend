package pl.bdygasinski.gameoflife.domain.matrix;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;

class Coordinate2DTest {

    private final Coordinate2D underTest = new Coordinate2D(1, 1);


    @DisplayName("Creation tests")
    @Nested
    class CreationTest {

        @DisplayName("Should throw if x is negative")
        @ParameterizedTest
        @ValueSource(ints = {Integer.MIN_VALUE, -1})
        void shouldThrowIfXIsNegative(int x) {
            // Given
            var givenCorrectY = 2;

            // When
            Exception result = catchException(() -> new Coordinate2D(x, givenCorrectY));

            // Then
            assertThat(result)
                    .isNotNull()
                    .hasMessageContaining(String.valueOf(x));
        }

        @DisplayName("Should throw if y is negative")
        @ParameterizedTest
        @ValueSource(ints = {Integer.MIN_VALUE, -1})
        void shouldThrowIfYIsNegative(int y) {
            // Given
            var givenCorrectX = 2;

            // When
            Exception result = catchException(() -> new Coordinate2D(givenCorrectX, y));

            // Then
            assertThat(result)
                    .isNotNull()
                    .hasMessageContaining(String.valueOf(y));
        }

        @DisplayName("Should create object if y and x are >= 0")
        @ParameterizedTest
        @ValueSource(ints = {0, Integer.MAX_VALUE})
        void shouldCreateObjectWhenCoordsAreValid(int x) {
            // Given
            var y = x;

            // When
            Exception result = catchException(() -> new Coordinate2D(x, y));

            // Then
            assertThat(result)
                    .isNull();
        }
    }

    @DisplayName("offset()")
    @Nested
    class OffsetTest {

        @DisplayName("Should give correct coordinate")
        @ParameterizedTest
        @CsvSource({
                "1,1",
                "-1,-1",
                "0,1",
                "0,-1",
                "1, 0",
                "-1,0",
                "0, 0"
        })
        void shouldGiveCorrectCoordinate(int deltaX, int deltaY) {
            // When
            Coordinate2D result = underTest.offset(deltaX, deltaY);

            // Then
            assertThat(result)
                    .isEqualTo(new Coordinate2D(
                            underTest.x() + deltaX,
                            underTest.y() + deltaY
                    ));
        }
    }

    @DisplayName("up()")
    @Nested
    class UpTest {

        @DisplayName("Should create new coordinate with y - 1")
        @Test
        void shouldMove() {
            // When
            Coordinate2D result = underTest.up();

            // Then
            assertThat(result.y())
                    .isEqualTo(underTest.y() - 1);
        }
    }

    @DisplayName("down()")
    @Nested
    class DownTest {

        @DisplayName("Should create new coordinate with y + 1")
        @Test
        void shouldMove() {
            // When
            Coordinate2D result = underTest.down();

            // Then
            assertThat(result.y())
                    .isEqualTo(underTest.y() + 1);
        }
    }

    @DisplayName("left()")
    @Nested
    class LeftTest {

        @DisplayName("Should create new coordinate with x - 1")
        @Test
        void shouldMove() {
            // When
            Coordinate2D result = underTest.left();

            // Then
            assertThat(result.x())
                    .isEqualTo(underTest.x() - 1);
        }
    }

    @DisplayName("right()")
    @Nested
    class RightTest {

        @DisplayName("Should create new coordinate with x + 1")
        @Test
        void shouldMove() {
            // When
            Coordinate2D result = underTest.right();

            // Then
            assertThat(result.x())
                    .isEqualTo(underTest.x() + 1);
        }
    }

    @DisplayName("upLeft()")
    @Nested
    class UpLeftTest {

        @DisplayName("Should create new coordinate with x - 1, y - 1")
        @Test
        void shouldMove() {
            // When
            Coordinate2D result = underTest.upLeft();

            // Then
            assertThat(result.x())
                    .isEqualTo(underTest.x() - 1);

            assertThat(result.y())
                    .isEqualTo(underTest.y() - 1);
        }
    }

    @DisplayName("upRight()")
    @Nested
    class UpRightTest {

        @DisplayName("Should create new coordinate with x + 1, y - 1")
        @Test
        void shouldMove() {
            // When
            Coordinate2D result = underTest.upRight();

            // Then
            assertThat(result.x())
                    .isEqualTo(underTest.x() + 1);

            assertThat(result.y())
                    .isEqualTo(underTest.y() - 1);
        }
    }

    @DisplayName("downLeft()")
    @Nested
    class DownLeftTest {

        @DisplayName("Should create new coordinate with x - 1, y + 1")
        @Test
        void shouldMove() {
            // When
            Coordinate2D result = underTest.downLeft();

            // Then
            assertThat(result.x())
                    .isEqualTo(underTest.x() - 1);

            assertThat(result.y())
                    .isEqualTo(underTest.y() + 1);
        }
    }

    @DisplayName("downRight()")
    @Nested
    class DownRightTest {

        @DisplayName("Should create new coordinate with x + 1, y + 1")
        @Test
        void shouldMove() {
            // When
            Coordinate2D result = underTest.downRight();

            // Then
            assertThat(result.x())
                    .isEqualTo(underTest.x() + 1);

            assertThat(result.y())
                    .isEqualTo(underTest.y() + 1);
        }
    }

    @DisplayName("allNeighborCoordinates()")
    @Nested
    class AllNeighborCoordinatesTest {

        @DisplayName("Should return all neighbor coordinates if they are valid")
        @Test
        void shouldReturnAllNeighborCoordinates() {
            // Given
            var underTest = new Coordinate2D(1, 1);

            // When
            List<Coordinate2D> result = underTest.allNeighborCoordinates();

            // Then
            assertThat(result)
                    .containsExactly(
                            new Coordinate2D(1, 0),
                            new Coordinate2D(1, 2),
                            new Coordinate2D(0, 1),
                            new Coordinate2D(2, 1),
                            new Coordinate2D(0, 0),
                            new Coordinate2D(2, 0),
                            new Coordinate2D(0, 2),
                            new Coordinate2D(2, 2)
                    );
        }

        @DisplayName("Should not return not valid neighbor coordinates")
        @Test
        void shouldNotReturnNotValidNeighborCoordinates() {
            // Given
            var underTest = new Coordinate2D(0, 0);

            // When
            List<Coordinate2D> result = underTest.allNeighborCoordinates();

            // Then
            assertThat(result)
                    .containsExactly(
                            new Coordinate2D(0, 1),
                            new Coordinate2D(1, 0),
                            new Coordinate2D(1, 1));
        }

    }

    @DisplayName("x()")
    @Nested
    class XTest {

        @DisplayName("Should return same value as an input")
        @ParameterizedTest
        @ValueSource(ints = {0, Integer.MAX_VALUE})
        void shouldPassInput(int x) {
            // Given
            var underTest = new Coordinate2D(x, 0);

            // When
            int result = underTest.x();

            // Then
            assertThat(result)
                    .isEqualTo(x);
        }
    }

    @DisplayName("y()")
    @Nested
    class YTest {

        @DisplayName("Should return same value as an input")
        @ParameterizedTest
        @ValueSource(ints = {0, Integer.MAX_VALUE})
        void shouldPassInput(int y) {
            // Given
            var underTest = new Coordinate2D(0, y);

            // When
            int result = underTest.y();

            // Then
            assertThat(result)
                    .isEqualTo(y);
        }
    }
}