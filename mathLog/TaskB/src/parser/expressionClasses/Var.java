package parser.expressionClasses;

import java.util.Objects;

public class Var implements LogicalObject, Comparable<LogicalObject> {
    private final String value;

    public Var(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getOperationSymbol() {
        return "var";
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Var var = (Var) o;
        return Objects.equals(value, var.value);
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
