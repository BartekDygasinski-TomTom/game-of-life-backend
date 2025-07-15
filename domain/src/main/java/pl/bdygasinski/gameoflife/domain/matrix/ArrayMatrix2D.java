package pl.bdygasinski.gameoflife.domain.matrix;

import pl.bdygasinski.gameoflife.domain.exception.InvalidCoordinateException;
import pl.bdygasinski.gameoflife.domain.value.Coordinate2D;

import java.util.*;

import static java.util.Objects.isNull;

final class ArrayMatrix2D<T> implements Matrix2D<T> {

    private final T[][] matrix;

    public ArrayMatrix2D(T[][] input) {
        if (isNull(input)) throw new IllegalArgumentException("Matrix cannot be null");
        String inputAsString = Arrays.toString(input);

        var rowCount = input.length;
        if (rowCount == 0)
            throw new IllegalArgumentException("Matrix must have at least one row but got %s".formatted(inputAsString));

        var columnCount = input[0] != null ? input[0].length : -1;
        if (columnCount == 0) {
            throw new IllegalArgumentException("Matrix must have at least one column but got %s".formatted(inputAsString));
        }

        validateIfMatrixContainNulls(input, columnCount);

        this.matrix = deepCopy(input, rowCount, columnCount);
    }

    @Override
    public T getValueAt(Coordinate2D coordinate) {
        validateCoordinate(coordinate);
        return matrix[coordinate.y()][coordinate.x()];
    }

    @Override
    public T setValueAt(T value, Coordinate2D coordinate) {
        validateCoordinate(coordinate);

        if (isNull(value)) {
            throw new IllegalArgumentException("Value must not contain null");
        }

        matrix[coordinate.y()][coordinate.x()] = value;
        return value;
    }

    @Override
    public int rowCount() { return matrix.length; }

    @Override
    public int columnCount() { return matrix[0].length; }

    @Override
    public List<T> toFlatList() {
        return Arrays.stream(matrix)
                .flatMap(Arrays::stream)
                .toList();
    }



    private void validateCoordinate(Coordinate2D coordinate) {
        if (isNull(coordinate)) {
            throw new InvalidCoordinateException("Provided coordinate must not be null");
        }
    }

    private void validateIfMatrixContainNulls(T[][] input, int expectedCols) {
        String inputAsString = Arrays.toString(input);

        for (T[] row : input) {
            if (isNull(row))
                throw new IllegalArgumentException("Matrix row cannot be null but got %s".formatted(inputAsString));
            if (row.length != expectedCols)
                throw new IllegalArgumentException("All rows must have the same length but got %s".formatted(inputAsString));
            for (T cell : row) {
                if (cell == null)
                    throw new IllegalArgumentException("Matrix cell cannot be null");
            }
        }
    }

    @SuppressWarnings("unchecked")
    private T[][] deepCopy(T[][] original, int rows, int cols) {
        T[][] copy = (T[][]) new Object[rows][cols]; // unavoidable
        for (int i = 0; i < rows; i++) {
            copy[i] = Arrays.copyOf(original[i], cols);
        }
        return copy;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ArrayMatrix2D<?> matrix2D)) return false;
        return Objects.deepEquals(matrix, matrix2D.matrix);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(matrix);
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
        return joiner.toString() + lineSeparator;
    }
}
