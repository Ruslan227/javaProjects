package parser.expressionClasses.variables;

import parser.expressionClasses.LogicalExpression;
import parser.expressionClasses.Var;
import parser.expressionClasses.any.AnyTerm;

import java.util.Set;

public class SpecificVar extends AbstractVariable implements Var {
    public SpecificVar(String name) {
        super(name);
    }

    @Override
    public void getAllVarsImpl(Set<SpecificVar> specificVars) {
        specificVars.add(this);
    }

    @Override
    public void getBoundedVarsImpl(Set<SpecificVar> boundedVars, Set<SpecificVar> freeVars) {
        if (!boundedVars.contains(this)) {
            freeVars.add(this);
        }
    }

    @Override
    public boolean isTerm() {
        return true;
    }
}
