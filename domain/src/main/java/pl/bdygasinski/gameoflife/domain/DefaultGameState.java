package pl.bdygasinski.gameoflife.domain;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import pl.bdygasinski.gameoflife.domain.cell.Cell;
import pl.bdygasinski.gameoflife.domain.cell.CellStats;
import pl.bdygasinski.gameoflife.domain.matrix.Coordinate2D;
import pl.bdygasinski.gameoflife.domain.matrix.Matrix2D;

import java.util.List;

@EqualsAndHashCode
@ToString
final class DefaultGameState implements GameState {

    private final Matrix2D<Cell> cellMatrix2D;
    private final GameStrategy cellTransitionStrategy;

    DefaultGameState(@NonNull Matrix2D<Cell> cellMatrix2D, @NonNull GameStrategy cellTransitionStrategy) {
        this.cellMatrix2D = cellMatrix2D;
        this.cellTransitionStrategy = cellTransitionStrategy;
    }

    @Override
    public GameState nextStep() {
        Matrix2D<Cell> newMatrix = cellMatrix2D.copy();

        for (Coordinate2D coordinate : cellMatrix2D.getAvailableCoordinates()) {
            Cell currentCell = cellMatrix2D.getValueAt(coordinate);
            CellStats cellStats = getCellStats(coordinate);
            Cell newCell = cellTransitionStrategy.applyCellTransition(currentCell, cellStats);
            newMatrix.setValueAt(newCell, coordinate);
        }

        return new DefaultGameState(newMatrix, cellTransitionStrategy);
    }

    @Override
    public Matrix2D<Cell> board() {
        return cellMatrix2D.copy();
    }



    private CellStats getCellStats(Coordinate2D coordinate) {
        List<Cell> neighborCells = getNeighborCells(coordinate);
        long liveNeighbors = liveNeighbors(neighborCells);
        return new CellStats(liveNeighbors);
    }

    private long liveNeighbors(List<Cell> neighborCells) {
        return neighborCells
                .stream()
                .filter(Cell.ALIVE::equals)
                .count();
    }

    private List<Cell> getNeighborCells(@NonNull Coordinate2D coordinate2D) {
        return coordinate2D
                .allNeighborCoordinates()
                .stream()
                .filter(cellMatrix2D::containsCoordinate)
                .map(cellMatrix2D::getValueAt)
                .toList();
    }
}
