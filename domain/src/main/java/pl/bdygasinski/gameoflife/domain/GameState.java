package pl.bdygasinski.gameoflife.domain;

import pl.bdygasinski.gameoflife.domain.cell.Cell;
import pl.bdygasinski.gameoflife.domain.matrix.Matrix2D;

public interface GameState {

    GameState nextStep();

    Matrix2D<Cell> board();
}
