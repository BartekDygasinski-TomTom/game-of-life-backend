package pl.bdygasinski.gameoflife.domain.exception;

public class InvalidCoordinateException extends RuntimeException {

    public InvalidCoordinateException(String message) {
        super(message);
    }
}
