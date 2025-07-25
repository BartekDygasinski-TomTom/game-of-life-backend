package pl.bdygasinski.gameoflife.domain.matrix;

import lombok.Value;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Value
public class Coordinate2D {

    int x;
    int y;

    private Coordinate2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Optional<Coordinate2D> from(int x, int y) {
        if (x < 0 || y < 0) {
            return Optional.empty();
        }

        return Optional.of(new Coordinate2D(x, y));
    }

    public Optional<Coordinate2D> offset(int deltaX, int deltaY) {
        return Coordinate2D.from(x + deltaX, y + deltaY);
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
        return Stream.of(up(), down(), left(), right(), upLeft(), upRight(), downLeft(), downRight())
                .flatMap(Optional::stream)
                .toList();
    }
}
