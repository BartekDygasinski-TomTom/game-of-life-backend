package pl.bdygasinski.gameoflife.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.bdygasinski.gameoflife.domain.cell.Cell;
import pl.bdygasinski.gameoflife.domain.cell.CellStats;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class GameStrategiesTest {

    @DisplayName("CLASSIC_GAME_OF_LIFE_STRATEGY")
    @Nested
    class ClassicGameOfLifeStrategyTest {

        private final GameStrategy underTest = GameStrategies.CLASSIC_GAME_OF_LIFE_STRATEGY;

        @DisplayName("Should correctly transform live cell")
        @ParameterizedTest
        @MethodSource({
                "liveCellsWithExpectedResults",
                "deadCellsWithExpectedResults"
        })
        void shouldCorrectlyTransformLiveCell(int liveCells, Cell givenCell, Cell expectedCell) {
            // Given
            var givenCellStats = new CellStats(liveCells);

            // When
            var result = underTest.applyCellTransition(givenCell, givenCellStats);

            // Then
            assertThat(result)
                    .isEqualTo(expectedCell);
        }



        private static Stream<Arguments> liveCellsWithExpectedResults() {
            return IntStream
                    .rangeClosed(0, 8)
                    .mapToObj(i -> Arguments.of(i, Cell.ALIVE, (i < 2 || i > 3) ? Cell.DEAD : Cell.ALIVE));
        }

        private static Stream<Arguments> deadCellsWithExpectedResults() {
            return IntStream
                    .rangeClosed(0, 8)
                    .mapToObj(i -> Arguments.of(i, Cell.DEAD, (i == 3) ? Cell.ALIVE : Cell.DEAD));
        }
    }
}