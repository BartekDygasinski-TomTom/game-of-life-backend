package pl.bdygasinski.gameoflife.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import pl.bdygasinski.gameoflife.domain.cell.Cell;
import pl.bdygasinski.gameoflife.domain.matrix.FixedSizeArrayMatrix2D;
import pl.bdygasinski.gameoflife.domain.matrix.MatrixDimensions;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.bdygasinski.gameoflife.domain.cell.Cell.ALIVE;
import static pl.bdygasinski.gameoflife.domain.cell.Cell.DEAD;

class GameStateTest {

    @DisplayName("nextStep()")
    @Nested
    class NextStepTest {

        @DisplayName("Should produce new board with correct cell changes")
        @Test
        void shouldProduceNewBoardWithCorrectCellChanges() {
            // Given
            var givenData = List.of(ALIVE, DEAD);
            var givenBoardDimensions = new MatrixDimensions(1, 2);
            var givenMatrix = new FixedSizeArrayMatrix2D<>(givenBoardDimensions, givenData, Cell.class);
            var givenStrategy = GameStrategies.CLASSIC_GAME_OF_LIFE_STRATEGY;

            var underTest = new DefaultGameState(givenMatrix, givenStrategy);

            // When
            var result = underTest.nextStep();

            // Then
            assertThat(result)
                    .isNotNull()
                    .isNotSameAs(underTest)
                    .extracting(GameState::board)
                    .isNotEqualTo(givenMatrix);
        }
    }
}
