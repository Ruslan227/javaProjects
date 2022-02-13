package detector;

import detector.rules.*;
import parser.Parser;
import parser.ParserException;
import parser.expressionClasses.LogicalExpression;
import parser.expressionClasses.Zero;
import parser.expressionClasses.any.AbstractAny;
import parser.expressionClasses.unary.Apostrophe;
import parser.expressionClasses.variables.SpecificVar;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static parser.expressionClasses.LogicalExpression.extractAnyByName;

public class MainDetector {
    private final static List<Rule> BEFORE_MP, AFTER_MP;

    static {
        BEFORE_MP = new ArrayList<>();
        // Gilbert
        BEFORE_MP.add(new PredicateAxiomRule("#a -> #b -> #a", "1"));
        BEFORE_MP.add(new PredicateAxiomRule("(#a -> #b) -> (#a -> #b -> #c) -> (#a -> #c)", "2"));
        BEFORE_MP.add(new PredicateAxiomRule("#a -> #b -> #a & #b", "3"));
        BEFORE_MP.add(new PredicateAxiomRule("#a & #b -> #a", "4"));
        BEFORE_MP.add(new PredicateAxiomRule("#a & #b -> #b", "5"));
        BEFORE_MP.add(new PredicateAxiomRule("#a -> #a | #b", "6"));
        BEFORE_MP.add(new PredicateAxiomRule("#b -> #a | #b", "7"));
        BEFORE_MP.add(new PredicateAxiomRule("(#a -> #c) -> (#b -> #c) -> (#a | #b -> #c)", "8"));
        BEFORE_MP.add(new PredicateAxiomRule("(#a -> #b) -> (#a -> !#b) -> !#a", "9"));
        BEFORE_MP.add(new PredicateAxiomRule("!!#a -> #a", "10"));
        // Predicate axioms with freedom
        BEFORE_MP.add(new PredicateFreedomAxiomRule("(@~x.#a) -> #a-bounded", "11", "@"));
        BEFORE_MP.add(new PredicateFreedomAxiomRule("#a-bounded -> ?~x.#a", "12", "?"));
        // A9
        BEFORE_MP.add(new PredicateAxiomRule("(#b) & (@~x.#a -> (#c)) -> #a", "A9") {
            @Override
            public Pair<MatchStatus, String> match(LogicalExpression expression, int row, Map<LogicalExpression, Integer> proved) {
                Set<AbstractAny> anySet = expression.matchWith(axiom);
                if (anySet == null) {
                    return new Pair<>(MatchStatus.FALSE);
                }

                SpecificVar x = (SpecificVar) extractAnyByName(anySet, "~x", axiomNumber);
                LogicalExpression a = extractAnyByName(anySet, "#a", axiomNumber);
                LogicalExpression b = extractAnyByName(anySet, "#b", axiomNumber);
                LogicalExpression c = extractAnyByName(anySet, "#c", axiomNumber);

                if (!a.getFreeVars().contains(x) || // x входит свободно в a
                        b.extractTermAs(a, x).getClass() != Zero.class || // b = a[x:=0]
                        !c.extractTermAs(a, x).equals(new Apostrophe(x)) // c = a[x:=x']
                ) {
                    return new Pair<>(MatchStatus.FALSE);
                }
                return new Pair<>(MatchStatus.TRUE, annotate(row));
            }
        });
        // Formal '
        BEFORE_MP.add(new FormalAxiomRule("a = b -> a' = b'", "A1"));
        BEFORE_MP.add(new FormalAxiomRule("a = b -> a = c -> b = c", "A2"));
        BEFORE_MP.add(new FormalAxiomRule("a' = b' -> a = b", "A3"));
        BEFORE_MP.add(new FormalAxiomRule("!a' = 0", "A4"));
        BEFORE_MP.add(new FormalAxiomRule("a + b' = (a + b)'", "A5"));
        BEFORE_MP.add(new FormalAxiomRule("a + 0 = a", "A6"));
        BEFORE_MP.add(new FormalAxiomRule("a * 0 = 0", "A7"));
        BEFORE_MP.add(new FormalAxiomRule("a * b' = a * b + a", "A8"));
    }

    static {
        AFTER_MP = new ArrayList<>();
        // Inference rules
        AFTER_MP.add(new InferencePredicateAxiomRule("(?~x.#b) -> #a", "?"));
        AFTER_MP.add(new InferencePredicateAxiomRule("#a -> @~x.#b", "@"));
    }

    public static List<String> annotateProof(List<String> proof) {
        MPRule mpRule = new MPRule();
        List<Rule> rules = Stream.concat(BEFORE_MP.stream(), Stream.concat(Stream.of(mpRule), AFTER_MP.stream()))
                .collect(Collectors.toList());

        Parser parser = new Parser();

        String[] parts = proof.get(0).split("\\|-");
        if (parts.length != 2) {
            throw new ParserException("Incorrect start of proof");
        }
        LogicalExpression toProve = parser.parse(parts[1]);

        List<String> annotatedProof = new ArrayList<>();
        annotatedProof.add("|-" + toProve.toString());

        Map<LogicalExpression, Integer> getRowByProvedExpression = new HashMap<>();
        LogicalExpression current = null;
        MatchStatus currentStatus = null;

        for (int i = 1; i < proof.size(); i++) {
            current = parser.parse(proof.get(i));

            currentStatus = MatchStatus.FALSE;
            for (Rule rule : rules) {
                Pair<MatchStatus, String> matchResult = rule.match(current, i, getRowByProvedExpression);
                ;
                currentStatus = matchResult.getFirst();
                if (currentStatus == MatchStatus.TRUE) {
                    mpRule.putProved(current, i);
                    annotatedProof.add(matchResult.getSecond() + " " + current.toString());
                    getRowByProvedExpression.putIfAbsent(current, i);
                    break;
                } else if (currentStatus == MatchStatus.NO_FREEDOM) {
                    annotatedProof.add(matchResult.getSecond());
                    break;
                }
            }
            if (currentStatus != MatchStatus.TRUE) {
                if (currentStatus == MatchStatus.FALSE) {
                    annotatedProof.add(String.format("Expression %d is not proved.", i));
                }
                break;
            }
        }
        if (currentStatus == MatchStatus.TRUE && !toProve.equals(current)) {
            annotatedProof.add("The proof proves different expression.");
        }
        return annotatedProof;
    }

    public static void main(String[] args) throws IOException {
        List<String> proof = new ArrayList<>();
        try (var reader = new BufferedReader(new InputStreamReader(System.in))) {
            String line = reader.readLine();
            while (line != null) {
                if (!line.isBlank() && !line.isEmpty()) {
                    proof.add(line);
                }
                line = reader.readLine();
            }
        }

        try (var writer = new BufferedWriter(new OutputStreamWriter(System.out))) {
            try {
                for (String t : annotateProof(proof)) {
                    writer.write(t);
                    writer.newLine();
                }
            } catch (ParserException e) {
                System.err.println("Incorrect input " + e.getMessage());
            }
        }
    }

}
