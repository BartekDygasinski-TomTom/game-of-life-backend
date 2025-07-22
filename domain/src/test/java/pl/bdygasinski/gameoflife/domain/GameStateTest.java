package pl.bdygasinski.gameoflife.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.bdygasinski.gameoflife.domain.cell.Cell;
import pl.bdygasinski.gameoflife.domain.cell.CellDataProvider;
import pl.bdygasinski.gameoflife.domain.matrix.FixedSizeArrayMatrix2D;
import pl.bdygasinski.gameoflife.domain.matrix.Matrix2D;
import pl.bdygasinski.gameoflife.domain.matrix.MatrixDimensions;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.bdygasinski.gameoflife.domain.cell.Cell.ALIVE;
import static pl.bdygasinski.gameoflife.domain.cell.Cell.DEAD;

@ExtendWith(MockitoExtension.class)
class GameStateTest {

    @Mock
    private Matrix2D<Cell> matrixMock;

    @Spy
    private GameStrategy gameStrategySpy = GameStrategies.CLASSIC_GAME_OF_LIFE_STRATEGY;

    private GameState underTest;

    @BeforeEach
    public void setUp() {
        underTest = new DefaultGameState(matrixMock, gameStrategySpy);
    }

    @DisplayName("nextStep()")
    @Nested
    class NextStepTest {

        @DisplayName("Should produce new board with correct cell changes")
        @Test
        void shouldProduceNewBoardWithCorrectCellChanges() {
            // Given
            var givenData = new Cell[][] {
                    {ALIVE, DEAD}
            };
            var givenBoardDimensions = new MatrixDimensions(1, 2);
            var givenBoardDataProvider = new CellDataProvider(givenData);
            var givenMatrix = new FixedSizeArrayMatrix2D<>(givenBoardDimensions, givenBoardDataProvider);
            var givenStrategy = gameStrategySpy;
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
