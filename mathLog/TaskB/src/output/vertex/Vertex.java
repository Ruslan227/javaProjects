package output.vertex;

import parser.expressionClasses.LogicalObject;

import java.util.List;

public class Vertex {
    private final VertexValue value;

    private Vertex child1 = null;
    private Vertex child2 = null;
    private Vertex child3 = null;

    public Vertex(VertexValue value) {
        this.value = value;
    }

    public void propogateHyp(List<LogicalObject> hyp) {
        value.addHypothesis(hyp);
        if (child1 != null)
            child1.propogateHyp(hyp);
        if (child2 != null)
            child2.propogateHyp(hyp);
        if (child3 != null)
            child3.propogateHyp(hyp);
    }

    public VertexValue getValue() {
        return value;
    }

    public Vertex getChild1() {
        return child1;
    }

    public void setChild1(Vertex child1) {
        this.child1 = child1;
    }

    public Vertex getChild2() {
        return child2;
    }

    public void setChild2(Vertex child2) {
        this.child2 = child2;
    }

    public Vertex getChild3() {
        return child3;
    }

    public void setChild3(Vertex child3) {
        this.child3 = child3;
    }
}
