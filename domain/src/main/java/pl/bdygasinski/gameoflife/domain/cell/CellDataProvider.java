package pl.bdygasinski.gameoflife.domain.cell;

import lombok.NonNull;
import pl.bdygasinski.gameoflife.domain.matrix.MatrixDataProvider;

import java.util.Arrays;

public class CellDataProvider implements MatrixDataProvider<Cell[][]> {

    private final Cell[][] cells;

    public CellDataProvider(@NonNull Cell[][] cells) {
        this.cells = cells;
    }

    @Override
    public Cell[][] provide() {
        return cells;
    }

    @Override
    public Cell[][] clone(Cell[][] data) {
        int rows = data.length;
        Cell[][] newData = new Cell[rows][];

        for (int rowIndex = 0; rowIndex < rows; rowIndex++) {
            newData[rowIndex] = Arrays.copyOf(data[rowIndex], data[rowIndex].length);
        }

        return newData;
    }
}
