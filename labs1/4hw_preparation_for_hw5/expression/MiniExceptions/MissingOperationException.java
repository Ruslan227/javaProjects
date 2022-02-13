package expression.MiniExceptions;

public class MissingOperationException extends ParsingException {
    public MissingOperationException(String s, final int ind) {
        super("Missing operation: " + ind + "\n" + s + "\n" + getPlace(ind));
    }
}