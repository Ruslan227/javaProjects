package expression.exceptions;

public class BadConstantException extends ParsingException {
    public BadConstantException(final String reason, final String s, final int ind) {
        super(Colors.RED + "Constant '" + reason + "' at position " + ind + " is bad\n" + Colors.PURPLE + s + "\n"
                + getPlace(ind, reason.length()));
    }

    public BadConstantException() {
        super(Colors.RED + "Bad constant" + Colors.RESET);
    }
}