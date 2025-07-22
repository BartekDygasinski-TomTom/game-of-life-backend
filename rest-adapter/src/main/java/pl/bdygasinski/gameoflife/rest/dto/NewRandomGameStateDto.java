package pl.bdygasinski.gameoflife.rest.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Positive;

public record NewRandomGameStateDto(
        @Positive
        int rows,
        @Positive
        int columns,
        @DecimalMin(value = "0.0", inclusive = true)
        @DecimalMax(value = "1.0", inclusive = true)
        double alivePercentage
) {
}
