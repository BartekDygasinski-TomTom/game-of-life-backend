package pl.bdygasinski.gameoflife.domain.matrix;

import pl.bdygasinski.gameoflife.domain.exception.InvalidBoardSizeException;

public record MatrixDimensions(int rows, int columns) {

    public MatrixDimensions {
        if (rows <= 0 || columns <= 0) {
            throw new InvalidBoardSizeException("Rows and columns must be > 0 but got rows=%s, columns=%s".formatted(rows, columns));
        }
    }
}
