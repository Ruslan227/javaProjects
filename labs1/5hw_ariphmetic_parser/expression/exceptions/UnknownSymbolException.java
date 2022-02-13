package expression.exceptions;

public class UnknownSymbolException extends ParsingException {
    public UnknownSymbolException(String s, final int ind) {
        super(Colors.RED + "Unknown symbol '" + s.charAt(ind) + "' at position " + ind + "\n" + Colors.PURPLE + s + "\n"
                + getPlace(ind, 1));
    }
}