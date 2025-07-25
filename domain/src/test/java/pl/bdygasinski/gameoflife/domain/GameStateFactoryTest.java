package pl.bdygasinski.gameoflife.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import pl.bdygasinski.gameoflife.domain.cell.Cell;
import pl.bdygasinski.gameoflife.domain.matrix.Matrix2D;
import pl.bdygasinski.gameoflife.domain.matrix.MatrixDimensions;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;

class GameStateFactoryTest {

    public static final GameStrategy DEFAULT_STRATEGY = GameStrategies.CLASSIC_GAME_OF_LIFE_STRATEGY;
    private final GameStateFactory underTest = new GameStateFactory();

    @DisplayName("fromBaseState()")
    @Nested
    class FromBaseStateTest {

        @DisplayName("Should throw if input cell matrix is null")
        @Test
        void shouldThrowIfInputCellMatrixIsNull() {
            assertThatThrownBy(() -> underTest.fromBaseState(null, DEFAULT_STRATEGY))
                    .isNotNull()
                    .hasMessageContaining("null");
        }

        @DisplayName("Should throw if input strategy is null")
        @Test
        void shouldThrowIfInputStrategyIsNull() {
            assertThatThrownBy(() -> underTest.fromBaseState(mock(Matrix2D.class), null))
                    .isNotNull()
                    .hasMessageContaining("null");
        }
    }

    @DisplayName("randomBoardWithAlivePercentage()")
    @Nested
    class RandomGameStateWithAlivePercentageTest {

        @DisplayName("Should throw if alive percentage is not valid")
        @ParameterizedTest
        @ValueSource(doubles = {-1, -0.000001 -0.0, 1.000001})
        void shouldThrowIfAlivePercentageIsNotValid(double alivePercentage) {
            // When
            var givenDimensions = new MatrixDimensions(4, 4);
            var result = catchException(() -> underTest.randomGameStateWithAlivePercentage(givenDimensions, alivePercentage, DEFAULT_STRATEGY));

            // Then
            assertThat(result)
                    .isNotNull()
                    .hasMessageContaining(String.valueOf(alivePercentage));
        }

        @DisplayName("Should give alive percentage of live cells in matrix")
        @ParameterizedTest
        @CsvSource({
                "0.0, 4, 4, 0",
                "0.5, 4, 4, 8",
                "1, 4, 4, 16"
        })
        void shouldHaveGivenAlivePercentageOfLiveCellsInMatrix(double alivePercentage, int rows, int cols, long expectedCount) {
            // Given
            var givenDimensions = new MatrixDimensions(rows, cols);

            // When
            var result = underTest.randomGameStateWithAlivePercentage(givenDimensions, alivePercentage, GameStrategies.CLASSIC_GAME_OF_LIFE_STRATEGY);

            // Then
            var resultMatrix = result.board();
            assertThat(resultMatrix.getDimensions())
                    .isEqualTo(givenDimensions);

            var actualAliveCount = resultMatrix
                    .toFlatList()
                    .stream()
                    .filter(cell -> cell == Cell.ALIVE)
                    .count();

            assertThat(actualAliveCount)
                    .isEqualTo(expectedCount);

        }


        @DisplayName("Should have throw if dimension is null")
        @Test
        void shouldThrowIfDimensionIsNull() {
            assertThatThrownBy(() -> underTest.randomGameStateWithAlivePercentage(null, 0.3, DEFAULT_STRATEGY))
                    .isNotNull()
                    .hasMessageContaining("null");
        }

    }
}
