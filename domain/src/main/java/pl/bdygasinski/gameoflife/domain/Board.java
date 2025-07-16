package pl.bdygasinski.gameoflife.domain;

import pl.bdygasinski.gameoflife.domain.matrix.Matrix2D;

public interface Board {

    Board nextStep();

    Matrix2D<Cell> cellMatrix2D();
}
