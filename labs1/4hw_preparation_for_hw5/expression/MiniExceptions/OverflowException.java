package expression.MiniExceptions;

public class OverflowException extends CountException {
    public OverflowException() {
        super("Value overflow occured!");
    }
}