package expression.MiniExceptions;

public class UnknownSymbolException extends ParsingException {
    public UnknownSymbolException(String s, final int ind) {
        super("Unknown symbol '" + s.charAt(ind) + "' at position " + ind + "\n" + s + "\n"
                + getPlace(ind));
    }
}