package parser.expressionClasses.quantifier;

import parser.expressionClasses.LogicalExpression;
import parser.expressionClasses.Var;
import parser.expressionClasses.variables.SpecificVar;

public class UniversalQuantifier extends Quantifier {
    public UniversalQuantifier(Var var, LogicalExpression expr) {
        super(var, expr, "@");
    }
}
