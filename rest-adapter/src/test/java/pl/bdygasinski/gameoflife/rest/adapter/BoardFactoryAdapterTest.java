package pl.bdygasinski.gameoflife.rest.adapter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.bdygasinski.gameoflife.domain.GameStateFactory;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;

class BoardFactoryAdapterTest {

    @DisplayName("Should throw if dependency is null")
    @ParameterizedTest
    @MethodSource("dependencyProvider")
    void shouldThrowIfDependencyIsNull(GameStateFactory gameStateFactory, MatrixMapper matrixMapper) {
        // When
        var result = catchException(() -> new GameStateFactoryAdapter(gameStateFactory, matrixMapper));

        // Then
        assertThat(result)
                .isNotNull()
                .hasMessageContaining("null");
    }

    private static Stream<Arguments> dependencyProvider() {
        return Stream.of(
                Arguments.of(null, new MatrixMapper()),
                Arguments.of(new GameStateFactory(), null)
        );
    }
}