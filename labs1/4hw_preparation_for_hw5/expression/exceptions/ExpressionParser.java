package expression.exceptions;

import expression.*;
import expression.MiniExceptions.*;
import java.util.*;

public class ExpressionParser implements Parser {
    private InternalParser parser;

    public TripleExpression parse(final String s) throws ParsingException {
        parser = new InternalParser(s);
        return addSub();
    }

    private CommonExpression addSub() throws ParsingException {
        CommonExpression result = mulDiv();
        while (true) {
            switch (parser.getCurrentToken()) {
            case ADD:
                result = new CheckedAdd(result, mulDiv());
                break;
            case SUB:
                result = new CheckedSubtract(result, mulDiv());
                break;
            default:
                return result;
            }
        }
    }

    private CommonExpression mulDiv() throws ParsingException {
        CommonExpression result = unary();
        while (true) {
            switch (parser.getCurrentToken()) {
            case MUL:
                result = new CheckedMultiply(result, unary());
                break;
            case DIV:
                result = new CheckedDivide(result, unary());
                break;
            default:
                return result;
            }
        }
    }

    private CommonExpression unary() throws ParsingException {
        CommonExpression result;
        switch (parser.getNextToken()) {
        case NUM:
            result = new Const(parser.getValue());
            parser.getNextToken();
            break;
        case VAR:
            result = new Variable(parser.getName());
            parser.getNextToken();
            break;
        case NEG:
            result = new CheckedNegate(unary());
            break;
        case LOG2:
            result = new CheckedLog2(unary());
            break;
        case POW2:
            result = new CheckedPow2(unary());
            break;
        case OP_BR:
            result = addSub();
            if (parser.getCurrentToken() != Token.CL_BR) {
                throw new MissingClosingBracketException(parser.getExpression(), parser.getIndex() - 1);
            }
            parser.getNextToken();
            break;
        default:
            throw new ParsingException("Incorrect expression" + "\n" + parser.getExpression());
        }
        return result;
    }

    private static class InternalParser {
        private String expression;
        private Token curToken;
        private int index = 0;
        private int value;
        private char name;
        private int balance;

        private static Set<Token> operations = EnumSet.of(Token.NEG, Token.ADD, Token.SUB, Token.MUL, Token.DIV,
                Token.LOG2, Token.POW2);
        private static final Set<Token> binary_operations = EnumSet.of(Token.ADD, Token.SUB, Token.MUL, Token.DIV);
        private static final Map<String, Token> identifiers = new HashMap<>();

        public InternalParser(final String expression) {
            this.expression = expression;
            index = 0;
            curToken = Token.START;
            balance = 0;
            identifiers.put("log2", Token.LOG2);
            identifiers.put("pow2", Token.POW2);
            identifiers.put("x", Token.VAR);
            identifiers.put("y", Token.VAR);
            identifiers.put("z", Token.VAR);
        }

        private void nextToken() throws ParsingException {
            skipWhitespace();
            if (index >= expression.length()) {
                checkMissingOperand();
                curToken = Token.END;
                return;
            }
            switch (expression.charAt(index)) {
            case '(':
                checkMissingOperation();
                balance++;
                curToken = Token.OP_BR;
                break;
            case ')':
                if (operations.contains(curToken) || curToken == Token.OP_BR) {
                    throw new MissingOperandException(expression, index);
                }
                balance--;
                if (balance < 0) {
                    throw new OddClosingBracketException(expression, index);
                }

                curToken = Token.CL_BR;
                break;
            case '-':
                if (curToken == Token.NUM || curToken == Token.VAR || curToken == Token.CL_BR) {
                    curToken = Token.SUB;
                } else {
                    if (index + 1 >= expression.length()) {
                        throw new MissingOperandException(expression, index);
                    }
                    if (Character.isDigit(expression.charAt(index + 1))) {
                        index++;
                        String temp = getNumber();
                        try {
                            value = Integer.parseInt("-" + temp);
                        } catch (NumberFormatException e) {
                            throw new ConstantException("-" + temp, expression, index);
                        }
                        curToken = Token.NUM;
                    } else {
                        curToken = Token.NEG;
                    }
                }
                break;
            case '+':
                checkMissingOperand();
                curToken = Token.ADD;
                break;
            case '*':
                checkMissingOperand();
                curToken = Token.MUL;
                break;
            case '/':
                checkMissingOperand();
                curToken = Token.DIV;
                break;
            default:
                if (Character.isDigit(expression.charAt(index))) {
                    checkMissingOperation();
                    String temp = getNumber();
                    try {
                        value = Integer.parseInt(temp);
                    } catch (Exception e) {
                        throw new ConstantException(temp + "kek", expression, index);
                    }
                    curToken = Token.NUM;
                } else {
                    String curInd = getIdentifier();
                    if (!identifiers.containsKey(curInd)) {
                        throw new UnknownNameException(expression, curInd, index);
                    }
                    if (binary_operations.contains(identifiers.get(curInd))) {
                        checkMissingOperand();
                    } else {
                        checkMissingOperation();
                    }
                    curToken = identifiers.get(curInd);
                    if (curToken == Token.VAR) {
                        name = curInd.charAt(0);
                    }
                }

            }
            index++;
        }

        private void skipWhitespace() {
            while (index < expression.length() && Character.isWhitespace(expression.charAt(index))) {
                index++;
            }
        }

        private void checkMissingOperand() throws MissingOperandException {
            if (operations.contains(curToken) || curToken == Token.OP_BR || curToken == Token.START) {
                throw new MissingOperandException(expression, index);
            }
        }

        private void checkMissingOperation() throws MissingOperationException {
            if (curToken == Token.CL_BR || curToken == Token.VAR || curToken == Token.NUM) {
                throw new MissingOperationException(expression, index);
            }
        }

        private boolean isPartOfIdentifier(final char x) {
            return Character.isLetterOrDigit(x);
        }

        private String getIdentifier() throws UnknownSymbolException {
            if (!Character.isLetter(expression.charAt(index))) {
                throw new UnknownSymbolException(expression, index);
            }
            int l = index;
            while (index < expression.length() && isPartOfIdentifier(expression.charAt(index))) {
                index++;
            }
            int r = index;
            index--;
            return expression.substring(l, r);
        }

        private String getNumber() {
            int l = index;
            while (index < expression.length() && Character.isDigit(expression.charAt(index))) {
                index++;
            }
            int r = index;
            index--;
            return expression.substring(l, r);
        }

        public Token getNextToken() throws ParsingException {
            nextToken();
            return curToken;
        }

        public Token getCurrentToken() {
            return curToken;
        }

        public int getValue() {
            return value;
        }

        public int getIndex() {
            return index;
        }

        public char getName() {
            return name;
        }

        public String getExpression() {
            return expression;
        }

    }

}