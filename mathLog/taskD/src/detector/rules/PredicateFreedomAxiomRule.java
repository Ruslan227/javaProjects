package detector.rules;

import detector.MatchStatus;
import detector.Pair;
import parser.expressionClasses.LogicalExpression;
import parser.expressionClasses.any.AbstractAny;
import parser.expressionClasses.variables.SpecificVar;

import java.util.Map;
import java.util.Set;

import static parser.expressionClasses.LogicalExpression.extractAnyByName;

public class PredicateFreedomAxiomRule extends PredicateAxiomRule {
    private final String type;

    public PredicateFreedomAxiomRule(String strAxiom, String axiomNumber, String type) {
        super(strAxiom, axiomNumber);
        this.type = type;
    }

    @Override
    public Pair<MatchStatus, String> match(LogicalExpression expression, int row, Map<LogicalExpression, Integer> proved) {
        Set<AbstractAny> anySet = expression.matchWith(axiom);
        if (anySet == null) {
            return new Pair<>(MatchStatus.FALSE);
        }

        SpecificVar x = (SpecificVar) extractAnyByName(anySet, "~x", axiomNumber);
        LogicalExpression a = extractAnyByName(anySet, "#a", axiomNumber);
        LogicalExpression abounded = extractAnyByName(anySet, "#a-bounded", axiomNumber);

        LogicalExpression y = abounded.extractTermAs(a, x);
        if (y == null) {
            return new Pair<>(MatchStatus.FALSE);
        }
        Set<SpecificVar> bounded = abounded.getBoundedVars();
        for (SpecificVar specificVar : y.getAllVars()) {
            if (bounded.contains(specificVar)) {
                return new Pair<>(
                        MatchStatus.NO_FREEDOM,
                        String.format(
                                "Expression %d: variable %s is not free for term %s in %s-axiom.", row, x, y, type
                        )
                );
            }
        }
        return new Pair<>(MatchStatus.TRUE, annotate(row));
    }
}
