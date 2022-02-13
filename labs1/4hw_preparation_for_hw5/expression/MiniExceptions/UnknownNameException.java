package expression.MiniExceptions;

public class UnknownNameException extends ParsingException {
    public UnknownNameException(final String name, final String s, final int ind) {
        super("Unknown name '" + name + "' at position: " + ind + "\n" + s + "\n"
                + getPlace(ind));
    }
}