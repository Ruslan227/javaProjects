package output.vertex;

import parser.expressionClasses.LogicalObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VertexValue {
    private final LogicalObject expression;
    private final int num;
    private final String ruleName;
    private ArrayList<LogicalObject> hypothesis;

    public VertexValue(ArrayList<LogicalObject> hypothesis, LogicalObject expression, int num, String ruleName) {
        this.hypothesis = hypothesis;
        this.expression = expression;
        this.num = num;
        this.ruleName = ruleName;
    }

    public void addHypothesis(List<LogicalObject> hyp) {
        hypothesis.addAll(hyp);
    }

    public ArrayList<LogicalObject> getHypothesis() {
        return hypothesis;
    }

    public void setHypothesis(ArrayList<LogicalObject> hypothesis) {
        this.hypothesis = hypothesis;
    }

    public LogicalObject getExpression() {
        return expression;
    }

    public int getNum() {
        return num;
    }

    public String getRuleName() {
        return ruleName;
    }

    @Override
    public String toString() {
        return "[" + getNum() + "] " +
                getHypothesis().stream().map(Object::toString).collect(Collectors.joining(",")) +
                "|-" + getExpression() + " [" +
                getRuleName() + "]";
    }
}
