package pl.bdygasinski.gameoflife.domain;

import lombok.NonNull;
import pl.bdygasinski.gameoflife.domain.cell.Cell;
import pl.bdygasinski.gameoflife.domain.matrix.Matrix2DView;

public interface GameState {

    @NonNull GameState nextStep();

    @NonNull Matrix2DView<Cell> board();
}
