package pl.bdygasinski.gameoflife.domain;

import pl.bdygasinski.gameoflife.domain.matrix.Coordinate2D;
import pl.bdygasinski.gameoflife.domain.matrix.Matrix2D;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

import static java.util.Objects.isNull;

final class DefaultBoard implements Board {

    private final Matrix2D<Cell> cellMatrix2D;
    private final GameStrategy cellTransitionStrategy;

    public DefaultBoard(Matrix2D<Cell> cellMatrix2D, GameStrategy cellTransitionStrategy) {
        if (isNull(cellMatrix2D))
            throw new IllegalArgumentException("Cell matrix cannot be null");

        if (isNull(cellTransitionStrategy)) {
            throw new IllegalArgumentException("Cell transition strategy cannot be null");
        }

        this.cellMatrix2D = cellMatrix2D;
        this.cellTransitionStrategy = cellTransitionStrategy;
    }

    @Override
    public Board nextStep() {
        Matrix2D<Cell> newMatrix = cellMatrix2D.clone();

        for (var coordinate : cellMatrix2D.getAvailableCoordinates()) {
            Cell currentCell = cellMatrix2D.getValueAt(coordinate);
            var neighborCells = getNeighborCells(coordinate);
            Cell newCell = cellTransitionStrategy.applyCellTransition(currentCell, neighborCells);
            newMatrix.setValueAt(newCell, coordinate);
        }


        return new DefaultBoard(newMatrix, cellTransitionStrategy);
    }

    @Override
    public Matrix2D<Cell> cellMatrix2D() {
        return cellMatrix2D.clone();
    }

    List<Cell> getNeighborCells(Coordinate2D coordinate2D) {
        return coordinate2D
                .allNeighborCoordinates()
                .stream()
                .filter(cellMatrix2D::containsCoordinate)
                .map(cellMatrix2D::getValueAt)
                .toList();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof DefaultBoard that)) return false;
        return Objects.equals(cellMatrix2D, that.cellMatrix2D) && Objects.equals(cellTransitionStrategy, that.cellTransitionStrategy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cellMatrix2D, cellTransitionStrategy);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", DefaultBoard.class.getSimpleName() + "[", "]")
                .add("cellMatrix2D=" + cellMatrix2D)
                .add("cellTransitionStrategy=" + cellTransitionStrategy)
                .toString();
    }
}
