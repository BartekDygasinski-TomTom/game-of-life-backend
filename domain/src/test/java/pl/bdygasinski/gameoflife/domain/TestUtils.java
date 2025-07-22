package pl.bdygasinski.gameoflife.domain;

import pl.bdygasinski.gameoflife.domain.matrix.*;

import java.util.Arrays;

public class TestUtils {

    public static Matrix2D<Integer> matrixFromData(Integer[][] data) {
        var givenMatrixDimensions = new MatrixDimensions(data.length, data[0].length);
        var givenMatrixDataProvider = new IntegerMatrixProvider(data);

        return new FixedSizeArrayMatrix2D<>(givenMatrixDimensions, givenMatrixDataProvider);
    }

    public static Matrix2D<Coordinate2D> matrixFromData(Coordinate2D[][] data) {
        var givenMatrixDimensions = new MatrixDimensions(data.length, data[0].length);
        var givenMatrixDataProvider = new CoordinateMatrixProvider(data);

        return new FixedSizeArrayMatrix2D<>(givenMatrixDimensions, givenMatrixDataProvider);
    }

    public static class CoordinateMatrixProvider implements MatrixDataProvider<Coordinate2D[][]> {

        private final Coordinate2D[][] data;

        public CoordinateMatrixProvider(Coordinate2D[][] data) {
            this.data = data;
        }

        @Override
        public Coordinate2D[][] provide() {
            return data;
        }

        @Override
        public Coordinate2D[][] clone() {
            Coordinate2D[][] newData = new Coordinate2D[data.length][];
            for (int rowIndex = 0; rowIndex < data.length; rowIndex++) {
                newData[rowIndex] = Arrays.copyOf(data[rowIndex], data[rowIndex].length);
            }

            return newData;
        }
    };

    public static class IntegerMatrixProvider implements MatrixDataProvider<Integer[][]> {

        private final Integer[][] data;

        public IntegerMatrixProvider(Integer[][] data) {
            this.data = data;
        }

        @Override
        public Integer[][] provide() {
            return data;
        }

        @Override
        public Integer[][] clone() {
            Integer[][] newData = new Integer[data.length][];
            for (int rowIndex = 0; rowIndex < data.length; rowIndex++) {
                newData[rowIndex] = Arrays.copyOf(data[rowIndex], data[rowIndex].length);
            }

            return newData;
        }
    }
}
