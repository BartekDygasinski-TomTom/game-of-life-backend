package pl.bdygasinski.gameoflife.domain.matrix;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;

class FixedSizeArrayMatrix2DTest {

    private final Collection<Coordinate2D> matrixData = Stream.of(
                    Coordinate2D.from(0, 0), Coordinate2D.from(1, 0), Coordinate2D.from(2, 0),
                    Coordinate2D.from(0, 1), Coordinate2D.from(1, 1), Coordinate2D.from(2, 1),
                    Coordinate2D.from(0, 2), Coordinate2D.from(1, 2), Coordinate2D.from(2, 2)
            )
            .flatMap(Optional::stream)
            .toList();

    private final MatrixDimensions givenMatrixDimensions = new MatrixDimensions(3, 3);

    private final FixedSizeArrayMatrix2D<Coordinate2D> underTest =
            new FixedSizeArrayMatrix2D<>(givenMatrixDimensions, matrixData, Coordinate2D.class);

    @DisplayName("creation tests")
    @Nested
    class CreationTest {

        private static Stream<Arguments> provideNullDependencies() {
            return Stream.of(
                            Arguments.of(null, List.of(1, 2, 3),
                            Arguments.of(new MatrixDimensions(1, 1), null)
                    ));
        }

        private static Stream<Integer[][]> provideDataWithIncorrectValues() {
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
                    },
                    new Integer[][]{
                            {}
                    },
                    new Integer[][]{
                            {1, 2},
                            {1}
                    }
            );
        }

        private static Collection<Integer> convertToCollectionWithNulls(Integer[][] matrix) {
            var arrayList = new ArrayList<Integer>(matrix.length);

            for (Integer[] integers : matrix) {
                if (integers == null) {
                    continue;
                }

                arrayList.addAll(Arrays.asList(integers));
            }

            return Collections.unmodifiableList(arrayList);
        }

        @DisplayName("Should throw if input is null")
        @ParameterizedTest
        @MethodSource("provideNullDependencies")
        void shouldThrowIfInputIsNull(MatrixDimensions matrixDimensions, Iterable<Integer> items) {
            // When
            var result = catchException(() -> new FixedSizeArrayMatrix2D<>(matrixDimensions, items, Integer.class));

            // Then
            assertThat(result)
                    .isNotNull();
        }

        @DisplayName("Should throw if input data are not valid")
        @ParameterizedTest
        @MethodSource("provideDataWithIncorrectValues")
        void shouldThrowIfMatrixContainsNull(Integer[][] matrixData) {
            // Given
            var givenDimensions = new MatrixDimensions(2, 3);
            var givenData = convertToCollectionWithNulls(matrixData);

            // When
            var result = catchException(() -> new FixedSizeArrayMatrix2D<>(givenDimensions, givenData, Integer.class));

            // Then
            assertThat(result)
                    .isNotNull();
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
            var givenCoordinate = Coordinate2D.from(x, y).orElseThrow();

            // When
            var result = underTest.getValueAt(givenCoordinate);

            // Then
            assertThat(result.getX())
                    .isEqualTo(x);

            assertThat(result.getY())
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
            var givenCorrectValue = Coordinate2D.from(1, 1).orElseThrow();

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
            var givenCorrectCoordinate = Coordinate2D.from(1, 1).orElseThrow();

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
            var givenCoordinate = Coordinate2D.from(x, y).orElseThrow();
            var givenValue = Coordinate2D.from(10, 10).orElseThrow();
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

    @DisplayName("getDimensions()")
    @Nested
    class GetDimensionsTest {

        @DisplayName("Should return input dimensions")
        @Test
        void shouldReturnViewOfSameMatrix() {
            // When
            var result = underTest.getDimensions();

            // Then
            assertThat(result)
                    .isEqualTo(givenMatrixDimensions);
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
                    matrixData.toArray(Coordinate2D[]::new)
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
            var givenData = List.of(1, 2, 3, 4, 5, 6);
            var givenDimensions = new MatrixDimensions(2, 3);
            var underTest = new FixedSizeArrayMatrix2D<>(givenDimensions, givenData, Integer.class);

            // When
            var result = underTest.getAvailableCoordinates();

            // Then
            var expected = givenDimensions.generateAllCoordinates().toArray(Coordinate2D[]::new);
            assertThat(result)
                    .hasSize(6)
                    .containsExactly(expected);
        }

    }

    @DisplayName("clone()")
    @Nested
    class CloneTest {

        @DisplayName("Should deep copy values")
        @Test
        void shouldDeepCopyValues() {
            // Given
            var givenData = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6));
            var givenDimensions = new MatrixDimensions(2, 3);
            var underTest = new FixedSizeArrayMatrix2D<>(givenDimensions, givenData, Integer.class);

            // When
           var result = underTest.copy();

            // Then
            givenData.set(4, 0);
            assertThat(result.getValueAt(Coordinate2D.from(1, 1).orElseThrow()))
                    .isEqualTo(5);
        }
    }

    @DisplayName("containsCoordinate()")
    @Nested
    class ContainsCoordinateTest {

        private final int givenRowCount = givenMatrixDimensions.columns();
        private final int givenColCount = givenMatrixDimensions.rows();

        @DisplayName("Should return true if x < columns and y < rows")
        @Test
        void shouldReturnTrue() {
            // Given
            var givenCoordinate = Coordinate2D.from(givenColCount - 1, givenRowCount -1);


            // When
            var result = underTest.containsCoordinate(givenCoordinate.orElseThrow());

            // Then
            assertThat(result)
                    .isTrue();
        }

        @DisplayName("Should return false if x >= columns")
        @Test
        void shouldReturnFalse() {
            // Given
            var givenCoordinate = Coordinate2D.from(givenColCount, givenRowCount - 1).orElseThrow();

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
            var givenCoordinate = Coordinate2D.from(givenColCount - 1, givenRowCount).orElseThrow();

            // When
            var result = underTest.containsCoordinate(givenCoordinate);

            // Then
            assertThat(result)
                    .isFalse();
        }
    }
}