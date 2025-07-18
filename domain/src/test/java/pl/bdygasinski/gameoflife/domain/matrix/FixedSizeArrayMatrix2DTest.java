package pl.bdygasinski.gameoflife.domain.matrix;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;

class FixedSizeArrayMatrix2DTest {

    private final Coordinate2D[][] matrix = {
            {new Coordinate2D(0, 0), new Coordinate2D(1, 0), new Coordinate2D(2, 0)},
            {new Coordinate2D(0, 1), new Coordinate2D(1, 1), new Coordinate2D(2, 1)},
            {new Coordinate2D(0, 2), new Coordinate2D(1, 2), new Coordinate2D(2, 2)},
    };
    private final FixedSizeArrayMatrix2D<Coordinate2D> underTest = new FixedSizeArrayMatrix2D<>(matrix);

    @DisplayName("creation tests")
    @Nested
    class CreationTest {

        @DisplayName("Should throw if input is null")
        @Test
        void shouldThrowIfInputIsNull() {
            // When
            Exception result = catchException(() -> new FixedSizeArrayMatrix2D<>(null));

            // Then
            assertThat(result)
                    .isNotNull()
                    .hasMessageContaining("null");
        }

        @DisplayName("Should throw if first dimension of array has size = 0")
        @Test
        void shouldThrowIfArrayIsEmpty() {
            // Given
            var givenArray = new Integer[][]{};

            // When
            Exception result = catchException(() -> new FixedSizeArrayMatrix2D<>(givenArray));

            // Then
            assertThat(result)
                    .isNotNull()
                    .hasMessageContaining(Arrays.toString(givenArray));
        }

        @DisplayName("Should throw if second dimension of array has size = 0")
        @Test
        void shouldThrowIfArrayIsEmpty2() {
            // Given
            var givenArray = new Integer[][]{
                    {}
            };

            // When
            Exception result = catchException(() -> new FixedSizeArrayMatrix2D<>(givenArray));

            // Then
            assertThat(result)
                    .isNotNull()
                    .hasMessageContaining(Arrays.toString(givenArray));
        }

        @DisplayName("Should throw if one of rows is null")
        @Test
        void shouldThrowIfOneOfRowsIsNull() {
            // Given
            var givenData = new Integer[][]{
                    null
            };

            // When
            Exception result = catchException(() -> new FixedSizeArrayMatrix2D<>(givenData));

            // Then
            assertThat(result)
                    .isNotNull()
                    .hasMessageContaining(Arrays.toString(givenData));
        }

        @DisplayName("Should throw if one of rows is not same size")
        @Test
        void shouldThrowIfOneOfRowsIsNotSameSIze() {
            // Given
            var givenData = new Integer[][]{
                    {1, 2, 3},
                    {1, 2}
            };

            // When
            Exception result = catchException(() -> new FixedSizeArrayMatrix2D<>(givenData));

            // Then
            assertThat(result)
                    .isNotNull()
                    .hasMessageContaining(Arrays.toString(givenData));
        }

        @DisplayName("Should throw if one of cells is null")
        @Test
        void shouldThrowIfOneOfCellIsNull() {
            // Given
            var givenData = new Integer[][]{
                    {1, 2, 3, null}
            };

            // When
            Exception result = catchException(() -> new FixedSizeArrayMatrix2D<>(givenData));

            // Then
            assertThat(result)
                    .isNotNull()
                    .hasMessageContaining("null");
        }
    }

    @DisplayName("getValueAt()")
    @Nested
    class GetValueAtTest {

        @DisplayName("Should throw if input coordinate is null")
        @Test
        void shouldThrowIfInputIsNull() {
            // When
            Exception result = catchException(() -> underTest.getValueAt(null));

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
            Coordinate2D result = underTest.getValueAt(givenCoordinate);

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
            Exception result = catchException(() -> underTest.setValueAt(givenCorrectValue, null));

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
            Exception result = catchException(() -> underTest.setValueAt(null, givenCorrectCoordinate));

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
            Coordinate2D result = underTest.setValueAt(givenValue, givenCoordinate);

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
            int result = underTest.rowCount();

            // Then
            assertThat(result)
                    .isEqualTo(matrix.length);
        }
    }

    @DisplayName("columnCount()")
    @Nested
    class ColumnCountTest {

        @DisplayName("Should return size of second dimension array")
        @Test
        void shouldReturnSizeOfSecondDimensionArray() {
            // When
            int result = underTest.columnCount();

            // Then
            assertThat(result)
                    .isEqualTo(matrix[0].length);
        }
    }

    @DisplayName("toFlatList()")
    @Nested
    class ToFlatListTest {

        @DisplayName("Should return items in row-major order (left to right, top to bottom)")
        @Test
        void shouldReturnItemsInRowMajorOrder() {
            // When
            List<Coordinate2D> result = underTest.toFlatList();

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
            var underTest = new FixedSizeArrayMatrix2D<>(givenData);

            // When
            var result = underTest.getAvailableCoordinates();

            // Then
            assertThat(result)
                    .hasSize(6);

            assertThat(result.stream().mapToInt(Coordinate2D::x).min())
                    .hasValue(0);
            assertThat(result.stream().mapToInt(Coordinate2D::x).max())
                    .hasValue(2);

            assertThat(result.stream().mapToInt(Coordinate2D::y).min())
                    .hasValue(0);
            assertThat(result.stream().mapToInt(Coordinate2D::y).max())
                    .hasValue(1);
        }

    }

