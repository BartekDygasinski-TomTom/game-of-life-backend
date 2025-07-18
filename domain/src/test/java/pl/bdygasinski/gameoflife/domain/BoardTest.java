package pl.bdygasinski.gameoflife.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.bdygasinski.gameoflife.domain.matrix.FixedSizeArrayMatrix2D;
import pl.bdygasinski.gameoflife.domain.matrix.Matrix2D;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.bdygasinski.gameoflife.domain.Cell.ALIVE;
import static pl.bdygasinski.gameoflife.domain.Cell.DEAD;

@ExtendWith(MockitoExtension.class)
class BoardTest {

    @Mock
    private Matrix2D<Cell> matrixMock;

    @Spy
    private GameStrategy gameStrategySpy = GameStrategies.CLASSIC_GAME_OF_LIFE_STRATEGY;

    private Board underTest;

    @BeforeEach
    public void setUp() {
        underTest = new DefaultBoard(matrixMock, gameStrategySpy);
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
            var givenMatrix = new FixedSizeArrayMatrix2D<>(givenData);
            var givenStrategy = gameStrategySpy;
            var underTest = new DefaultBoard(givenMatrix, givenStrategy);

            // When
            var result = underTest.nextStep();

            // Then
            assertThat(result)
                    .isNotNull()
                    .isNotSameAs(underTest)
                    .extracting(Board::cellMatrix2D)
                    .isNotEqualTo(givenMatrix);
        }
    }
}
