package pl.bdygasinski.gameoflife.domain;

import lombok.NonNull;
import pl.bdygasinski.gameoflife.domain.cell.Cell;
import pl.bdygasinski.gameoflife.domain.cell.CellDataProvider;
import pl.bdygasinski.gameoflife.domain.matrix.Coordinate2D;
import pl.bdygasinski.gameoflife.domain.matrix.FixedSizeArrayMatrix2D;
import pl.bdygasinski.gameoflife.domain.matrix.Matrix2D;
import pl.bdygasinski.gameoflife.domain.matrix.MatrixDimensions;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GameStateFactory {

    public GameState randomGameStateWithAlivePercentage(@NonNull MatrixDimensions dimensions, double alivePercentage, @NonNull GameStrategy strategy) {
        if (alivePercentage < 0 || alivePercentage > 1) {
            throw new IllegalArgumentException("Alive percentage must be between 0 and 1 but was %s".formatted(alivePercentage));
        }

        int rows = dimensions.rows();
        int cols = dimensions.columns();

        int totalCells = rows * cols;
        int aliveCount = (int) Math.round(alivePercentage * totalCells);

        List<Coordinate2D> allCoordinates = generateAllCoordinates(rows, cols);
        Set<Coordinate2D> coordinatesMarkedForRevival = pickRandomCoordinatesSubset(allCoordinates, aliveCount);
        CellDataProvider cellDataProvider = buildMatrix(rows, cols, coordinatesMarkedForRevival);

        Matrix2D<Cell> matrix2D = new FixedSizeArrayMatrix2D<>(dimensions, cellDataProvider);
        return new DefaultGameState(matrix2D, strategy);
    }

    public GameState fromBaseState(@NonNull Matrix2D<Cell> baseState, @NonNull GameStrategy gameStrategy) {
        return new DefaultGameState(baseState, gameStrategy);
    }



    private List<Coordinate2D> generateAllCoordinates(int rows, int cols) {
        return IntStream
                .range(0, rows)
                .boxed()
                .flatMap(row -> IntStream
                        .range(0, cols)
                        .mapToObj(col -> new Coordinate2D(col, row)))
                .collect(Collectors.toList());
    }

    private Set<Coordinate2D> pickRandomCoordinatesSubset(List<Coordinate2D> allCoords, int aliveCount) {
        Collections.shuffle(allCoords);
        return Set.copyOf(allCoords.subList(0, aliveCount));
    }

    private CellDataProvider buildMatrix(int rows, int cols, Set<Coordinate2D> aliveCoords) {
        Cell[][] matrix = new Cell[rows][cols];

        generateAllCoordinates(rows, cols)
                .forEach(coord ->
                        matrix[coord.y()][coord.x()] = aliveCoords.contains(coord) ? Cell.ALIVE : Cell.DEAD);

        return new CellDataProvider(matrix);
    }
}
