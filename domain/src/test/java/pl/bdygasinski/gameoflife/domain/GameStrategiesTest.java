package pl.bdygasinski.gameoflife.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
            var givenNeighborCells = Stream.of(Cell.ALIVE, Cell.ALIVE, Cell.ALIVE);

            // When
            var result = underTest.applyCellTransition(givenCell, givenNeighborCells);

            // Then
            assertThat(result)
                    .isEqualTo(Cell.ALIVE);
        }

        @DisplayName("When neighbor live cells = 3 and other are dead, should transform dead cell to live cell")
        @Test
        void shouldTransformDeadCellToLiveCell2() {
            // Given
            var givenCell = Cell.DEAD;
            var givenNeighborCells = Stream.of(Cell.DEAD, Cell.ALIVE, Cell.ALIVE, Cell.ALIVE, Cell.DEAD);

            // When
            var result = underTest.applyCellTransition(givenCell, givenNeighborCells);

            // Then
            assertThat(result)
                    .isEqualTo(Cell.ALIVE);
        }

        @DisplayName("When neighbor live cells < 2, should transform live cell to dead cell due to underpopulation")
        @Test
        void shouldTransformLiveCellToDeadCell() {
            // Given
            var givenCell = Cell.ALIVE;
            var givenNeighborCells = Stream.of(Cell.ALIVE);

            // When
            var result = underTest.applyCellTransition(givenCell, givenNeighborCells);

            // Then
            assertThat(result)
                    .isEqualTo(Cell.DEAD);
        }

        @DisplayName("When neighbor live cells < 2 and other are dead, should transform live cell to dead cell due to underpopulation")
        @Test
        void shouldTransformLiveCellToDeadCell2() {
            // Given
            var givenCell = Cell.ALIVE;
            var givenNeighborCells = Stream.of(Cell.DEAD, Cell.ALIVE, Cell.DEAD);

            // When
            var result = underTest.applyCellTransition(givenCell, givenNeighborCells);

            // Then
            assertThat(result)
                    .isEqualTo(Cell.DEAD);
        }

        @DisplayName("When neighbor live cells > 3, should transform live cell to dead cell due to overpopulation")
        @Test
        void shouldTransformLiveCellToDeadCell3() {
            // Given
            var givenCell = Cell.ALIVE;
            var givenNeighborCells = Stream.of(Cell.ALIVE, Cell.ALIVE, Cell.ALIVE, Cell.ALIVE);

            // When
            var result = underTest.applyCellTransition(givenCell, givenNeighborCells);

            // Then
            assertThat(result)
                    .isEqualTo(Cell.DEAD);
        }

        @DisplayName("When neighbor live cells > 3 and other are dead, should transform live cell to dead cell due to overpopulation")
        @Test
        void shouldTransformLiveCellToDeadCell4() {
            // Given
            var givenCell = Cell.ALIVE;
            var givenNeighborCells = Stream.of(Cell.DEAD, Cell.ALIVE, Cell.ALIVE, Cell.ALIVE, Cell.ALIVE, Cell.DEAD);

            // When
            var result = underTest.applyCellTransition(givenCell, givenNeighborCells);

            // Then
            assertThat(result)
                    .isEqualTo(Cell.DEAD);
        }

        @DisplayName("When neighbor live cells = 2, live cell should live to next generation")
        @Test
        void shouldLive() {
            // Given
            var givenCell = Cell.ALIVE;
            var givenNeighborCells = Stream.of(Cell.ALIVE, Cell.ALIVE);

            // When
            var result = underTest.applyCellTransition(givenCell, givenNeighborCells);

            // Then
            assertThat(result)
                    .isEqualTo(Cell.ALIVE);
        }

        @DisplayName("When neighbor live cells = 3, live cell should live to next generation")
        @Test
        void shouldLive2() {
            // Given
            var givenCell = Cell.ALIVE;
            var givenNeighborCells = Stream.of(Cell.ALIVE, Cell.ALIVE, Cell.ALIVE);

            // When
            var result = underTest.applyCellTransition(givenCell, givenNeighborCells);

            // Then
            assertThat(result)
                    .isEqualTo(Cell.ALIVE);
        }

        @DisplayName("When neighbor live cells = 2 and other are dead, live cell should live to next generation")
        @Test
        void shouldLive3() {
            // Given
            var givenCell = Cell.ALIVE;
            var givenNeighborCells = Stream.of(Cell.DEAD, Cell.ALIVE, Cell.ALIVE, Cell.DEAD);

            // When
            var result = underTest.applyCellTransition(givenCell, givenNeighborCells);

            // Then
            assertThat(result)
                    .isEqualTo(Cell.ALIVE);
        }

        @DisplayName("When neighbor live cells = 3 and other are dead, live cell should live to next generation")
        @Test
        void shouldLive4() {
            // Given
            var givenCell = Cell.ALIVE;
            var givenNeighborCells = Stream.of(Cell.DEAD, Cell.ALIVE, Cell.ALIVE, Cell.ALIVE, Cell.DEAD);

            // When
            var result = underTest.applyCellTransition(givenCell, givenNeighborCells);

            // Then
            assertThat(result)
                    .isEqualTo(Cell.ALIVE);
        }
    }
}