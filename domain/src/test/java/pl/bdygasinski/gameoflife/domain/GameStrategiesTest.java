package pl.bdygasinski.gameoflife.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pl.bdygasinski.gameoflife.domain.cell.Cell;
import pl.bdygasinski.gameoflife.domain.cell.CellStats;

import static org.assertj.core.api.Assertions.assertThat;

class GameStrategiesTest {

    @DisplayName("CLASSIC_GAME_OF_LIFE_STRATEGY")
    @Nested
    class ClassicGameOfLifeStrategyTest {

        private final GameStrategy underTest = GameStrategies.CLASSIC_GAME_OF_LIFE_STRATEGY;

        @DisplayName("When neighbor live cells = 3, should transform dead cell to live cell")
        @Test
        void shouldTransformDeadCellToLiveCell() {
            // Given
            var givenCell = Cell.DEAD;
            var givenCellStats = new CellStats(3);

            // When
            var result = underTest.applyCellTransition(givenCell, givenCellStats);

            // Then
            assertThat(result)
                    .isEqualTo(Cell.ALIVE);
        }

        @DisplayName("When neighbor live cells < 2, should transform live cell to dead cell due to underpopulation")
        @Test
        void shouldTransformLiveCellToDeadCell() {
            // Given
            var givenCell = Cell.ALIVE;
            var givenCellStats = new CellStats(1);

            // When
            var result = underTest.applyCellTransition(givenCell, givenCellStats);

            // Then
            assertThat(result)
                    .isEqualTo(Cell.DEAD);
        }

        @DisplayName("When neighbor live cells > 3, should transform live cell to dead cell due to overpopulation")
        @Test
        void shouldTransformLiveCellToDeadCell3() {
            // Given
            var givenCell = Cell.ALIVE;
            var givenCellStats = new CellStats(4);

            // When
            var result = underTest.applyCellTransition(givenCell, givenCellStats);

            // Then
            assertThat(result)
                    .isEqualTo(Cell.DEAD);
        }

        @DisplayName("When neighbor live cells = 2, live cell should live to next generation")
        @ParameterizedTest
        @ValueSource(longs = {2, 3})
        void shouldLive(long liveCells) {
            // Given
            var givenCell = Cell.ALIVE;
            var givenCellStats = new CellStats(liveCells);

            // When
            var result = underTest.applyCellTransition(givenCell, givenCellStats);

            // Then
            assertThat(result)
                    .isEqualTo(Cell.ALIVE);
        }
    }
}