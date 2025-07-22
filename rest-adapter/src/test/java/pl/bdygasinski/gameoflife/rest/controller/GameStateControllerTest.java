package pl.bdygasinski.gameoflife.rest.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;

class GameStateControllerTest {

    @DisplayName("creation test")
    @Nested
    class CreationTest {

        @DisplayName("Should throw if BoardService is null")
        @Test
        void shouldThrow() {
            // When
            var result = catchException(() -> new GameStateController(null));

            // Then
            assertThat(result)
                    .isNotNull()
                    .hasMessageContaining("null");
        }
    }
}