package pl.bdygasinski.gameoflife.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import pl.bdygasinski.gameoflife.domain.cell.Cell;
import pl.bdygasinski.gameoflife.domain.cell.CellStats;
import pl.bdygasinski.gameoflife.domain.matrix.Coordinate2D;
import pl.bdygasinski.gameoflife.domain.matrix.Matrix2D;
import pl.bdygasinski.gameoflife.domain.matrix.Matrix2DView;

@Value
@Getter(AccessLevel.NONE)
class DefaultGameState implements GameState {

    @NonNull Matrix2D<Cell> cellMatrix2D;
    @NonNull GameStrategy cellTransitionStrategy;

    @Override
    public @NonNull GameState nextStep() {
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
    public @NonNull Matrix2DView<Cell> board() {
        return cellMatrix2D;
    }



    private CellStats getCellStats(Coordinate2D coordinate) {
        long liveNeighbors = coordinate.
                allNeighborCoordinates()
                .stream()
                .filter(cellMatrix2D::containsCoordinate)
                .map(cellMatrix2D::getValueAt)
                .filter(Cell.ALIVE::equals)
                .count();
        return new CellStats(liveNeighbors);
    }
}
