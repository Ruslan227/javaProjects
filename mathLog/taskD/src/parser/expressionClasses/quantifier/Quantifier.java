package parser.expressionClasses.quantifier;

import parser.expressionClasses.AbstractLogicalExpression;
import parser.expressionClasses.LogicalExpression;
import parser.expressionClasses.Var;
import parser.expressionClasses.any.AbstractAny;
import parser.expressionClasses.any.AnyTerm;
import parser.expressionClasses.variables.SpecificVar;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

public abstract class Quantifier extends AbstractLogicalExpression {
    private final String qSymbol;
    private final Var var;
    private final LogicalExpression expr;

    public Quantifier(Var var, LogicalExpression expr, String qSymbol) {
        this.var = var;
        this.expr = expr;
        this.qSymbol = qSymbol;
    }

    public Var getVar() {
        return var;
    }

    public LogicalExpression getExpr() {
        return expr;
    }

    @Override
    public void getAllVarsImpl(Set<SpecificVar> specificVars) {
        var.getAllVarsImpl(specificVars);
        expr.getAllVarsImpl(specificVars);
    }

    @Override
    public void getBoundedVarsImpl(Set<SpecificVar> boundedVars, Set<SpecificVar> freeVars) {
        if (!(var instanceof SpecificVar)) {
            throw new RuntimeException("Incorrect quantifier -> getBoundedVarsImpl");
        }
        boundedVars.add((SpecificVar) var);
        expr.getBoundedVarsImpl(boundedVars, freeVars);
        boundedVars.removeAll(freeVars);
    }

    @Override
    protected boolean matchWithImplNext(LogicalExpression expression, Map<String, AbstractAny> substitutions) {
        Quantifier quantifier = (Quantifier) expression;
        return var.matchWithImpl(quantifier.getVar(), substitutions) &&
                expr.matchWithImpl(quantifier.getExpr(), substitutions);
    }

    @Override
    protected boolean extractTermAsImplNext(LogicalExpression expression, SpecificVar specificVar, AnyTerm substitution) {
        Quantifier quantifier = (Quantifier) expression;
        if (var.equals(specificVar)) {
            return true;
        }
        return var.extractTermAsImpl(quantifier.getVar(), specificVar, substitution) &&
                expr.extractTermAsImpl(quantifier.getExpr(), specificVar, substitution);
    }

    @Override
    public boolean substitute(Map<String, LogicalExpression> subByName) {
        return var.substitute(subByName) && expr.substitute(subByName);
    }

    @Override
    public boolean isTerm() {
        return false;
    }

    @Override
    public String toString() {
        return "(" + qSymbol + var.toString() + "." + expr.toString() + ")";
    }

    @Override
    public boolean safeEquals(Object o) {
        Quantifier that = (Quantifier) o;
        return var.equals(that.var) && expr.equals(that.expr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(var, expr, qSymbol);
    }
}
