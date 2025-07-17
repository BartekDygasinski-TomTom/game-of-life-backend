package pl.bdygasinski.gameoflife.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import pl.bdygasinski.gameoflife.domain.matrix.ArrayMatrix2D;
import pl.bdygasinski.gameoflife.domain.matrix.Coordinate2D;
import pl.bdygasinski.gameoflife.domain.matrix.Matrix2D;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;
import static org.mockito.Mockito.mock;

class BoardFactoryTest {

    private final BoardFactory underTest = new BoardFactory();

    @DisplayName("fromBaseState()")
    @Nested
    class FromBaseStateTest {

        @DisplayName("Should throw if input cell matrix is null")
        @Test
        void shouldThrowIfInputCellMatrixIsNull() {
            // When
            Exception result = catchException(() -> underTest.fromBaseState(null, GameStrategies.CLASSIC_GAME_OF_LIFE_STRATEGY));

            // Then
            assertThat(result)
                    .isNotNull()
                    .hasMessageContaining("null");
        }

        @DisplayName("Should throw if input strategy is null")
        @Test
        void shouldThrowIfInputStrategyIsNull() {
            // When
            var givenMatrix = mock(Matrix2D.class);
            Exception result = catchException(() -> underTest.fromBaseState(givenMatrix, null));

            // Then
            assertThat(result)
                    .isNotNull()
                    .hasMessageContaining("null");
        }
    }

    @DisplayName("generateAllCoordinates()")
    @Nested
    class GenerateAllCoordinatesTest {

        @DisplayName("Should give list of coordinates of all items in given rows and cols")
        @Test
        void shouldGiveListOfCoordinatesOfAllItemsInGivenRowsAndCols() {
            // Given
            var givenRows = 3;
            var givenCols = 5;

            // When
            var result = underTest.generateAllCoordinates(givenRows, givenCols);

            // Then
            assertThat(result)
                    .hasSize(givenCols * givenRows);

            assertThat(result.stream().mapToInt(Coordinate2D::x).min())
                    .hasValue(0);
            assertThat(result.stream().mapToInt(Coordinate2D::x).max())
                    .hasValue(givenCols - 1);

            assertThat(result.stream().mapToInt(Coordinate2D::y).min())
                    .hasValue(0);
            assertThat(result.stream().mapToInt(Coordinate2D::y).max())
                    .hasValue(givenRows - 1);
        }
    }

    @DisplayName("pickRandomCoordinatesSubset()")
    @Nested
    class PickRandomCoordinatesSubsetTest {

        @DisplayName("Should pick input count of elements")
        @ParameterizedTest
        @ValueSource(ints = {Integer.MIN_VALUE, 0, Integer.MAX_VALUE})
        void shouldPickInputCountOfElements(int count) {
            // Given
            var givenList = new ArrayList<>(List.of(
                    new Coordinate2D(0, 0), new Coordinate2D(1, 0), new Coordinate2D(2, 0),
                    new Coordinate2D(0, 1), new Coordinate2D(1, 1), new Coordinate2D(2, 1)
            ));
            int expectedSize = Math.max(0, Math.min(count, givenList.size()));

            // When
            var result = underTest.pickRandomCoordinatesSubset(givenList, count);

            // Then
            assertThat(result)
                    .hasSize(expectedSize);
        }
    }

    @DisplayName("buildMatrix()")
    @Nested
    class BuildMatrixTest {

        @DisplayName("Should build matrix given rows * given columns with LIVE cells in provided coordinates")
        @Test
        void shouldBuildMatrixWithGivenSizeWithLiveCellsAtProvidedCoordinates() {
            // Given
            Set<Coordinate2D> givenPositionOfLiveCells = Set.of(
                    new Coordinate2D(0, 0), new Coordinate2D(3, 2),
                    new Coordinate2D(1, 2), new Coordinate2D(3, 3)
            );
            var givenColSize = 5;
            var givenRowSize = 4;

            // When
            Cell[][] result = underTest.buildMatrix(givenRowSize, givenColSize, givenPositionOfLiveCells);

            // Then
            assertThat(result)
                    .isNotNull()
                    .hasDimensions(givenRowSize, givenColSize);

            ArrayMatrix2D<Cell> wrapperMatrix = new ArrayMatrix2D<>(result);
           var partitioned = wrapperMatrix
                    .getAvailableCoordinates()
                    .stream()
                    .collect(partitioningBy(
                            givenPositionOfLiveCells::contains,
                            mapping(wrapperMatrix::getValueAt, toList())
                    ));

            assertThat(partitioned.get(true))   // cells that SHOULD be alive
                    .as("Cells at expected ALIVE coordinates")
                    .allMatch(cell -> cell == Cell.ALIVE);

            assertThat(partitioned.get(false))  // cells that SHOULD be dead
                    .as("Cells at remaining coordinates")
                    .allMatch(cell -> cell == Cell.DEAD);
        }

    }
    @DisplayName("randomBoardWithAlivePercentage()")
    @Nested
    class RandomBoardWithAlivePercentageTest {

        @DisplayName("Should throw if alive percentage is not valid")
        @ParameterizedTest
        @ValueSource(doubles = {-1, -0.000001 -0.0, 1.000001})
        void shouldThrowIfAlivePercentageIsNotValid(double alivePercentage) {
            // When
            var givenStrategy = GameStrategies.CLASSIC_GAME_OF_LIFE_STRATEGY;
            Exception result = catchException(() -> underTest.randomBoardWithAlivePercentage(4, 4, alivePercentage, givenStrategy));

            // Then
            assertThat(result)
                    .isNotNull()
                    .hasMessageContaining(String.valueOf(alivePercentage));
        }

        @DisplayName("Should have given alive percentage of live cells in matrix")
        @ParameterizedTest
        @CsvSource({
                "0.0, 4, 4, 0",
                "0.5, 4, 4, 8",
                "1, 4, 4, 16"
        })
        void shouldHaveGivenAlivePercentageOfLiveCellsInMatrix(double alivePercentage, int rows, int cols, double expectedCount) {
            // When
            Board result = underTest.randomBoardWithAlivePercentage(rows, cols, alivePercentage, GameStrategies.CLASSIC_GAME_OF_LIFE_STRATEGY);

            // Then
            Matrix2D<Cell> resultMatrix = result.cellMatrix2D();
            assertThat(resultMatrix.rowCount())
                    .isEqualTo(rows);
            assertThat(resultMatrix.columnCount())
                    .isEqualTo(cols);

            double actualAliveCount = resultMatrix
                    .toFlatList()
                    .stream()
                    .filter(cell -> cell == Cell.ALIVE)
                    .count();

            assertThat(actualAliveCount)
                    .isEqualTo(expectedCount);

        }

    }
}
