package pl.bdygasinski.gameoflife.rest.adapter;

import org.springframework.stereotype.Component;
import pl.bdygasinski.gameoflife.domain.cell.Cell;
import pl.bdygasinski.gameoflife.domain.cell.CellDataProvider;
import pl.bdygasinski.gameoflife.domain.matrix.*;
import pl.bdygasinski.gameoflife.rest.dto.GameStateDto;

import java.util.List;
import java.util.stream.IntStream;

@Component
class MatrixMapper {

    Matrix2D<Cell> toDomain(GameStateDto dto) {
        List<List<Boolean>> matrix = dto.matrix();
        int colCount = matrix.getFirst().size();

        Cell[][] cells = matrix
                .stream()
                .map(row -> IntStream
                        .range(0, colCount)
                        .mapToObj(x -> row.get(x) ? Cell.ALIVE : Cell.DEAD)
                        .toArray(Cell[]::new))
                .toArray(Cell[][]::new);

        MatrixDataProvider<Cell[][]> cellDataProvider = new CellDataProvider(cells);
        MatrixDimensions matrixDimensions = new MatrixDimensions(cells.length, colCount);

        return new FixedSizeArrayMatrix2D<>(matrixDimensions, cellDataProvider);
    }

    GameStateDto toDto(Matrix2D<Cell> matrix) {
        var rows = IntStream
                .range(0, matrix.rowCount())
                .mapToObj(row -> IntStream
                        .range(0, matrix.columnCount())
                        .mapToObj(col -> matrix.getValueAt(new Coordinate2D(col, row)) == Cell.ALIVE)
                        .toList())
                .toList();

        return new GameStateDto(rows);
    }
}