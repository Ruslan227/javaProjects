package expression.exceptions;

public class OddClosingParenthesisException extends ParsingException {
    public OddClosingParenthesisException(String s, final int ind) {
        super(Colors.RED + "Odd closing parenthesis at position: " + ind + "\n" + Colors.PURPLE + s + "\n"
                + getPlace(ind, 1));
    }
}