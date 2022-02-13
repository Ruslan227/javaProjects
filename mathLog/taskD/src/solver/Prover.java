package solver;

import detector.Pair;
import parser.Parser;
import parser.expressionClasses.LogicalExpression;
import parser.expressionClasses.Zero;
import parser.expressionClasses.any.AbstractAny;
import parser.expressionClasses.binary.Add;
import parser.expressionClasses.binary.Mul;
import parser.expressionClasses.unary.Apostrophe;
import parser.expressionClasses.variables.SpecificVar;

import java.util.*;
import java.util.stream.Stream;

import static parser.expressionClasses.LogicalExpression.extractAnyByName;
import static solver.Utils.mergeStreams;

public class Prover {
    public final static LogicalExpression TWO = Utils.getNumber(2);
    public final static LogicalExpression ZERO = new Zero();

    public final static Parser parser = new Parser(true);

    public static Stream<String> cycleOut(LogicalExpression x, LogicalExpression y) {
        // 2*x=y
        LogicalExpression e_2x = new Mul(TWO, x);
        LogicalExpression e_2xPlus0 = new Add(e_2x, ZERO);
        // 2*x+0=2*x
        var part1 = mergeStreams(
                addUniversalQuantifiers("a+0=a", "a"),
                sub("@a.a+0=a", e_2x)
        );
        // 2*x=2*x+0
        var part2 = proofBEqAFromUniversalAEqBFollowBEqA(e_2xPlus0, e_2x);
        // 2*x+0=y
        var part3 = proofBEqCFromA2(e_2x, e_2xPlus0, y);
        // 2*x+2=[y+2]
        Stream<String> part4 = Stream.empty();
        for (int i = 0; i < 2; i++) {
            part4 = Stream.concat(part4, cycleIn(x, y, i));
        }
        // (2*x'=2*x+2)
        var part5 = mergeStreams(
                addUniversalQuantifiers("(a*b'=a*b+a)", "a", "b"),
                sub("@a.@b.a*b'=a*b+a", TWO, x)
        );
        // 2*x+2=2*x'
        var part6 = proofBEqAFromUniversalAEqBFollowBEqA(
                new Mul(TWO, new Apostrophe(x)),
                new Add(e_2x, TWO)
        );
        // 2*x' = [y+2]
        var part7 = proofBEqCFromA2(
                new Add(e_2x, TWO),
                new Mul(TWO, new Apostrophe(x)),
                Utils.addApostrophes(y, 2)
        );
        return mergeStreams(part1, part2, part3, part4, part5, part6, part7);
    }

    public static Stream<String> cycleIn(LogicalExpression x, LogicalExpression y, int i) {
        // 2*x+i=[y+i]
        LogicalExpression e_2x = new Mul(TWO, x); // 2*x
        LogicalExpression e_i = Utils.getNumber(i); // i
        LogicalExpression e_yi = Utils.addApostrophes(y, i); // [y+i]
        // (2*x+i)'= [y+i]'
        var part1 = mergeStreams(
                addUniversalQuantifiers("a=b->a'=b'", "a", "b"),
                sub("@a.@b.a=b->a'=b'", new Add(e_2x, e_i), e_yi),
                Stream.of(String.format("%s' = %s'", new Add(e_2x, e_i), e_yi))
        );
        // 2*x+i'=(2*x+i)'
        var part2 = mergeStreams(
                addUniversalQuantifiers("a+b'=(a+b)'", "a", "b"),
                sub("@a.@b.a+b'=(a+b)'", e_2x, e_i)
        );
        // (2*x+i)'= (2*x+i')
        var part3 = proofBEqAFromUniversalAEqBFollowBEqA(
                new Add(e_2x, new Apostrophe(e_i)), // a = (2*x+i')
                new Apostrophe(new Add(e_2x, e_i))  // b = (2*x+i)'
        );
        // (2*x+i') = [y+i]
        var part4 = proofBEqCFromA2(
                new Apostrophe(new Add(e_2x, e_i)), // a = (2*x+i)'
                new Add(e_2x, new Apostrophe(e_i)), // b = (2*x+i')
                new Apostrophe(e_yi)                // c = [y+i]'
        );
        return mergeStreams(part1, part2, part3, part4);
    }

