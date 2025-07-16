package pl.bdygasinski.gameoflife.domain.matrix;

import pl.bdygasinski.gameoflife.domain.value.Coordinate2D;

import java.util.List;

public interface Matrix2D<T> {

    T getValueAt(Coordinate2D coordinate);

    T setValueAt(T value, Coordinate2D coordinate);

    int rowCount();

    int columnCount();

    List<T> toFlatList();

    Matrix2D<T> clone();
}
