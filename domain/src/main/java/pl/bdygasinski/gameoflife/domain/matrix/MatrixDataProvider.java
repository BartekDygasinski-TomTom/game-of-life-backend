package pl.bdygasinski.gameoflife.domain.matrix;

public interface MatrixDataProvider<T> {

    T provide();

    T clone();
}
