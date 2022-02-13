package expression.exceptions;

public class UnknownNameException extends ParsingException {
    public UnknownNameException(final String name, final String s, final int ind) {
        super(Colors.RED + "Unknown name '" + name + "' at position: " + ind + "\n" + Colors.PURPLE + s + "\n"
                + getPlace(ind, name.length()));
    }
}