package pl.bdygasinski.gameoflife.domain.matrix;

import lombok.NonNull;

public interface Matrix2D<T> extends Matrix2DView<T> {

    @NonNull T setValueAt(@NonNull T value, @NonNull Coordinate2D coordinate);

    @NonNull Matrix2D<T> copy();
}
