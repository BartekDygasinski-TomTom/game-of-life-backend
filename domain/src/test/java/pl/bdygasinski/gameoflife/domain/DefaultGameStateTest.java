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

import static org.assertj.core.api.Assertions.*;

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
            assertThatThrownBy(() -> new DefaultGameState(null, spyGameStrategy))
                    .isNotNull()
                    .hasMessageContaining("null");
        }

        @DisplayName("Should throw if input strategy is null")
        @Test
        void shouldThrowIfInputStrategyIsNull() {
            assertThatThrownBy(() -> new DefaultGameState(mockMatrix, null))
                    .isNotNull()
                    .hasMessageContaining("null");
        }

        @DisplayName("Should create object if dependencies are correct")
        @Test
        void shouldCreateObjectIfDependenciesAreCorrect() {
            Exception result = catchException(() -> new DefaultGameState(mockMatrix, spyGameStrategy));
            assertThat(result)
                    .isNull();

        }
    }
}