package expression.exceptions;

public class MissingOperationException extends ParsingException {
    public MissingOperationException(String s, final int ind) {
        super(Colors.RED + "Missing operation: " + ind + "\n" + Colors.PURPLE + s + "\n" + getPlace(ind, 1));
    }
}