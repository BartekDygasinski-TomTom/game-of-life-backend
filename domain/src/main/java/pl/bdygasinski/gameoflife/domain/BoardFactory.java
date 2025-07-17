package pl.bdygasinski.gameoflife.domain;

import pl.bdygasinski.gameoflife.domain.matrix.ArrayMatrix2D;
import pl.bdygasinski.gameoflife.domain.matrix.Coordinate2D;
import pl.bdygasinski.gameoflife.domain.matrix.Matrix2D;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BoardFactory {

    public Board randomBoardWithAlivePercentage(int rows, int cols, double alivePercentage, GameStrategy strategy) {
        if (alivePercentage < 0 || alivePercentage > 1) {
            throw new IllegalArgumentException("Alive percentage must be between 0 and 1 but was %s".formatted(alivePercentage));
        }

        int totalCells = rows * cols;
        int aliveCount = (int) Math.round(alivePercentage * totalCells);

        List<Coordinate2D> allCoordinates = generateAllCoordinates(rows, cols);
        Set<Coordinate2D> coordinatesMarkedForRevival = pickRandomCoordinatesSubset(allCoordinates, aliveCount);
        Cell[][] cells = buildMatrix(rows, cols, coordinatesMarkedForRevival);

        return new FixedSizeBoard(new ArrayMatrix2D<>(cells), strategy);
    }

    public Board fromBaseState(Matrix2D<Cell> baseState, GameStrategy gameStrategy) {
        return new FixedSizeBoard(baseState, gameStrategy);
    }

    List<Coordinate2D> generateAllCoordinates(int rows, int cols) {
        return IntStream
                .range(0, rows * cols)
                .mapToObj(i -> new Coordinate2D(i % cols, i / cols))
                .collect(Collectors.toList());
    }

    Set<Coordinate2D> pickRandomCoordinatesSubset(List<Coordinate2D> allCoords, int count) {
        if (count < 0) count = 0;
        if (count > allCoords.size()) count = allCoords.size();

        Collections.shuffle(allCoords);
        return Set.copyOf(allCoords.subList(0, count));
    }

    Cell[][] buildMatrix(int rows, int cols, Set<Coordinate2D> aliveCoords) {
        Cell[][] matrix = new Cell[rows][cols];

        IntStream.range(0, rows * cols).forEach(i -> {
            int y = i / cols;
            int x = i % cols;
            Coordinate2D coord = new Coordinate2D(x, y);
            matrix[y][x] = aliveCoords.contains(coord) ? Cell.ALIVE : Cell.DEAD;
        });

        return matrix;
    }
}
