package detector.rules;

import detector.MatchStatus;
import detector.Pair;
import detector.annotations.FormalAxiomAnnotation;
import parser.Parser;
import parser.expressionClasses.LogicalExpression;

import java.util.Map;

public class FormalAxiomRule extends FormalAxiomAnnotation implements Rule {
    private final LogicalExpression axiom;

    public FormalAxiomRule(String strAxiom, String axiomNumber) {
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
