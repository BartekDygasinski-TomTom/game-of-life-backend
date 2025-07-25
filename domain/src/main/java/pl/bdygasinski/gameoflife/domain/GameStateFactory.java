package pl.bdygasinski.gameoflife.domain;

import lombok.NonNull;
import pl.bdygasinski.gameoflife.domain.cell.Cell;
import pl.bdygasinski.gameoflife.domain.matrix.Coordinate2D;
import pl.bdygasinski.gameoflife.domain.matrix.FixedSizeArrayMatrix2D;
import pl.bdygasinski.gameoflife.domain.matrix.Matrix2D;
import pl.bdygasinski.gameoflife.domain.matrix.MatrixDimensions;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class GameStateFactory {

    public @NonNull GameState randomGameStateWithAlivePercentage(@NonNull MatrixDimensions dimensions, double alivePercentage, @NonNull GameStrategy strategy) {
        if (alivePercentage < 0 || alivePercentage > 1) {
            throw new IllegalArgumentException("Alive percentage must be between 0 and 1 but was %s".formatted(alivePercentage));
        }

        int aliveCount = (int) Math.round(alivePercentage * dimensions.getTotalCells());

        List<Coordinate2D> allCoordinates = dimensions.generateAllCoordinates();
        Set<Coordinate2D> coordinatesMarkedForRevival = pickRandomCoordinatesSubset(allCoordinates, aliveCount);

        Iterable<Cell> items = allCoordinates
                .stream()
                .map(coord -> coordinatesMarkedForRevival.contains(coord) ? Cell.ALIVE : Cell.DEAD)
                .toList();

        Matrix2D<Cell> matrix2D = new FixedSizeArrayMatrix2D<>(dimensions, items, Cell.class);
        return new DefaultGameState(matrix2D, strategy);
    }

    public @NonNull GameState fromBaseState(@NonNull Matrix2D<Cell> baseState, @NonNull GameStrategy gameStrategy) {
        return new DefaultGameState(baseState, gameStrategy);
    }

    private Set<Coordinate2D> pickRandomCoordinatesSubset(List<Coordinate2D> allCoords, int aliveCount) {
        Collections.shuffle(allCoords);
        return Set.copyOf(allCoords.subList(0, aliveCount));
    }
}
