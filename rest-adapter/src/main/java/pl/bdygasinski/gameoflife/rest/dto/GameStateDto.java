
package pl.bdygasinski.gameoflife.rest.dto;

import pl.bdygasinski.gameoflife.rest.validation.ValidMatrix;

import java.util.List;

public record GameStateDto(
        @ValidMatrix
        List<List<Boolean>> matrix
) {
}
