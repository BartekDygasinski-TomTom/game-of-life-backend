package pl.bdygasinski.gameoflife.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.bdygasinski.gameoflife.domain.cell.Cell;
import pl.bdygasinski.gameoflife.domain.matrix.Matrix2D;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;

@ExtendWith(MockitoExtension.class)
class DefaultGameStateTest {

    @Mock
    private Matrix2D<Cell> mockMatrix;

    @Spy
    private GameStrategy spyGameStrategy = GameStrategies.CLASSIC_GAME_OF_LIFE_STRATEGY;

    @InjectMocks
    private DefaultGameState underTest;

    @DisplayName("construction tests")
    @Nested
    class ConstructionTest {

        @DisplayName("Should throw if input cell matrix is null")
        @Test
        void shouldThrowIfInputCellMatrixIsNull() {
            // When
            Exception result = catchException(() -> new DefaultGameState(null, GameStrategies.CLASSIC_GAME_OF_LIFE_STRATEGY));

            // Then
            assertThat(result)
                    .isNotNull()
                    .hasMessageContaining("null");
        }

        @DisplayName("Should throw if input strategy is null")
        @Test
        void shouldThrowIfInputStrategyIsNull() {
            // When
            Exception result = catchException(() -> new DefaultGameState(mockMatrix, null));

            // Then
            assertThat(result)
                    .isNotNull()
                    .hasMessageContaining("null");
        }
    }
}