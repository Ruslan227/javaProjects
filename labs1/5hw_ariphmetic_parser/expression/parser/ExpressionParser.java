package expression.parser;

import expression.operations.*;
import expression.exceptions.*;
import expression.expressions.*;
import java.util.*;

public class ExpressionParser<T> implements Parser<T> {
    private InternalParser<T> internal;
    private Operation<T> myOp;

    public ExpressionParser(final Operation<T> x) {
        myOp = x;
    }

    public TripleExpression<T> parse(final String s) throws ParsingException {
        internal = new InternalParser<T>(s, myOp);
        return minMax();
    }

    private TripleExpression<T> minMax() throws ParsingException {
        TripleExpression<T> res = addSub();
        while (true) {
            switch (internal.getCurrentToken()) {
            case MIN:
                res = new Min<T>(res, addSub(), myOp);
                break;
            case MAX:
                res = new Max<T>(res, addSub(), myOp);
                break;
            default:
                return res;
            }
        }

    }

    private TripleExpression<T> addSub() throws ParsingException {
        TripleExpression<T> res = mulDiv();
        while (true) {
            switch (internal.getCurrentToken()) {
            case ADD:
                res = new Add<T>(res, mulDiv(), myOp);
                break;
            case SUB:
                res = new Subtract<T>(res, mulDiv(), myOp);
                break;
            default:
                return res;
            }
        }

    }

    private TripleExpression<T> mulDiv() throws ParsingException {
        TripleExpression<T> res = unary();
        while (true) {
            switch (internal.getCurrentToken()) {
            case MUL:
                res = new Multiply<T>(res, unary(), myOp);
                break;
            case DIV:
                res = new Divide<T>(res, unary(), myOp);
                break;
            default:
                return res;
            }
        }
    }

    private TripleExpression<T> unary() throws ParsingException {
        TripleExpression<T> res;
        switch (internal.getNextToken()) {
        case CNT:
            res = new Count<T>(unary(), myOp);
            // internal.getNextToken();
            break;
        case NUM:
            res = new Const<T>(internal.getValue());
            internal.getNextToken();
            break;
        case VAR:
            res = new Variable<T>(internal.getName());
            internal.getNextToken();
            break;
        case NEG:
            res = new Negate<T>(unary(), myOp);
            break;
        case OP_BRACE:
            res = addSub();
            if (internal.getCurrentToken() != Token.CL_BRACE) {
                throw new MissingClosingParenthesisException(internal.getExpression(), internal.getInd());
            }
            internal.getNextToken();
            break;
        default:
            throw new ParsingException("Incorrect expression" + "\n" + internal.getExpression());
        }
        return res;
    }

    private static class InternalParser<T> {
        private String expression;
        private int ind = 0;

        final private Operation<T> op;

        private Token curToken;
        private static HashSet<Token> OPERATIONS = new HashSet<>();
        private static HashSet<Token> BINARY_OPERATIONS = new HashSet<>();
        private static HashMap<String, Token> IDENTIFIERS = new HashMap<>();

        static {
            IDENTIFIERS.put("x", Token.VAR);
            IDENTIFIERS.put("y", Token.VAR);
            IDENTIFIERS.put("z", Token.VAR);
            IDENTIFIERS.put("count", Token.CNT);
            IDENTIFIERS.put("min", Token.MIN);
            IDENTIFIERS.put("max", Token.MAX);

            OPERATIONS.add(Token.ADD);
            OPERATIONS.add(Token.SUB);
            OPERATIONS.add(Token.MUL);
            OPERATIONS.add(Token.DIV);
            OPERATIONS.add(Token.MIN);
            OPERATIONS.add(Token.MAX);

            BINARY_OPERATIONS.add(Token.ADD);
            BINARY_OPERATIONS.add(Token.SUB);
            BINARY_OPERATIONS.add(Token.MUL);
            BINARY_OPERATIONS.add(Token.DIV);
            BINARY_OPERATIONS.add(Token.MIN);
            BINARY_OPERATIONS.add(Token.MAX);
        }

        private T value;
        private char name;
        private int balance;

        public InternalParser(final String s, final Operation<T> operation) {
            expression = s;
            ind = 0;
            curToken = Token.BEGIN;
            balance = 0;
            op = operation;
        }

        public Token getNextToken() throws ParsingException {
            nextToken();
            return curToken;
        }

        public Token getCurrentToken() {
            return curToken;
        }

        public T getValue() {
            return value;
        }

