package pl.bdygasinski.gameoflife.domain.exception;

public class InvalidCoordinateException extends IllegalArgumentException {

    public InvalidCoordinateException(String message) {
        super(message);
    }
}
