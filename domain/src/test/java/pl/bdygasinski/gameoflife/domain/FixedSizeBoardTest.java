package pl.bdygasinski.gameoflife.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.bdygasinski.gameoflife.domain.matrix.ArrayMatrix2D;
import pl.bdygasinski.gameoflife.domain.matrix.Coordinate2D;
import pl.bdygasinski.gameoflife.domain.matrix.Matrix2D;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static pl.bdygasinski.gameoflife.domain.Cell.ALIVE;
import static pl.bdygasinski.gameoflife.domain.Cell.DEAD;

@ExtendWith(MockitoExtension.class)
class FixedSizeBoardTest {

    @Mock
    private Matrix2D<Cell> matrixMock;

    @Spy
    private GameStrategy gameStrategySpy = GameStrategies.CLASSIC_GAME_OF_LIFE_STRATEGY;

    @InjectMocks
    private FixedSizeBoard underTest;

    @DisplayName("construction tests")
    @Nested
    class ConstructionTest {

        @DisplayName("Should throw if input cell matrix is null")
        @Test
        void shouldThrowIfInputCellMatrixIsNull() {
            // When
            Exception result = catchException(() -> new FixedSizeBoard(null, GameStrategies.CLASSIC_GAME_OF_LIFE_STRATEGY));

            // Then
            assertThat(result)
                    .isNotNull()
                    .hasMessageContaining("null");
        }

        @DisplayName("Should throw if input strategy is null")
        @Test
        void shouldThrowIfInputStrategyIsNull() {
            // When
            Exception result = catchException(() -> new FixedSizeBoard(matrixMock, null));

            // Then
            assertThat(result)
                    .isNotNull()
                    .hasMessageContaining("null");
        }
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
            var givenMatrix = new ArrayMatrix2D<>(givenData);
            var givenStrategy = gameStrategySpy;
            var underTest = new FixedSizeBoard(givenMatrix, givenStrategy);

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

    @DisplayName("getNeighborCells()")
    @Nested
    class GetNeighborCellsTest {

        @DisplayName("Should get neighbor cells when they are present in board")
        @Test
        void shouldGetNeighborCells(){
            // Given
            var givenCoordinate = new Coordinate2D(0, 0);
            given(matrixMock.getValueAt(any()))
                    .willReturn(ALIVE);
            given(matrixMock.containsCoordinate(any()))
                    .willReturn(true);


            // When
            var result = underTest.getNeighborCells(givenCoordinate);

            // Then
            var expectedNeighborCoordinates = givenCoordinate.allNeighborCoordinates();
            assertThat(result)
                    .hasSize(expectedNeighborCoordinates.size());
        }
    }
}