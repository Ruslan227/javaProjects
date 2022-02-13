package detector.rules;

import detector.MatchStatus;
import detector.Pair;
import detector.annotations.PredicateAxiomAnnotation;
import parser.Parser;
import parser.expressionClasses.LogicalExpression;

import java.util.Map;

public class PredicateAxiomRule extends PredicateAxiomAnnotation implements Rule {
    protected final LogicalExpression axiom;

    public PredicateAxiomRule(String strAxiom, String axiomNumber) {
        super(axiomNumber);
        axiom = new Parser(true).parse(strAxiom);
    }

    @Override
    public Pair<MatchStatus, String> match(LogicalExpression expression, int row, Map<LogicalExpression, Integer> proved) {
        if (expression.matchWith(axiom) != null) {
            return new Pair<>(MatchStatus.TRUE, annotate(row));
        } else {
            return new Pair<>(MatchStatus.FALSE);
        }
    }
}