        public char getName() {
            return name;
        }

        public int getInd() {
            return ind;
        }

        public String getExpression() {
            return expression;
        }

        private void skipWhiteSpace() {
            while (ind < expression.length() && Character.isWhitespace(expression.charAt(ind))) {
                ind++;
            }
        }

        private void checkForOperand(final int pos) throws MissingOperandException {
            if (OPERATIONS.contains(curToken) || curToken == Token.OP_BRACE || curToken == Token.BEGIN) {
                throw new MissingOperandException(expression, pos);
            }
        }

        private void checkForOperation(final int pos) throws MissingOperationException {
            if (curToken == Token.CL_BRACE || curToken == Token.VAR || curToken == Token.NUM) {
                throw new MissingOperationException(expression, pos);
            }
        }

        private boolean isPartOfNumber(final char x) {
            return Character.isDigit(x) || x == '.' || x == 'e';
        }

        private String getNumber() {
            int l = ind;
            while (ind < expression.length() && isPartOfNumber(expression.charAt(ind))) {
                ind++;
            }
            int r = ind;
            ind--;
            return expression.substring(l, r);
        }

        private boolean isPartOfIdentifier(final char x) {
            return Character.isLetterOrDigit(x) || x == '_';
        }

        private String getIdentifier() throws UnknownSymbolException {
            if (!Character.isLetter(expression.charAt(ind))) {
                throw new UnknownSymbolException(expression, ind);
            }
            int l = ind;
            while (ind < expression.length() && isPartOfIdentifier(expression.charAt(ind))) {
                ind++;
            }
            int r = ind;
            ind--;
            return expression.substring(l, r);
        }

        private void nextToken() throws ParsingException {
            skipWhiteSpace();
            if (ind >= expression.length()) {
                checkForOperand(ind);
                curToken = Token.END;
                return;
            }
            char ch = expression.charAt(ind);
            switch (ch) {
            case '-':
                if (curToken == Token.NUM || curToken == Token.VAR || curToken == Token.CL_BRACE) {
                    curToken = Token.SUB;
                } else {
                    if (ind + 1 >= expression.length()) {
                        throw new MissingOperandException(expression, ind + 1);
                    }
                    if (Character.isDigit(expression.charAt(ind + 1))) {
                        ind++;
                        String temp = getNumber();
                        try {
                            value = op.parseNumber("-" + temp);
                        } catch (BadConstantException e) {
                            throw new BadConstantException("-" + temp, expression, ind - temp.length());
                        }
                        curToken = Token.NUM;
                    } else {
                        curToken = Token.NEG;
                    }
                }
                break;
            case '+':
                checkForOperand(ind);
                curToken = Token.ADD;
                break;
            case '*':
                checkForOperand(ind);
                curToken = Token.MUL;
                break;
            case '/':
                checkForOperand(ind);
                curToken = Token.DIV;
                break;
            case '(':
                if (curToken == Token.CL_BRACE || curToken == Token.NUM || curToken == Token.VAR) {
                    throw new OddOpeningParenthesisException(expression, ind);
                }
                balance++;
                curToken = Token.OP_BRACE;
                break;
            case ')':
                if (OPERATIONS.contains(curToken) || curToken == Token.OP_BRACE) {
                    throw new MissingOperandException(expression, ind);
                }
                balance--;
                if (balance < 0) {
                    throw new OddClosingParenthesisException(expression, ind);
                }
                curToken = Token.CL_BRACE;
                break;
            default:
                if (Character.isDigit(ch)) {
                    checkForOperation(ind);
                    String temp = getNumber();
                    try {
                        value = op.parseNumber(temp);
                    } catch (BadConstantException e) {
                        throw new BadConstantException(temp, expression, ind - temp.length() + 1);
                    }
                    curToken = Token.NUM;
                } else {
                    String curInd = getIdentifier();
                    if (!IDENTIFIERS.containsKey(curInd)) {
                        throw new UnknownNameException(curInd, expression, ind - curInd.length() + 1);
                    }
                    if (BINARY_OPERATIONS.contains(IDENTIFIERS.get(curInd))) {
                        checkForOperand(ind - curInd.length() + 1);
                    } else {
                        checkForOperation(ind - curInd.length() + 1);
                    }
                    curToken = IDENTIFIERS.get(curInd);
                    if (curToken == Token.VAR) {
                        name = curInd.charAt(0);
                    }
                }

            }
            ind++;
        }
    }

}