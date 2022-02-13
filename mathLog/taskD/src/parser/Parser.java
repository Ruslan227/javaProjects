package parser;

import parser.expressionClasses.LogicalExpression;
import parser.expressionClasses.Var;
import parser.expressionClasses.Zero;
import parser.expressionClasses.any.AnyExpression;
import parser.expressionClasses.any.AnyTerm;
import parser.expressionClasses.any.AnyVar;
import parser.expressionClasses.binary.*;
import parser.expressionClasses.quantifier.ExistantialQuantifier;
import parser.expressionClasses.quantifier.UniversalQuantifier;
import parser.expressionClasses.unary.Apostrophe;
import parser.expressionClasses.unary.Neg;
import parser.expressionClasses.variables.Predicate;
import parser.expressionClasses.variables.SpecificVar;
import parser.tokens.Token;
import parser.tokens.Tokenizer;

import java.util.Collections;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class Parser {
    private final boolean isAnySupported;
    private Tokenizer tokenizer;
    private boolean isTerm = false;

    public Parser(boolean isAnySupported) {
        this.isAnySupported = isAnySupported;
    }

    public Parser() {
        this(false);
    }

    public LogicalExpression parse(String expression) {
        return parse(expression, false);
    }

    public LogicalExpression parse(String expression, boolean isTerm) {
        this.isTerm = isTerm;
        tokenizer = new Tokenizer(
                expression,
                isAnySupported ? Collections.emptySet() : Set.of(Token.ANY_EXPR, Token.ANY_TERM)
        );
        tokenizer.nextToken();
        return parseFollow();
    }

    private LogicalExpression parseLeftAssoc(final Token token,
                                             Supplier<LogicalExpression> nextLevel,
                                             BiFunction<LogicalExpression, LogicalExpression, LogicalExpression> constructor) {

        LogicalExpression res = nextLevel.get();
        while (tokenizer.getToken() == token) {
            tokenizer.nextToken();
            res = constructor.apply(res, nextLevel.get());
        }
        return res;
    }

    private LogicalExpression parseRightAssoc(final Token token,
                                              Supplier<LogicalExpression> nextLevel,
                                              BiFunction<LogicalExpression, LogicalExpression, LogicalExpression> constructor) {

        LogicalExpression res = nextLevel.get();
        while (tokenizer.getToken() == token) {
            tokenizer.nextToken();
            res = constructor.apply(res, parseRightAssoc(token, nextLevel, constructor));
        }
        return res;
    }

    private LogicalExpression parseFollow() {
        return parseRightAssoc(Token.OP_FOLLOW, this::parseDis, Follow::new);
    }

    private LogicalExpression parseDis() {
        return parseLeftAssoc(Token.OP_OR, this::parseConj, Dis::new);
    }

    private LogicalExpression parseConj() {
        return parseLeftAssoc(Token.OP_AND, this::parseUnary, Conj::new);
    }

    private LogicalExpression parseUnary() {
        switch (tokenizer.getToken()) {
            case OP_NEG:
                tokenizer.nextToken();
                return new Neg(parseUnary());
            case OPEN_BRACKET:
                if (isTerm || checkBracketsIsTerm()) {
                    return parsePredicate();
                }
                tokenizer.nextToken();
                LogicalExpression expr = parseFollow();
                tokenizer.nextToken();
                return expr;
            case UNIVERSAL:
            case EXIST:
                Token qToken = tokenizer.getToken();
                tokenizer.nextToken();
                Var var = parseExpectVar();
                if (tokenizer.getToken() != Token.DOT) {
                    throw new ParserException("Expected dot");
                }
                tokenizer.nextToken();
                LogicalExpression exprQ = parseFollow();
                return qToken == Token.EXIST ? new ExistantialQuantifier(var, exprQ) : new UniversalQuantifier(var, exprQ);
            default:
                return parsePredicate();
        }
    }

    private LogicalExpression parsePredicate() {
        switch (tokenizer.getToken()) {
            case PREDICATE:
                return parseExpectPredicate();
            case ANY_EXPR:
                var expr = new AnyExpression(tokenizer.getTokenValue());
                tokenizer.nextToken();
                return expr;
            default:
                LogicalExpression term1 = parseTerm();
                if (tokenizer.getToken() == Token.END || tokenizer.getToken() == Token.CLOSE_BRACKET) { // for task d
                    return term1;
                }
                if (tokenizer.getToken() != Token.EQ) {
                    throw new ParserException("Expected =");
                }
                tokenizer.nextToken();
                LogicalExpression term2 = parseTerm();
                return new Eq(term1, term2);
        }
    }


    private boolean checkBracketsIsTerm() {
        var checkTokenizer = new Tokenizer(tokenizer);
        int balance = 1;
        while (balance != 0 && checkTokenizer.getToken() != Token.UNKNOWN && checkTokenizer.getToken() != Token.END) {
            checkTokenizer.nextToken();
            if (checkTokenizer.getToken() == Token.OPEN_BRACKET) {
                balance++;
            }
            if (checkTokenizer.getToken() == Token.CLOSE_BRACKET) {
                balance--;
            }
        }
        if (balance != 0) {
            throw new ParserException("Expected ) after (");
        }
        checkTokenizer.nextToken();
        return checkTokenizer.getToken() == Token.EQ || checkTokenizer.getToken() == Token.APOSTROPHE;
    }

    private LogicalExpression parseTerm() {
        return parseLeftAssoc(Token.ADD, this::parseSummand, Add::new);
    }

    private LogicalExpression parseSummand() {
        return parseLeftAssoc(Token.MUL, this::parseMultiplying, Mul::new);
    }

    private LogicalExpression parseMultiplying() {
        LogicalExpression res;
        switch (tokenizer.getToken()) {
            case ANY_VAR:
            case SPECIFIC_VAR:
                res = parseExpectVar();
                break;
            case OPEN_BRACKET:
                tokenizer.nextToken();
                res = parseTerm();
                if (tokenizer.getToken() != Token.CLOSE_BRACKET) {
                    throw new ParserException("Expected )");
                }
                tokenizer.nextToken();
                break;
            case ANY_TERM:
                res = new AnyTerm(tokenizer.getTokenValue());
                tokenizer.nextToken();
                break;
            case ZERO:
                tokenizer.nextToken();
                res = new Zero();
                break;
            default:
                throw new ParserException("Invalid input");
        }

        while (tokenizer.getToken() == Token.APOSTROPHE) {
            res = new Apostrophe(res);
            tokenizer.nextToken();
        }

        return res;
    }

    private Var parseExpectVar() {
        if (tokenizer.getToken() == Token.SPECIFIC_VAR) {
            var res = new SpecificVar(tokenizer.getTokenValue());
            tokenizer.nextToken();
            return res;
        }
        if (isAnySupported && tokenizer.getToken() == Token.ANY_VAR) {
            var res = new AnyVar(tokenizer.getTokenValue());
            tokenizer.nextToken();
            return res;
        }
        throw new ParserException("Expected variable");
    }

    private Predicate parseExpectPredicate() {
        if (tokenizer.getToken() == Token.PREDICATE) {
            var res = new Predicate(tokenizer.getTokenValue());
            tokenizer.nextToken();
            return res;
        }
        throw new ParserException("Expected predicate");
    }
}
