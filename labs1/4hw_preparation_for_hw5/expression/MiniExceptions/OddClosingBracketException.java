package expression.MiniExceptions;

public class OddClosingBracketException extends ParsingException {
    public OddClosingBracketException(String s, final int ind) {
        super("Odd closing bracket at position: " + ind + "\n" + s + "\n"
                + getPlace(ind));
    }
}