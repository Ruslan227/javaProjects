package expression.exceptions;

public class OddOpeningParenthesisException extends ParsingException {
    public OddOpeningParenthesisException(String s, final int ind) {
        super(Colors.RED + "Odd opening parenthesis at position: " + ind + "\n" + Colors.PURPLE + s + "\n"
                + getPlace(ind, 1));
    }
}