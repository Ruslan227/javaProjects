package expression.exceptions;

public class ValueOverflowException extends CalculatingException {
    public ValueOverflowException() {
        super("Value overflow occured!");
    }
}