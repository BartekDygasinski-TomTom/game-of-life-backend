package pl.bdygasinski.gameoflife.domain.matrix;

import lombok.NonNull;
import pl.bdygasinski.gameoflife.domain.exception.InvalidBoardSizeException;
import pl.bdygasinski.gameoflife.domain.exception.InvalidCoordinateException;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public record MatrixDimensions(int rows, int columns) {

    public MatrixDimensions {
        if (rows <= 0 || columns <= 0) {
            throw new InvalidBoardSizeException("Rows and columns must be > 0 but got rows=%s, columns=%s".formatted(rows, columns));
        }
    }

    public int getTotalCells() {
        return rows * columns;
    }

    public @NonNull List<Coordinate2D> generateAllCoordinates() {
        return IntStream
                .range(0, rows)
                .boxed()
                .flatMap(row -> IntStream
                        .range(0, columns)
                        .mapToObj(col -> Coordinate2D.from(col, row)
                                .orElseThrow(() -> InvalidCoordinateException.withInvalidCoord(col, row))))
                .collect(Collectors.toList());
    }
}
