package expression.MiniExceptions;

public class ConstantException extends ParsingException {
    public ConstantException(final String reason, final String s, final int ind) {
        super("Constant '" + reason + "' at position " + ind + " is'n correct\n" + s + "\n"
                + getPlace(ind));
    }
}