package parser.expressionClasses.variables;

import parser.expressionClasses.AbstractLogicalExpression;
import parser.expressionClasses.LogicalExpression;
import parser.expressionClasses.any.AbstractAny;
import parser.expressionClasses.any.AnyTerm;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

public abstract class AbstractVariable extends AbstractLogicalExpression {
    private final String name;

    public AbstractVariable(String value) {
        this.name = value;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean matchWithImplNext(LogicalExpression expression, Map<String, AbstractAny> substitutions) {
        return equals(expression);
    }

    @Override
    protected boolean extractTermAsImplNext(LogicalExpression expression, SpecificVar specificVar, AnyTerm substitution) {
        return equals(expression);
    }

    @Override
    protected boolean safeEquals(Object o) {
        AbstractVariable other = (AbstractVariable) o;
        return name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
