package expression.MiniExceptions;

public class MissingClosingBracketException extends ParsingException {
    public MissingClosingBracketException(String s, final int ind) {
        super("Missing ')' for '(' at position: " + ind + "\n" + s
                + "\n" + getPlace(ind));
    }
}