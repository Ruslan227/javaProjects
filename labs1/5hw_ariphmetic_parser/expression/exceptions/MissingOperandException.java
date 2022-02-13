package expression.exceptions;

public class MissingOperandException extends ParsingException {
    public MissingOperandException(String s, final int ind) {
        super(Colors.RED + "Missing operand before position: " + ind + "\n" + Colors.PURPLE + s + "\n"
                + getPlace(ind, 1));
    }
}