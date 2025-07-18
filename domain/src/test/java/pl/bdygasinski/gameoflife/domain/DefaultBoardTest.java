package pl.bdygasinski.gameoflife.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.bdygasinski.gameoflife.domain.matrix.FixedSizeArrayMatrix2D;
import pl.bdygasinski.gameoflife.domain.matrix.Coordinate2D;
import pl.bdygasinski.gameoflife.domain.matrix.Matrix2D;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static pl.bdygasinski.gameoflife.domain.Cell.ALIVE;
import static pl.bdygasinski.gameoflife.domain.Cell.DEAD;

@ExtendWith(MockitoExtension.class)
class DefaultBoardTest {

    @Mock
    private Matrix2D<Cell> matrixMock;

    @Spy
    private GameStrategy gameStrategySpy = GameStrategies.CLASSIC_GAME_OF_LIFE_STRATEGY;

    @InjectMocks
    private DefaultBoard underTest;

    @DisplayName("construction tests")
    @Nested
    class ConstructionTest {

        @DisplayName("Should throw if input cell matrix is null")
        @Test
        void shouldThrowIfInputCellMatrixIsNull() {
            // When
            Exception result = catchException(() -> new DefaultBoard(null, GameStrategies.CLASSIC_GAME_OF_LIFE_STRATEGY));

            // Then
            assertThat(result)
                    .isNotNull()
                    .hasMessageContaining("null");
        }

        @DisplayName("Should throw if input strategy is null")
        @Test
        void shouldThrowIfInputStrategyIsNull() {
            // When
            Exception result = catchException(() -> new DefaultBoard(matrixMock, null));

            // Then
            assertThat(result)
                    .isNotNull()
                    .hasMessageContaining("null");
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

    @DisplayName("toString()")
    @Nested
    class ToStringTest {

        @Test
        @DisplayName("toString should include class name, cellMatrix2D and cellTransitionStrategy")
        void toStringShouldContainExpectedParts() {
            // Arrange
            Matrix2D<Cell> matrixMock = mock(Matrix2D.class);
            GameStrategy strategyMock = mock(GameStrategy.class);

            when(matrixMock.toString()).thenReturn("MatrixMockString");
            when(strategyMock.toString()).thenReturn("StrategyMockString");

            DefaultBoard board = new DefaultBoard(matrixMock, strategyMock);

            // Act
            String result = board.toString();

            // Assert
            assertThat(result)
                    .startsWith("DefaultBoard[")
                    .contains("cellMatrix2D=MatrixMockString")
                    .contains("cellTransitionStrategy=StrategyMockString")
                    .endsWith("]");
        }

    }

    @DisplayName("equals()")
    @Nested
    class EqualsTest {

        @Test
        @DisplayName("Should be equal to itself")
        void shouldBeEqualToItself() {
            // When
            // Then
            assertThat(underTest)
                    .isEqualTo(underTest);

        }

        @Test
        @DisplayName("Should be equal to another object with same values")
        void shouldBeEqualToAnotherWithSameValues() {
            // Given
            var givenData = new Cell[][]{
                    {DEAD, ALIVE}
            };
            var givenMatrix = new FixedSizeArrayMatrix2D<>(givenData);
            var underTest = new DefaultBoard(givenMatrix, gameStrategySpy);

            var givenMatrix2 = new FixedSizeArrayMatrix2D<>(givenData);
            var givenNewMatrixWithSameValues = new DefaultBoard(givenMatrix2, gameStrategySpy);

            // When
            // Then
            assertThat(underTest)
                    .isEqualTo(givenNewMatrixWithSameValues);
        }

        @Test
        @DisplayName("Should not be equal to object with different values")
        void shouldNotBeEqualToDifferentValues() {
            // Given
            var givenData = new Cell[][]{
                    {DEAD, ALIVE}
            };
            var givenMatrix = new FixedSizeArrayMatrix2D<>(givenData);
            var underTest = new DefaultBoard(givenMatrix, gameStrategySpy);

            var givenData2 = new Cell[][]{
                    {ALIVE, ALIVE}
            };
            var givenMatrix2 = new FixedSizeArrayMatrix2D<>(givenData2);
            var givenNewMatrixWithSameValues = new DefaultBoard(givenMatrix2, gameStrategySpy);

            // When
            // Then
            assertThat(underTest)
                    .isNotEqualTo(givenNewMatrixWithSameValues);
        }

        @Test
        @DisplayName("Should not be equal to null")
        void shouldNotBeEqualToNull() {
            // When
            // Then
            assertThat(underTest)
                    .isNotEqualTo(null);
        }

        @Test
        @DisplayName("Should not be equal to different type")
        void shouldNotBeEqualToDifferentType() {
            // When
            // Then
            assertThat(underTest)
                    .isNotEqualTo("Not board");
        }

    }

    @DisplayName("hashCode()")
    @Nested
    class HashCodeTest {

        @Test
        @DisplayName("Should produce same hashCode for equal objects")
        void shouldProduceSameHashCodeForEqualObjects() {
            // When
            // Then
            assertThat(underTest.hashCode())
                    .isEqualTo(underTest.hashCode());
        }

        @Test
        @DisplayName("Should produce different hashCode for not equal objects")
        void shouldProduceDifferentHashCodeForNotEqualObjects() {
            // Given
            var givenData = new Cell[][]{
                    {DEAD, ALIVE}
            };
            var givenMatrix = new FixedSizeArrayMatrix2D<>(givenData);
            var underTest = new DefaultBoard(givenMatrix, gameStrategySpy);

            var givenData2 = new Cell[][]{
                    {ALIVE, ALIVE}
            };
            var givenMatrix2 = new FixedSizeArrayMatrix2D<>(givenData2);
            var givenNewMatrixWithSameValues = new DefaultBoard(givenMatrix2, gameStrategySpy);

            // When
            // Then
            assertThat(underTest.hashCode())
                    .isNotEqualTo(givenNewMatrixWithSameValues.hashCode());
        }
    }
}