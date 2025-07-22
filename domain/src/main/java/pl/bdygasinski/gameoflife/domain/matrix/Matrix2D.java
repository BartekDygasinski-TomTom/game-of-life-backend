package pl.bdygasinski.gameoflife.domain.matrix;

import java.util.List;

public interface Matrix2D<T> {

    T getValueAt(Coordinate2D coordinate);

    T setValueAt(T value, Coordinate2D coordinate);

    int rowCount();

    int columnCount();

    List<T> toFlatList();

    List<Coordinate2D> getAvailableCoordinates();

    Matrix2D<T> copy();

    boolean containsCoordinate(Coordinate2D coordinate2D);
}
