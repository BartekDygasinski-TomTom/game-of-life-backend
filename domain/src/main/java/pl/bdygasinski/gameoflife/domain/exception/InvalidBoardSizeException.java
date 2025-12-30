package pl.bdygasinski.gameoflife.domain.exception;

public class InvalidBoardSizeException extends IllegalArgumentException {

    public InvalidBoardSizeException(String s) {
        super(s);
    }
}
