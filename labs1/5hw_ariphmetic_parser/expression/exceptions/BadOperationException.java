package expression.exceptions;

public class BadOperationException extends CalculatingException {
    public BadOperationException(String reason) {
        super("Incorrect operation occured: " + reason);
    }
}