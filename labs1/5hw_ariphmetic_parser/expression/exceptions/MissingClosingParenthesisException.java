package expression.exceptions;

public class MissingClosingParenthesisException extends ParsingException {
    public MissingClosingParenthesisException(String s, final int ind) {
        super(Colors.RED + "Missing closing parenthesis for opening one at position: " + ind + "\n" + Colors.PURPLE + s
                + "\n" + getPlace(ind, 1));
    }
}