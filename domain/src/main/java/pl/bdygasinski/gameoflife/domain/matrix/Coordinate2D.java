package pl.bdygasinski.gameoflife.domain.matrix;

import pl.bdygasinski.gameoflife.domain.exception.InvalidCoordinateException;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public record Coordinate2D(int x, int y) {

    public Coordinate2D {
        if (x < 0) {
            throw new InvalidCoordinateException("X must be >= 0 but was %s".formatted(x));
        }

        if (y < 0) {
            throw new InvalidCoordinateException("Y must be >= 0 but was %s".formatted(y));
        }
    }

    public Optional<Coordinate2D> offset(int deltaX, int deltaY) {
        try {
            return Optional.of(new Coordinate2D(x + deltaX, y + deltaY));

        } catch (InvalidCoordinateException e) {
            return Optional.empty();
        }
    }

    public Optional<Coordinate2D> up() {
        return offset(0, -1);
    }

    public Optional<Coordinate2D> down()  {
        return offset(0, 1);
    }

    public Optional<Coordinate2D> left()  {
        return offset(-1, 0);
    }

    public Optional<Coordinate2D> right() {
        return offset(1, 0);
    }

    public Optional<Coordinate2D> upLeft()    {
        return offset(-1, -1);
    }

    public Optional<Coordinate2D> upRight()   {
        return offset(1, -1);
    }

    public Optional<Coordinate2D> downLeft()  {
        return offset(-1, 1);
    }

    public Optional<Coordinate2D> downRight() {
        return offset(1, 1);
    }

    public List<Coordinate2D> allNeighborCoordinates() {
        List<Supplier<Optional<Coordinate2D>>> directionSuppliers = List.of(
                this::up, this::down, this::left, this::right,
                this::upLeft, this::upRight, this::downLeft, this::downRight
        );

        return directionSuppliers
                .stream()
                .map(Supplier::get)
                .flatMap(Optional::stream)
                .toList();

    }
}
