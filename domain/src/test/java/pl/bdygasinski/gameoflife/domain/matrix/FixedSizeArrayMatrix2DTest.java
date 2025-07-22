package pl.bdygasinski.gameoflife.domain.matrix;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.bdygasinski.gameoflife.domain.TestUtils.CoordinateMatrixProvider;
import pl.bdygasinski.gameoflife.domain.TestUtils.IntegerMatrixProvider;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;
import static pl.bdygasinski.gameoflife.domain.TestUtils.matrixFromData;

class FixedSizeArrayMatrix2DTest {

    private final Coordinate2D[][] matrixData = {
            {new Coordinate2D(0, 0), new Coordinate2D(1, 0), new Coordinate2D(2, 0)},
            {new Coordinate2D(0, 1), new Coordinate2D(1, 1), new Coordinate2D(2, 1)},
            {new Coordinate2D(0, 2), new Coordinate2D(1, 2), new Coordinate2D(2, 2)},
    };
    private final MatrixDimensions givenMatrixDimensions =
            new MatrixDimensions(matrixData.length, matrixData[0].length);

    private final MatrixDataProvider<Coordinate2D[][]> givenMatrixDataProvider =
            new CoordinateMatrixProvider(matrixData);

    private final FixedSizeArrayMatrix2D<Coordinate2D> underTest =
            new FixedSizeArrayMatrix2D<>(givenMatrixDimensions, givenMatrixDataProvider);

    @DisplayName("creation tests")
    @Nested
    class CreationTest {

        private static Stream<Arguments> provideNullDependencies() {
            return Stream.of(
                    Arguments.of(null, new CoordinateMatrixProvider(new Coordinate2D[][]{
                            {new Coordinate2D(0, 0)}
                    })),
                    Arguments.of(new MatrixDimensions(1, 1), null)
            );
        }

        private static Stream<Integer[][]> provideDataWithNullValues() {
            return Stream.of(
                    new Integer[][]{
                            null,
                            {1, 2, 3}
                    },
                    new Integer[][]{
                            {1, 2, 3},
                            null
                    },
                    new Integer[][]{
                            {1, 2, null},
                            {1, 2, 3}
                    },
                    new Integer[][] {
                            {1, 2, 3},
                            {1, null, 3}
                    }
            );
        }

        private static Stream<Arguments> provideDataWithIncorrectDimensions() {
            return Stream.of(
                    Arguments.of(new MatrixDimensions(1, 1), new Integer[][]{{}}),
                    Arguments.of(new MatrixDimensions(1, 1), new Integer[][]{
                            {1, 2}
                    }),
                    Arguments.of(new MatrixDimensions(1, 1), new Integer[][]{
                            {1}, {2}
                    }),
                    Arguments.of(new MatrixDimensions(2, 2), new Integer[][]{
                            {1, 2},
                            {1}
                    }),
                    Arguments.of(new MatrixDimensions(2, 2), new Integer[][]{
                            {1, 2},
                            {1, 2, 3}
                    })
            );
        }

        @DisplayName("Should throw if input is null")
        @ParameterizedTest
        @MethodSource("provideNullDependencies")
        void shouldThrowIfInputIsNull(MatrixDimensions matrixDimensions, MatrixDataProvider<Coordinate2D[][]> dataProvider) {
            // When
            var result = catchException(() -> new FixedSizeArrayMatrix2D<>(matrixDimensions, dataProvider));

            // Then
            assertThat(result)
                    .isNotNull()
                    .hasMessageContaining("null");
        }

        @DisplayName("Should throw if any row or column is null")
        @ParameterizedTest
        @MethodSource("provideDataWithNullValues")
        void shouldThrowIfMatrixContainsNull(Integer[][] matrixData) {
            // Given
            var givenDimensions = new MatrixDimensions(2, 3);
            var givenDataProvider = new IntegerMatrixProvider(matrixData);

            // When
            var result = catchException(() -> new FixedSizeArrayMatrix2D<>(givenDimensions, givenDataProvider));

            // Then
            assertThat(result)
                    .isNotNull()
                    .hasMessageContaining("null");
        }

