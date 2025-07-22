package pl.bdygasinski.gameoflife.domain.matrix;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import pl.bdygasinski.gameoflife.domain.exception.InvalidBoardSizeException;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.IntStream;

import static java.util.Objects.requireNonNull;

@EqualsAndHashCode
public final class FixedSizeArrayMatrix2D<T> implements Matrix2D<T> {

    private final MatrixDimensions matrixDimensions;
    private final MatrixDataProvider<T[][]> matrixDataProvider;
    private final T[][] matrix;

    public FixedSizeArrayMatrix2D(@NonNull MatrixDimensions matrixDimensions, @NonNull MatrixDataProvider<T[][]> matrixDataProvider) {
        this.matrixDataProvider = matrixDataProvider;
        this.matrixDimensions = matrixDimensions;
        this.matrix = matrixDataProvider.clone();

        validateMatrixAfterCreation();
    }

    @Override
    public T getValueAt(@NonNull Coordinate2D coordinate) {
        return matrix[coordinate.y()][coordinate.x()];
    }

    @Override
    public T setValueAt(@NonNull T value, @NonNull Coordinate2D coordinate) {
        matrix[coordinate.y()][coordinate.x()] = value;
        return value;
    }

    @Override
    public int rowCount() { return matrixDimensions.rows(); }

    @Override
    public int columnCount() { return matrixDimensions.columns(); }

    @Override
    public List<T> toFlatList() {
        return Arrays
                .stream(matrix)
                .flatMap(Arrays::stream)
                .toList();
    }

    @Override
    public List<Coordinate2D> getAvailableCoordinates() {
        return IntStream
                .range(0, rowCount())
                .boxed()
                .flatMap(row -> IntStream
                        .range(0, columnCount())
                        .mapToObj(col -> new Coordinate2D(col, row)))
                .toList();
    }

    @Override
    public Matrix2D<T> copy() {
        T[][] clonedMatrix = matrixDataProvider.clone();
        return new FixedSizeArrayMatrix2D<>(
                matrixDimensions,
                new MatrixDataProvider<>() {
                    @Override
                    public T[][] provide() {
                        return clonedMatrix;
                    }

                    @Override
                    public T[][] clone() {
                        return matrixDataProvider.clone();
                    }
                }
        );
    }

    @Override
    public boolean containsCoordinate(@NonNull Coordinate2D coordinate2D) {
        return coordinate2D.x() < columnCount() && coordinate2D.y() < rowCount();
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
}
