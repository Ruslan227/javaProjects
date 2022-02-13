package parser;

import parser.expressionClasses.*;
import parser.tokens.Tokenizer;
import parser.tokens.Tokens;

import java.util.function.BiFunction;
import java.util.function.Supplier;

public class Parser {
    public Tokenizer tokenizer;

    public LogicalObject parse(String expression) {
        if (expression.isEmpty())
            return new Var("");
        expression = expression.replace("|-", "⊢").replace("->", "→");
        tokenizer = new Tokenizer(expression);

        return parseFollow();
    }

    private LogicalObject parseSmth(final Tokens token,
                                    Supplier<LogicalObject> supplier,
                                    BiFunction<LogicalObject, LogicalObject, LogicalObject> constructor) {

        LogicalObject res = supplier.get();

        while (true) {
            if (tokenizer.getToken() == token) {
                res = constructor.apply(res, supplier.get());
            } else {
                return res;
            }
        }
    }

    private LogicalObject parseFollow() {
        LogicalObject res = parseDis();
        while (true) {
            if (tokenizer.getToken() == Tokens.OP_FOLLOW) {
                res = new FollowExpr(res, parseFollow());
            } else {
                return res;
            }
        }
    }

    private LogicalObject parseDis() {
        return parseSmth(Tokens.OP_OR, this::parseConj, Dis::new);
    }

    private LogicalObject parseConj() {
        return parseSmth(Tokens.OP_AND, this::parseUnary, Conj::new);
    }

    private LogicalObject parseUnary() {
        tokenizer.nextToken();
        LogicalObject res;
        switch (tokenizer.getToken()) {
            case OP_NEG:
                res = new Neg(parseUnary());
                break;
            case VAR:
                res = new Var(tokenizer.getLastVar());
                tokenizer.nextToken();
                break;
            case OPEN_BRACKET:
                res = parseFollow();
                tokenizer.nextToken();
                break;
            default:
                throw new IllegalArgumentException("unary parsing fail");
        }

        return res;
    }


}
