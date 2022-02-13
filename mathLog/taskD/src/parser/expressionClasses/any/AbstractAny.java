package parser.expressionClasses.any;

import parser.expressionClasses.AbstractLogicalExpression;
import parser.expressionClasses.LogicalExpression;
import parser.expressionClasses.variables.SpecificVar;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

public abstract class AbstractAny extends AbstractLogicalExpression {
    private final String name;
    private LogicalExpression substitution;

    public AbstractAny(String name) {
        this(name, null);
    }

    public AbstractAny(String name, LogicalExpression substitution) {
        this.name = name;
        this.substitution = substitution;
    }

    public boolean substitute(LogicalExpression substitution) {
        if (checkSubstitution(substitution)) {
            this.substitution = substitution;
            return true;
        }
        this.substitution = null;
        return false;
    }

    public String getName() {
        return name;
    }

    public LogicalExpression getSubstitution() {
        return substitution;
    }

    protected abstract boolean checkSubstitution(LogicalExpression substitution);

    @Override
    public void getAllVarsImpl(Set<SpecificVar> specificVars) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void getBoundedVarsImpl(Set<SpecificVar> boundedVars, Set<SpecificVar> freeVars) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected boolean matchWithImplNext(LogicalExpression expression, Map<String, AbstractAny> substitutions) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected boolean extractTermAsImplNext(LogicalExpression expression, SpecificVar specificVar, AnyTerm substitution) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean substitute(Map<String, LogicalExpression> subByName) {
        LogicalExpression sub = subByName.get(name);
        if (sub != null) {
            return substitute(sub);
        }
        return substitution == null || substitution.substitute(subByName);
    }

    @Override
    public boolean isTerm() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return substitution == null ? name : substitution.toString();
    }

    @Override
    protected boolean safeEquals(Object o) {
        AbstractAny other = (AbstractAny) o;
        return name.equals(other.name) && Objects.equals(substitution, other.substitution);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, substitution);
    }
}
