package expression.MiniExceptions;

public class MissingOperandException extends ParsingException {
    public MissingOperandException(String s, final int ind) {
        super("Missing operand before position: " + ind + "\n" + s + "\n"
                + getPlace(ind));
    }
}