package parser.expressionClasses.variables;

import parser.expressionClasses.LogicalExpression;
import parser.expressionClasses.any.AnyTerm;

import java.util.Set;

public class Predicate extends AbstractVariable {
    public Predicate(String name) {
        super(name);
    }

    @Override
    public void getAllVarsImpl(Set<SpecificVar> specificVars) {
    }

    @Override
    public void getBoundedVarsImpl(Set<SpecificVar> boundedVars, Set<SpecificVar> freeVars) {
    }

    @Override
    public boolean isTerm() {
        return false;
    }
}