        @DisplayName("Should throw if all dimensions of input data are not same as declared dimensions")
        @ParameterizedTest
        @MethodSource("provideDataWithIncorrectDimensions")
        void shouldThrowIfMatrixContainsDataWithWrongDimensions(MatrixDimensions matrixDimensions, Integer[][] values) {
            // Given
            var givenDataProvider = new IntegerMatrixProvider(values);

            // When
            var result = catchException(() -> new FixedSizeArrayMatrix2D<>(matrixDimensions, givenDataProvider));

            // Then
            assertThat(result)
                    .isNotNull()
                    .hasMessageContaining(String.valueOf(matrixDimensions.rows()))
                    .hasMessageContaining(String.valueOf(matrixDimensions.columns()));
        }
    }

    @DisplayName("getValueAt()")
    @Nested
    class GetValueAtTest {

        @DisplayName("Should throw if input coordinate is null")
        @Test
        void shouldThrowIfInputIsNull() {
            // When
            var result = catchException(() -> underTest.getValueAt(null));

            // Then
            assertThat(result)
                    .isNotNull()
                    .hasMessageContaining("null");
        }

        @DisplayName("Should give item at given position")
        @ParameterizedTest
        @MethodSource("coordinateProvider")
        void shouldGiveItemAtGivenPosition(int x, int y) {
            // Given
            var givenCoordinate = new Coordinate2D(x, y);

            // When
            var result = underTest.getValueAt(givenCoordinate);

            // Then
            assertThat(result.x())
                    .isEqualTo(x);

            assertThat(result.y())
                    .isEqualTo(y);
        }


        static Stream<Arguments> coordinateProvider() {
            return IntStream.range(0, 3) // rows (y)
                    .boxed()
                    .flatMap(y -> IntStream.range(0, 3) // cols (x)
                            .mapToObj(x -> Arguments.of(x, y)));
        }
    }

    @DisplayName("setValueAt()")
    @Nested
    class SetValueAtTest {

        @DisplayName("Should throw if input coordinate is null")
        @Test
        void shouldThrowIfInputIsNull() {
            // Given
            var givenCorrectValue = new Coordinate2D(1, 1);

            // When
            var result = catchException(() -> underTest.setValueAt(givenCorrectValue, null));

            // Then
            assertThat(result)
                    .isNotNull()
                    .hasMessageContaining("null");
        }

        @DisplayName("Should throw if input value is null")
        @Test
        void shouldThrowIfInputValueIsNull() {
            // Given
            var givenCorrectCoordinate = new Coordinate2D(1, 1);

            // When
            var result = catchException(() -> underTest.setValueAt(null, givenCorrectCoordinate));

            // Then
            assertThat(result)
                    .isNotNull()
                    .hasMessageContaining("null");
        }

        @DisplayName("Should set item at given position")
        @ParameterizedTest
        @MethodSource("coordinateProvider")
        void shouldGiveItemAtGivenPosition(int x, int y) {
            // Given
            var givenCoordinate = new Coordinate2D(x, y);
            var givenValue = new Coordinate2D(10, 10);
            var preSetItem = underTest.getValueAt(givenCoordinate);

            // When
            var result = underTest.setValueAt(givenValue, givenCoordinate);

            // Then
            assertThat(result)
                    .isEqualTo(givenValue);

            assertThat(underTest.getValueAt(givenCoordinate))
                    .isEqualTo(givenValue)
                    .isNotEqualTo(preSetItem);
        }


        static Stream<Arguments> coordinateProvider() {
            return IntStream.range(0, 3) // rows (y)
                    .boxed()
                    .flatMap(y -> IntStream.range(0, 3) // cols (x)
                            .mapToObj(x -> Arguments.of(x, y)));
        }
    }

    @DisplayName("rowCount()")
    @Nested
    class RowCountTest {

