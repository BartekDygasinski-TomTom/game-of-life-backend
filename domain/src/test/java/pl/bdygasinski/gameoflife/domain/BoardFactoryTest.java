package pl.bdygasinski.gameoflife.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import pl.bdygasinski.gameoflife.domain.matrix.Matrix2D;

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


        @DisplayName("Should have throw if rows or cols are <= 0")
        @ParameterizedTest
        @CsvSource({
                "-1, 1",
                "0, 1",
                "1, -1",
                "1, 0"
        })
        void shouldThrowIfRowsOrColsAreNotValid(int rows, int cols) {
            // When
            Exception result = catchException(() -> underTest.randomBoardWithAlivePercentage(rows, cols, 0.3, GameStrategies.CLASSIC_GAME_OF_LIFE_STRATEGY));

            // Then
            assertThat(result)
                    .isNotNull();
            assertThat(result.getMessage())
                    .containsAnyOf(String.valueOf(rows), String.valueOf(cols));
        }

    }
}
