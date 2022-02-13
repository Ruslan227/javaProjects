package parser.expressionClasses;

import java.util.Objects;

public class Neg implements LogicalObject, Comparable<LogicalObject> {
    private LogicalObject value = null;

    public Neg() {
    }

    public Neg(LogicalObject value) {
        this.value = value;
    }

    public LogicalObject getValue() {
        return value;
    }

    public String getOperationSymbol() {
        return "neg";
    }

//    @Override
//    public String toString() {
//        return "!" + value.toString();
//    }

    @Override
    public String toString() {
        return (value == null)? "_|_" : "(" + value.toString() + "->" + "_|_)";
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Neg neg = (Neg) o;
        return Objects.equals(value, neg.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public int compareTo(LogicalObject o) {
        return hashCode() - o.hashCode();
    }
}
