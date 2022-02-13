package parser.expressionClasses.any;

import parser.expressionClasses.LogicalExpression;

public class AnyExpression extends AbstractAny {
    public AnyExpression(String name) {
        super(name);
    }

    public AnyExpression(String name, LogicalExpression substitution) {
        super(name, substitution);
    }

    @Override
    protected boolean checkSubstitution(LogicalExpression substitution) {
        return substitution != null;
    }
}
