package pl.bdygasinski.gameoflife.domain.cell;

import pl.bdygasinski.gameoflife.domain.exception.InvalidCoordinateException;
import pl.bdygasinski.gameoflife.domain.value.Coordinate2D;

import static java.util.Objects.isNull;

public record DeadCell(Coordinate2D coordinate2D) implements Cell {

    public DeadCell {
        if (isNull(coordinate2D)) {
            throw new InvalidCoordinateException("Coordinate must not be null");
        }
    }

    public LiveCell resurrect() {
        return new LiveCell(coordinate2D);
    }
}
