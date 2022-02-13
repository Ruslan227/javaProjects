package expression.MiniExceptions;

public class OperationException extends CountException {
    public OperationException(String reason) {
        super("Incorrect operation: " + reason);
    }
}