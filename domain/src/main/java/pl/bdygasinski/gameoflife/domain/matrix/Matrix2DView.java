package pl.bdygasinski.gameoflife.domain.matrix;

import lombok.NonNull;

import java.util.List;

public interface Matrix2DView<T> {

    @NonNull T getValueAt(@NonNull Coordinate2D coordinate);

    @NonNull MatrixDimensions getDimensions();

    @NonNull List<T> toFlatList();

    @NonNull List<Coordinate2D> getAvailableCoordinates();

    boolean containsCoordinate(@NonNull Coordinate2D coordinate2D);
}
