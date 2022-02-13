package detector.rules;

import detector.MatchStatus;
import detector.Pair;
import parser.expressionClasses.LogicalExpression;
import parser.expressionClasses.any.AbstractAny;
import parser.expressionClasses.binary.Follow;
import parser.expressionClasses.variables.SpecificVar;

import java.util.Map;
import java.util.Set;

import static parser.expressionClasses.LogicalExpression.extractAnyByName;

public class InferencePredicateAxiomRule extends PredicateAxiomRule {

    public InferencePredicateAxiomRule(String strAxiom, String axiomNumber) {
        super(strAxiom, axiomNumber);
    }

    @Override
    public Pair<MatchStatus, String> match(LogicalExpression expression, int row, Map<LogicalExpression, Integer> proved) {
        Set<AbstractAny> anySet = expression.matchWith(axiom);
        if (anySet == null) {
            return new Pair<>(MatchStatus.FALSE);
        }

        SpecificVar x = (SpecificVar) extractAnyByName(anySet, "~x", axiomNumber);
        LogicalExpression a = extractAnyByName(anySet, "#a", axiomNumber);
        LogicalExpression b = extractAnyByName(anySet, "#b", axiomNumber);

        Integer prevRow = proved.get(axiomNumber.equals("@") ? new Follow(a, b) : new Follow(b, a));
        if (prevRow == null) {
            return new Pair<>(MatchStatus.FALSE);
        }

        if (a.getFreeVars().contains(x)) {
            return new Pair<>(
                    MatchStatus.NO_FREEDOM,
                    String.format("Expression %d: variable %s occurs free in %s-rule.", row, x, axiomNumber)
            );
        }
        return new Pair<>(MatchStatus.TRUE, String.format("[%d. %s-intro %d]", row, axiomNumber, prevRow));
    }
}