    public static Stream<String> proofBEqAFromUniversalAEqBFollowBEqA(LogicalExpression a, LogicalExpression b) {
        return mergeStreams(sub("(@a.@b.(a=b->b=a))", a, b), Stream.of(b + "=" + a));
    }

    public static Stream<String> proofBEqCFromA2(LogicalExpression a, LogicalExpression b, LogicalExpression c) {
        return mergeStreams(
                addUniversalQuantifiers("a=b->a=c->b=c", "a", "b", "c"),
                sub("@a.@b.@c.a=b->a=c->b=c", a, b, c),
                Stream.of(a + "=" + c + "->" + b + "=" + c),
                Stream.of(b + "=" + c)
        );
    }

    // @a.@b.(a=b->b=a)
    public static Stream<String> universalAEqBFollowBEqA() {
        return mergeStreams(
                addUniversalQuantifiers("(a=b->a=c->b=c)", "a", "b", "c"),
                sub("@a.@b.@c.(a=b->a=c->b=c)", "a+0", "a", "a"),
                Stream.of("(a+0)=a", "(a+0)=a->a=a", "a=a"),
                sub("@c.(a=b->a=c->b=c)", "a"),
                Stream.of(
                        "(a=b->a=a) -> (a=b->a=a->b=a) -> (a=b->b=a)",
                        "a=a->a=b->a=a",
                        "a=b->a=a",
                        "(a=b->a=a->b=a) -> (a=b->b=a)",
                        "(a=b->b=a)"
                ),
                addUniversalQuantifiers("(a=b->b=a)", "a", "b")
        );
    }

    // Добавить к expr кванторы (vars)
    public static Stream<String> addUniversalQuantifiers(String expr, String... vars) {
        String proved = "(0=0->0=0->0=0)";
        expr = parser.parse(expr).toString();
        List<String> proof = new ArrayList<>(List.of(
                proved,
                expr,
                expr + "->" + proved + "->" + expr,
                proved + "->" + expr
        ));
        String prev = expr;
        Collections.reverse(Arrays.asList(vars));
        for (String var : vars) {
            String cur = String.format("(@%s.%s)", var, prev);
            proof.add(proved + "->" + cur);
            prev = cur;
        }
        proof.add(prev);
        return proof.stream();
    }

    // При условии, что expr был раньше доказан - доказывает подстановку subs в expr
    public static Stream<String> sub(String expr, String... subs) {
        Stream<String> res = Stream.empty();
        String prev = expr;
        for (String sub : subs) {
            var proofAndLast = reduceQuantifier(prev, sub);
            res = Stream.concat(res, proofAndLast.getFirst());
            prev = proofAndLast.getSecond();
        }
        return res;
    }

    public static Stream<String> sub(String expr, LogicalExpression... subs) {
        return sub(expr, Arrays.stream(subs).map(Object::toString).toArray(String[]::new));
    }

    private static Pair<Stream<String>, String> reduceQuantifier(String cur, String sub) {
        LogicalExpression curExpr = parser.parse(cur);
        cur = curExpr.toString();

        LogicalExpression toMatch = parser.parse("@~x.#a");
        Set<AbstractAny> anySet = curExpr.matchWith(toMatch);
        if (anySet == null) {
            throw new RuntimeException("input doesn't match @~x.#a");
        }

        SpecificVar x = (SpecificVar) extractAnyByName(anySet, "~x");
        LogicalExpression a = extractAnyByName(anySet, "#a");
        String xName = x.toString();

        LogicalExpression subExpr = parser.parse(a.toString().replaceAll(xName, "\\$" + xName));
        subExpr.substitute(Map.of("$" + xName, parser.parse(sub, true)));
        String res = subExpr.toString();

        return new Pair<>(Stream.of(cur, cur + "->" + res, res), res);
    }

}
