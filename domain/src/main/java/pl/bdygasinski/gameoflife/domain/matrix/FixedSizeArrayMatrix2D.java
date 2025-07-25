package pl.bdygasinski.gameoflife.domain.matrix;

import lombok.NonNull;
import lombok.Value;
import pl.bdygasinski.gameoflife.domain.exception.InvalidBoardSizeException;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.StringJoiner;

import static java.util.Objects.requireNonNull;

@Value
public class FixedSizeArrayMatrix2D<T> implements Matrix2D<T> {

    MatrixDimensions matrixDimensions;
    T[][] matrix;
    Class<T> type;

    public FixedSizeArrayMatrix2D(@NonNull MatrixDimensions matrixDimensions, @NonNull Iterable<T> items, Class<T> type) {
        this.matrixDimensions = matrixDimensions;
        this.type = type;
        this.matrix = createArrayFromIterable(items, matrixDimensions, type);

        validateMatrixAfterCreation();
    }

    private FixedSizeArrayMatrix2D(FixedSizeArrayMatrix2D<T> original) {
        this.matrixDimensions = original.matrixDimensions;
        this.type = original.type;
        this.matrix = deepCopy(original.matrix);
    }

    @Override
    public @NonNull T getValueAt(@NonNull Coordinate2D coordinate) {
        return matrix[coordinate.getY()][coordinate.getX()];
    }

    @Override
    public @NonNull MatrixDimensions getDimensions() {
        return matrixDimensions;
    }

    @Override
    public @NonNull T setValueAt(@NonNull T value, @NonNull Coordinate2D coordinate) {
        matrix[coordinate.getY()][coordinate.getX()] = value;
        return value;
    }

    @Override
    public @NonNull List<T> toFlatList() {
        return Arrays
                .stream(matrix)
                .flatMap(Arrays::stream)
                .toList();
    }

    @Override
    public @NonNull List<Coordinate2D> getAvailableCoordinates() {
        return matrixDimensions.generateAllCoordinates();
    }

    @Override
    public @NonNull Matrix2D<T> copy() {
        return new FixedSizeArrayMatrix2D<>(this);
    }

    @Override
    public boolean containsCoordinate(@NonNull Coordinate2D coordinate2D) {
        return coordinate2D.getX() < matrixDimensions.columns() && coordinate2D.getY() < matrixDimensions.rows();
    }

    @Override
    public String toString() {
        String lineSeparator = System.lineSeparator();
        int rowSize = prepareRow(matrix[0], lineSeparator).length();
        String horizontalBorder = "-".repeat(rowSize - 1) + lineSeparator;

        StringJoiner matrixString = new StringJoiner(horizontalBorder, horizontalBorder, horizontalBorder);

        for (T[] row : matrix) {
            String rowString = prepareRow(row, lineSeparator);
            matrixString.add(rowString);
        }

        return matrixString.toString();
    }



    private String prepareRow(T[] row, String lineSeparator) {
        StringJoiner joiner = new StringJoiner(" | ", "| ", " |");
        for (T cell : row) {
            joiner.add(String.valueOf(cell));
        }
        return joiner + lineSeparator;
    }

    private void validateMatrixAfterCreation() {
        requireNonNull(matrix, "Matrix can't be null");

        if (matrix.length != matrixDimensions.rows()) {
            throw new InvalidBoardSizeException("All rows must have the same length but got %s".formatted(matrixDimensions));
        }

        for (T[] row : matrix) {
            requireNonNull(row, "Row can't be null");
            if (row.length != matrixDimensions.columns())
                throw new InvalidBoardSizeException("All rows must have the same length but got %s".formatted(matrixDimensions));
            for (T cell : row) {
                requireNonNull(cell, "Cell can't be null");
            }
        }
    }

    @SuppressWarnings("unchecked")
    private T[][] deepCopy(T[][] original) {
        T[][] copy = (T[][]) Array.newInstance(type, matrixDimensions.rows(), matrixDimensions.columns());
        for (int i = 0; i < matrixDimensions.rows(); i++) {
            copy[i] = original[i].clone();
        }
        return copy;
    }

    @SuppressWarnings("unchecked")
    private static <T> T[][] createArrayFromIterable(Iterable<T> iterable, MatrixDimensions matrixDimensions, Class<T> type) {
        T[][] copy = (T[][]) Array.newInstance(type, matrixDimensions.rows(), matrixDimensions.columns());
        Iterator<T> iterator = iterable.iterator();

        for (int rowIndex = 0; rowIndex < matrixDimensions.rows(); rowIndex++) {
            for (int colIndex = 0; colIndex < matrixDimensions.columns(); colIndex++) {
                copy[rowIndex][colIndex] = iterator.hasNext() ? iterator.next() : null;
            }
        }
        return copy;
    }
}
