package expression.exceptions;

public class CalculatingException extends Exception {
    public CalculatingException(final String message) {
        super(Colors.PURPLE + message + Colors.RESET);
    }
}