        @DisplayName("Should return size of first dimension array")
        @Test
        void shouldReturnSizeOfFirstDimensionArray() {
            // When
            var result = underTest.rowCount();

            // Then
            assertThat(result)
                    .isEqualTo(matrixData.length);
        }
    }

    @DisplayName("columnCount()")
    @Nested
    class ColumnCountTest {

        @DisplayName("Should return size of second dimension array")
        @Test
        void shouldReturnSizeOfSecondDimensionArray() {
            // When
            var result = underTest.columnCount();

            // Then
            assertThat(result)
                    .isEqualTo(matrixData[0].length);
        }
    }

    @DisplayName("toFlatList()")
    @Nested
    class ToFlatListTest {

        @DisplayName("Should return items in row-major order (left to right, top to bottom)")
        @Test
        void shouldReturnItemsInRowMajorOrder() {
            // When
            var result = underTest.toFlatList();

            // Then
            assertThat(result).containsExactly(
                    new Coordinate2D(0, 0), new Coordinate2D(1, 0), new Coordinate2D(2, 0),
                    new Coordinate2D(0, 1), new Coordinate2D(1, 1), new Coordinate2D(2, 1),
                    new Coordinate2D(0, 2), new Coordinate2D(1, 2), new Coordinate2D(2, 2)
            );
        }

    }

    @DisplayName("getAvailableCoordinates()")
    @Nested
    class GetAvailableCoordinatesTest {

        @DisplayName("Should give list of coordinates of all items in matrix")
        @Test
        void shouldGiveListOfCoordinatesOfAllItemsInMatrix() {
            // Given
            var givenData = new Integer[][]{
                    {1, 2, 3},
                    {4, 5, 6}
            };
            var underTest = matrixFromData(givenData);

            // When
            var result = underTest.getAvailableCoordinates();

            // Then
            assertThat(result)
                    .hasSize(6)
                    .containsExactly(
                            new Coordinate2D(0, 0),
                            new Coordinate2D(1, 0),
                            new Coordinate2D(2, 0),
                            new Coordinate2D(0, 1),
                            new Coordinate2D(1, 1),
                            new Coordinate2D(2, 1)
                    );
        }

    }

    @DisplayName("clone()")
    @Nested
    class CloneTest {

        @DisplayName("Should deep copy values")
        @Test
        void shouldDeepCopyValues() {
            // Given
            var givenMatrix = new Integer[][]{
                    {1, 2, 3},
                    {4, 5, 6}
            };

            var underTest = matrixFromData(givenMatrix);

            // When
           var result = underTest.copy();

            // Then
            givenMatrix[1][1] = 0;
            assertThat(result.getValueAt(new Coordinate2D(1, 1)))
                    .isEqualTo(5);
        }
    }

    @DisplayName("containsCoordinate()")
    @Nested
    class ContainsCoordinateTest {

        private final int givenRowCount = matrixData.length;
        private final int givenColCount = matrixData[0].length;

        @DisplayName("Should return true if x < columns and y < rows")
        @Test
        void shouldReturnTrue() {
            // Given
            var givenCoordinate = new Coordinate2D(givenColCount - 1, givenRowCount -1);


            // When
            var result = underTest.containsCoordinate(givenCoordinate);

            // Then
            assertThat(result)
                    .isTrue();
        }

        @DisplayName("Should return false if x >= columns")
        @Test
        void shouldReturnFalse() {
            // Given
            var givenCoordinate = new Coordinate2D(givenColCount, givenRowCount - 1);

            // When
            var result = underTest.containsCoordinate(givenCoordinate);

            // Then
            assertThat(result)
                    .isFalse();
        }

        @DisplayName("Should return false if y >= rows")
        @Test
        void shouldReturnFalse2() {
            // Given
            var givenCoordinate = new Coordinate2D(givenColCount - 1, givenRowCount);

            // When
            var result = underTest.containsCoordinate(givenCoordinate);

            // Then
            assertThat(result)
                    .isFalse();
        }
    }
}