
package pl.bdygasinski.gameoflife.rest.dto;

import java.util.List;

public record GameStateDto(
        List<List<Boolean>> matrix
) {
}
