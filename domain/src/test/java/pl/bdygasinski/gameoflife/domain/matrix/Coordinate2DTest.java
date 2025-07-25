package pl.bdygasinski.gameoflife.domain.matrix;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class Coordinate2DTest {

    private final Coordinate2D underTest = Coordinate2D.from(1, 1).orElseThrow();
    private final int CORRECT_X = 2;
    private final int CORRECT_Y = 2;


    @DisplayName("Creation tests")
    @Nested
    class CreationTest {

        @DisplayName("Should return empty optional if x is negative")
        @ParameterizedTest
        @ValueSource(ints = {Integer.MIN_VALUE, -1})
        void shouldReturnEmptyOptionalIfXIsNegative(int x) {
            // When
            var result = Coordinate2D.from(x, CORRECT_Y);

            // Then
            assertThat(result)
                    .isEmpty();
        }

        @DisplayName("Should return empty optional y is negative")
        @ParameterizedTest
        @ValueSource(ints = {Integer.MIN_VALUE, -1})
        void shouldReturnEmptyOptionalIfYIsNegative(int y) {
            // When
            var result = Coordinate2D.from(CORRECT_X, y);

            // Then
            assertThat(result)
                    .isEmpty();
        }

        @DisplayName("Should create object if y and x are >= 0")
        @ParameterizedTest
        @ValueSource(ints = {0, Integer.MAX_VALUE})
        void shouldCreateObjectWhenCoordsAreValid(int x) {
            // Given
            var y = x;

            // When
            var result = Coordinate2D.from(x, y);

            // Then
            assertThat(result)
                    .isPresent();
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
            var result = underTest.offset(deltaX, deltaY);

            // Then
            assertThat(result)
                    .isNotEmpty()
                    .isEqualTo(Coordinate2D.from(
                            underTest.getX() + deltaX,
                            underTest.getY() + deltaY
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
            var result = underTest.up();

            // Then
            var expected = Coordinate2D.from(underTest.getX(), underTest.getY() - 1);
            assertThat(result)
                    .isNotEmpty()
                    .isEqualTo(expected);
        }
    }

    @DisplayName("down()")
    @Nested
    class DownTest {

        @DisplayName("Should create new coordinate with y + 1")
        @Test
        void shouldMove() {
            // When
            var result = underTest.down();

            // Then
            var expected = Coordinate2D.from(underTest.getX(), underTest.getY() + 1);
            assertThat(result)
                    .isNotEmpty()
                    .isEqualTo(expected);
        }
    }

    @DisplayName("left()")
    @Nested
    class LeftTest {

        @DisplayName("Should create new coordinate with x - 1")
        @Test
        void shouldMove() {
            // When
            var result = underTest.left();

            // Then
            var expected = Coordinate2D.from(underTest.getX() - 1, underTest.getY());
            assertThat(result)
                    .isNotEmpty()
                    .isEqualTo(expected);
        }
    }

    @DisplayName("right()")
    @Nested
    class RightTest {

        @DisplayName("Should create new coordinate with x + 1")
        @Test
        void shouldMove() {
            // When
            var result = underTest.right();

            // Then
            var expected = Coordinate2D.from(underTest.getX() + 1, underTest.getY());
            assertThat(result)
                    .isNotEmpty()
                    .isEqualTo(expected);
        }
    }

    @DisplayName("upLeft()")
    @Nested
    class UpLeftTest {

        @DisplayName("Should create new coordinate with x - 1, y - 1")
        @Test
        void shouldMove() {
            // When
            var result = underTest.upLeft();

            // Then
            var expected = Coordinate2D.from(underTest.getX() - 1, underTest.getY() - 1);
            assertThat(result)
                    .isNotEmpty()
                    .isEqualTo(expected);

        }
    }

    @DisplayName("upRight()")
    @Nested
    class UpRightTest {

        @DisplayName("Should create new coordinate with x + 1, y - 1")
        @Test
        void shouldMove() {
            // When
            var result = underTest.upRight();

            // Then
            var expected = Coordinate2D.from(underTest.getX() + 1, underTest.getY() - 1);
            assertThat(result)
                    .isNotEmpty()
                    .isEqualTo(expected);
        }
    }

    @DisplayName("downLeft()")
    @Nested
    class DownLeftTest {

        @DisplayName("Should create new coordinate with x - 1, y + 1")
        @Test
        void shouldMove() {
            // When
            var result = underTest.downLeft();

            // Then
            var expected = Coordinate2D.from(underTest.getX() - 1, underTest.getY() + 1);
            assertThat(result)
                    .isNotEmpty()
                    .isEqualTo(expected);
        }
    }

    @DisplayName("downRight()")
    @Nested
    class DownRightTest {

        @DisplayName("Should create new coordinate with x + 1, y + 1")
        @Test
        void shouldMove() {
            // When
            var result = underTest.downRight();

            // Then
            var expected = Coordinate2D.from(underTest.getX() + 1, underTest.getY() + 1);
            assertThat(result)
                    .isNotEmpty()
                    .isEqualTo(expected);
        }
    }

    @DisplayName("allNeighborCoordinates()")
    @Nested
    class AllNeighborCoordinatesTest {

        @DisplayName("Should return all neighbor coordinates if they are valid")
        @Test
        void shouldReturnAllNeighborCoordinates() {
            // When
            var result = underTest.allNeighborCoordinates();

            // Then
            var expected = Stream.of(
                            Coordinate2D.from(1, 0),
                            Coordinate2D.from(1, 2),
                            Coordinate2D.from(0, 1),
                            Coordinate2D.from(2, 1),
                            Coordinate2D.from(0, 0),
                            Coordinate2D.from(2, 0),
                            Coordinate2D.from(0, 2),
                            Coordinate2D.from(2, 2))
                    .flatMap(Optional::stream)
                    .toArray(Coordinate2D[]::new);

            assertThat(result)
                    .containsExactly(expected);
        }

        @DisplayName("Should not return not valid neighbor coordinates")
        @Test
        void shouldNotReturnNotValidNeighborCoordinates() {
            // Given
            var underTest = Coordinate2D.from(0, 0).orElseThrow();

            // When
            var result = underTest.allNeighborCoordinates();

            // Then
            var expected = Stream.of(
                            Coordinate2D.from(0, 1),
                            Coordinate2D.from(1, 0),
                            Coordinate2D.from(1, 1))
                    .flatMap(Optional::stream)
                    .toArray(Coordinate2D[]::new);

            assertThat(result)
                    .containsExactly(expected);
        }
    }
}