    @DisplayName("equals()")
    @Nested
    class EqualsTest {

        @Test
        @DisplayName("Should be equal to itself")
        void shouldBeEqualToItself() {
            // Given
            var givenData1 = new Integer[][]{
                    {1, 2, 3}
            };
            var givenMatrix1 = new FixedSizeArrayMatrix2D<>(givenData1);

            // When
            // Then
            assertThat(givenMatrix1)
                    .isEqualTo(givenMatrix1);

        }

        @Test
        @DisplayName("Should be equal to another object with same values")
        void shouldBeEqualToAnotherWithSameValues() {
            // Given
            var givenData1 = new Integer[][]{
                    {1, 2, 3}
            };
            var givenData2 = new Integer[][]{
                    {1, 2, 3}
            };
            var givenMatrix1 = new FixedSizeArrayMatrix2D<>(givenData1);
            var givenMatrix2 = new FixedSizeArrayMatrix2D<>(givenData2);

            // When
            // Then
            assertThat(givenMatrix1)
                    .isEqualTo(givenMatrix2);
        }

        @Test
        @DisplayName("Should not be equal to object with different values")
        void shouldNotBeEqualToDifferentValues() {
            // Given
            var givenData1 = new Integer[][]{
                    {1, 2, 3}
            };
            var givenData2 = new Integer[][]{
                    {1, 2, 3, 4}
            };
            var givenMatrix1 = new FixedSizeArrayMatrix2D<>(givenData1);
            var givenMatrix2 = new FixedSizeArrayMatrix2D<>(givenData2);

            // When
            // Then
            assertThat(givenMatrix1)
                    .isNotEqualTo(givenMatrix2);
        }

        @Test
        @DisplayName("Should not be equal to null")
        void shouldNotBeEqualToNull() {
            // Given
            var givenData1 = new Integer[][]{
                    {1, 2, 3}
            };
            var givenMatrix1 = new FixedSizeArrayMatrix2D<>(givenData1);

            // When
            // Then
            assertThat(givenMatrix1)
                    .isNotEqualTo(null);
        }

        @Test
        @DisplayName("Should not be equal to different type")
        void shouldNotBeEqualToDifferentType() {
            // Given
            var givenData1 = new Integer[][]{
                    {1, 2, 3}
            };
            var givenMatrix1 = new FixedSizeArrayMatrix2D<>(givenData1);

            // When
            // Then
            assertThat(givenMatrix1)
                    .isNotEqualTo("Not matrix");
        }
    }

    @DisplayName("hashCode()")
    @Nested
    class HashCodeTest {

        @Test
        @DisplayName("Should produce same hashCode for equal objects")
        void shouldProduceSameHashCodeForEqualObjects() {
            // Given
            var givenData1 = new Integer[][]{
                    {1, 2, 3}
            };
            var givenData2 = new Integer[][]{
                    {1, 2, 3}
            };
            var givenMatrix1 = new FixedSizeArrayMatrix2D<>(givenData1);
            var givenMatrix2 = new FixedSizeArrayMatrix2D<>(givenData2);

            // When
            // Then
            assertThat(givenMatrix1.hashCode())
                    .isEqualTo(givenMatrix2.hashCode());
        }

        @Test
        @DisplayName("Should produce different hashCode for unequal objects")
        void shouldProduceDifferentHashCodeForUnequalObjects() {
            // Given
            var givenData1 = new Integer[][]{
                    {1, 2, 3, 4}
            };
            var givenData2 = new Integer[][]{
                    {1, 2, 3}
            };
            var givenMatrix1 = new FixedSizeArrayMatrix2D<>(givenData1);
            var givenMatrix2 = new FixedSizeArrayMatrix2D<>(givenData2);

            // When
            // Then
            assertThat(givenMatrix1.hashCode())
                    .isNotEqualTo(givenMatrix2.hashCode());
        }
    }

    @DisplayName("toString()")
    @Nested
    class ToStringTest {

        @DisplayName("Should return matrix with borders")
        @Test
        void shouldReturnMatrixWithBorders() {
            Integer[][] data = {
                    {1, 2, 3},
                    {4, 5, 6},
                    {7, 8, 9}
            };
            var underTest = new FixedSizeArrayMatrix2D<>(data);

            // When
            String result = underTest.toString();

            // Then
            SoftAssertions softAssertions = new SoftAssertions();
            for (Integer[] row : data) {
                for (Integer value : row) {
                    softAssertions.
                            assertThat(result)
                            .withFailMessage("Expected toString to contain: %s", value)
                            .contains(value.toString());
                }
            }
            softAssertions.assertAll();
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
            var underTest = new FixedSizeArrayMatrix2D<>(givenMatrix);

            // When
            Matrix2D<Integer> result = underTest.clone();

            // Then
            givenMatrix[1][1] = 0;
            assertThat(result.getValueAt(new Coordinate2D(1, 1)))
                    .isEqualTo(5);
        }
    }

    @DisplayName("containsCoordinate()")
    @Nested
    class ContainsCoordinateTest {

        private final int givenRowCount = matrix.length;
        private final int givenColCount = matrix[0].length;

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