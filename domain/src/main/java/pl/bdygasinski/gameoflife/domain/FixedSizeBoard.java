package pl.bdygasinski.gameoflife.domain;

import pl.bdygasinski.gameoflife.domain.matrix.Coordinate2D;
import pl.bdygasinski.gameoflife.domain.matrix.Matrix2D;

import java.util.List;

import static java.util.Objects.isNull;

record FixedSizeBoard(Matrix2D<Cell> cellMatrix2D, GameStrategy cellTransitionStrategy) implements Board {

    public FixedSizeBoard {
        if (isNull(cellMatrix2D))
            throw new IllegalArgumentException("Cell matrix cannot be null");

        if (isNull(cellTransitionStrategy)) {
            throw new IllegalArgumentException("Cell transition strategy cannot be null");
        }
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


        return new FixedSizeBoard(newMatrix, cellTransitionStrategy);
    }

    List<Cell> getNeighborCells(Coordinate2D coordinate2D) {
        return coordinate2D
                .allNeighborCoordinates()
                .stream()
                .filter(cellMatrix2D::containsCoordinate)
                .map(cellMatrix2D::getValueAt)
                .toList();
    }
}
