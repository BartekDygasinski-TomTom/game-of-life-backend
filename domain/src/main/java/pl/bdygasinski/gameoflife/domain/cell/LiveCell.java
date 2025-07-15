package pl.bdygasinski.gameoflife.domain.cell;

import pl.bdygasinski.gameoflife.domain.exception.InvalidCoordinateException;
import pl.bdygasinski.gameoflife.domain.value.Coordinate2D;

import static java.util.Objects.isNull;

public record LiveCell(Coordinate2D coordinate2D) implements Cell {

    public LiveCell {
        if (isNull(coordinate2D)) {
            throw new InvalidCoordinateException("Coordinate must not be null");
        }
    }

    public DeadCell die() {
        return new DeadCell(coordinate2D);
    }
}
