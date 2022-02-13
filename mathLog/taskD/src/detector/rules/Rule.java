package detector.rules;

import detector.MatchStatus;
import detector.Pair;
import detector.annotations.Annotation;
import parser.expressionClasses.LogicalExpression;

import java.util.Map;

public interface Rule extends Annotation {
    Pair<MatchStatus, String> match(LogicalExpression expression, int row, Map<LogicalExpression, Integer> proved);
}
