package pl.bdygasinski.gameoflife.domain.exception;

public class InvalidCoordinateException extends IllegalArgumentException {

    InvalidCoordinateException(String message) {
        super(message);
    }

    public static InvalidCoordinateException withInvalidCoord(int x , int y) {
        return new InvalidCoordinateException("X and Y of coordinate must be >= 0 but got x=%s and y=%s".formatted(x, y));
    }
}
