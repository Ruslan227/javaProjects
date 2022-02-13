package output.axiomsTreeGenerators;

import com.sun.jdi.ArrayReference;
import output.vertex.Vertex;
import output.vertex.VertexValue;
import parser.expressionClasses.Conj;
import parser.expressionClasses.FollowExpr;
import parser.expressionClasses.LogicalObject;
import parser.expressionClasses.Neg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TreeGeneratorAxiom {

    // return last children (child)
    public List<Vertex> generateTreeAx1(Vertex parent) {

        var child1Lvl1 = turnstileRight(parent, "I->");
        var child1Lvl2 = turnstileRight(child1Lvl1, "Ax");

        return Collections.singletonList(child1Lvl2);
    }

    public Vertex turnstileRight(Vertex parent, String ruleName) {
        var expr = (FollowExpr) parent.getValue().getExpression();
        LogicalObject firstBrackets = expr.getArg1();

        LogicalObject tail = expr.getArg2();

        var hyp = new ArrayList<>(parent.getValue().getHypothesis());
        hyp.add(firstBrackets);

        var child1Value = new VertexValue(hyp,
                tail,
                parent.getValue().getNum() + 1,
                ruleName);

        var child1 = new Vertex(child1Value);
        parent.setChild1(child1);
        return child1;
    }

    // expr1 mp expr2 = parent
    public List<Vertex> generateTreeMP(Vertex parent,
                                       LogicalObject expr1,
                                       LogicalObject expr2,
                                       String ruleName1,
                                       String ruleName2) {
        var hyp1 = new ArrayList<>(parent.getValue().getHypothesis());
        var hyp2 = new ArrayList<>(parent.getValue().getHypothesis());
        VertexValue child1Value = new VertexValue(hyp1,
                expr1,
                parent.getValue().getNum() + 1,
                ruleName1);
        VertexValue child2Value = new VertexValue(hyp2,
                expr2,
                parent.getValue().getNum() + 1,
                ruleName2);

        Vertex child1 = new Vertex(child1Value);
        parent.setChild1(child1);
        Vertex child2 = new Vertex(child2Value);
        parent.setChild2(child2);

        return new ArrayList<>(Arrays.asList(child1, child2));
    }

    public List<Vertex> generateTreeAx2(Vertex parent) {

        var child1Lvl1 = turnstileRight(parent, "I->");
        var child1Lvl2 = turnstileRight(child1Lvl1, "I->");
        var child1Lvl3 = turnstileRight(child1Lvl2, "E->");

        FollowExpr expr = (FollowExpr) child1Lvl1.getValue().getExpression();
        FollowExpr br1 = (FollowExpr) expr.getArg1();
        LogicalObject betaImplGamma = br1.getArg2();
        FollowExpr expr1 = (FollowExpr) betaImplGamma;
        LogicalObject beta = expr1.getArg1();

        var r = generateTreeMP(child1Lvl3, betaImplGamma, beta, "E->", "E->");
        var child1Lvl4 = r.get(0);
        var child2Lvl4 = r.get(1);

        // expr.getArg1() - alpha beta gamma
        FollowExpr expr2 = (FollowExpr) child1Lvl2.getValue().getExpression();
        LogicalObject alpha = expr2.getArg1();
        FollowExpr expr3 = (FollowExpr) parent.getValue().getExpression();
        LogicalObject alphaImplBeta = expr3.getArg1();
        var res1 = generateTreeMP(child1Lvl4, expr.getArg1(), alpha, "Ax", "Ax");
        var res2 = generateTreeMP(child2Lvl4, alphaImplBeta, alpha, "Ax", "Ax");
        res1.addAll(res2);
        return res1;
    }

    public List<Vertex> generateTreeAx3(Vertex parent) {
        var child1Lvl1 = turnstileRight(parent, "I->");
        FollowExpr expr1 = (FollowExpr) child1Lvl1.getValue().getExpression();
        Conj alphaConjBeta = (Conj) expr1.getArg2();

        var hyp = new ArrayList<>(child1Lvl1.getValue().getHypothesis());
        hyp.add(expr1.getArg1());

        var child1ValueLvl2 = new VertexValue(hyp,
                alphaConjBeta,
                child1Lvl1.getValue().getNum() + 1,
                "I&");

        var child1Lvl2 = new Vertex(child1ValueLvl2);
        child1Lvl1.setChild1(child1Lvl2);

        Conj expr = (Conj) child1Lvl2.getValue().getExpression();
        LogicalObject alpha = expr.getArg1();
        LogicalObject beta = expr.getArg2();

        var child1ValueLvl3 = new VertexValue(new ArrayList<>(child1Lvl2.getValue().getHypothesis()),
                alpha,
                child1Lvl2.getValue().getNum() + 1,
                "Ax");
        var child1Lvl3 = new Vertex(child1ValueLvl3);
        child1Lvl2.setChild1(child1Lvl3);

        var child2ValueLvl3 = new VertexValue(new ArrayList<>(child1Lvl2.getValue().getHypothesis()),
                beta,
                child1Lvl2.getValue().getNum() + 1,
                "Ax");
        var child2Lvl3 = new Vertex(child2ValueLvl3);
        child1Lvl2.setChild2(child2Lvl3);

        return new ArrayList<>(Arrays.asList(child1Lvl3, child2Lvl3));
    }

    public List<Vertex> generateTreeAx4(Vertex parent) {
        var child1Lvl1 = turnstileRight(parent, "El&");
        FollowExpr expr = (FollowExpr) parent.getValue().getExpression();
        LogicalObject alphaConjBeta = expr.getArg1();

        var child1ValueLvl2 = new VertexValue(new ArrayList<>(child1Lvl1.getValue().getHypothesis()),
                alphaConjBeta,
                child1Lvl1.getValue().getNum() + 1,
                "Ax");

        var child1Lvl2 = new Vertex(child1ValueLvl2);
        child1Lvl1.setChild1(child1Lvl2);

        return Collections.singletonList(child1Lvl2);
    }

    public List<Vertex> generateTreeAx5(Vertex parent) {
        var child1Lvl1 = turnstileRight(parent, "Er&");
        FollowExpr expr = (FollowExpr) parent.getValue().getExpression();
        LogicalObject alphaConjBeta = expr.getArg1();
        var child1ValueLvl2 = new VertexValue(new ArrayList<>(child1Lvl1.getValue().getHypothesis()),
                alphaConjBeta,
                child1Lvl1.getValue().getNum() + 1,
                "Ax");
        var child1Lvl2 = new Vertex(child1ValueLvl2);
        child1Lvl1.setChild1(child1Lvl2);
        return Collections.singletonList(child1Lvl2);
    }

    public List<Vertex> generateTreeAx6(Vertex parent) {
        var child1Lvl1 = turnstileRight(parent, "Il|");
        FollowExpr expr = (FollowExpr) parent.getValue().getExpression();
        LogicalObject alpha = expr.getArg1();

        var child1ValueLvl2 = new VertexValue(new ArrayList<>(child1Lvl1.getValue().getHypothesis()),
                alpha,
                child1Lvl1.getValue().getNum() + 1,
                "Ax");

        var child1Lvl2 = new Vertex(child1ValueLvl2);
        child1Lvl1.setChild1(child1Lvl2);

        return Collections.singletonList(child1Lvl2);
    }

    public List<Vertex> generateTreeAx7(Vertex parent) {
        var child1Lvl1 = turnstileRight(parent, "Ir|");
        FollowExpr expr = (FollowExpr) parent.getValue().getExpression();
        LogicalObject beta = expr.getArg1();
        var child1ValueLvl2 = new VertexValue(new ArrayList<>(child1Lvl1.getValue().getHypothesis()),
                beta,
                child1Lvl1.getValue().getNum() + 1,
                "Ax");
        var child1Lvl2 = new Vertex(child1ValueLvl2);
        child1Lvl1.setChild1(child1Lvl2);

        return Collections.singletonList(child1Lvl2);
    }

    public List<Vertex> generateTreeAx8(Vertex parent) {
        var child1Lvl1 = turnstileRight(parent, "I->");
        var child1Lvl2 = turnstileRight(child1Lvl1, "I->");
        var child1Lvl3 = turnstileRight(child1Lvl2, "E|");


        FollowExpr expr1 = (FollowExpr) parent.getValue().getExpression();
        LogicalObject alphaImplGamma = expr1.getArg1();
        FollowExpr expr2 = (FollowExpr) alphaImplGamma;
        LogicalObject alpha = expr2.getArg1();
        LogicalObject gamma = expr2.getArg2();
        var hyp1 = new ArrayList<>(child1Lvl3.getValue().getHypothesis());
        hyp1.add(alpha);

        var child1ValueLvl4 = new VertexValue(hyp1,
                gamma,
                child1Lvl3.getValue().getNum() + 1,
                "E->");
        var child1Lvl4 = new Vertex(child1ValueLvl4);
        child1Lvl3.setChild1(child1Lvl4);
        var listChild1 = generateTreeMP(child1Lvl4, alphaImplGamma, alpha, "Ax", "Ax");

        FollowExpr expr3 = (FollowExpr) expr1.getArg2();
        FollowExpr expr4 = (FollowExpr) expr3.getArg1();
        LogicalObject beta = expr4.getArg1();

        var hyp2 = new ArrayList<>(child1Lvl3.getValue().getHypothesis());
        hyp2.add(beta);

        var child2ValueLvl4 = new VertexValue(hyp2,
                gamma,
                child1Lvl3.getValue().getNum() + 1,
                "E->");

        var child2Lvl4 = new Vertex(child2ValueLvl4);
        child1Lvl3.setChild2(child2Lvl4);
        var listChild2 = generateTreeMP(child2Lvl4, expr4, beta, "Ax", "Ax");

        FollowExpr expr5 = (FollowExpr) child1Lvl2.getValue().getExpression();
        LogicalObject alphaDisBeta = expr5.getArg1();

        var child3ValueLvl4 = new VertexValue(new ArrayList<>(child1Lvl3.getValue().getHypothesis()),
                alphaDisBeta,
                child1Lvl3.getValue().getNum() + 1,
                "Ax");

        var child3Lvl4 = new Vertex(child3ValueLvl4);
        child1Lvl3.setChild3(child3Lvl4);

        listChild1.addAll(listChild2);
        listChild1.add(child3Lvl4);

        return listChild1;
    }

    public List<Vertex> generateTreeAx9(Vertex parent) {
        var child1Lvl1 = turnstileRight(parent, "I->");
        var child1Lvl2 = turnstileRight(child1Lvl1, "I->");
        Neg alphaNeg = (Neg) child1Lvl2.getValue().getExpression();
        LogicalObject alpha = alphaNeg.getValue();

        var hyp = new ArrayList<>(child1Lvl2.getValue().getHypothesis());
        hyp.add(alpha);

        var child1ValueLvl3 = new VertexValue(hyp,
                new Neg(),
                child1Lvl2.getValue().getNum() + 1,
                "E->");
        var child1Lvl3 = new Vertex(child1ValueLvl3);
        child1Lvl2.setChild1(child1Lvl3);

        FollowExpr expr = (FollowExpr) child1Lvl1.getValue().getExpression();
        FollowExpr alphaImplBetaNeg = (FollowExpr) expr.getArg1();
        LogicalObject betaNeg = alphaImplBetaNeg.getArg2();


        Neg neg = (Neg) betaNeg;
        LogicalObject beta = neg.getValue();

        var r = generateTreeMP(child1Lvl3, betaNeg, beta, "E->", "E->");

        var child1Lvl4 = r.get(0);
        var child2Lvl4 = r.get(1);

        var r1 = generateTreeMP(child1Lvl4, alphaImplBetaNeg, alpha, "Ax", "Ax");
        var child1Lvl5 = r1.get(0);
        var child2Lvl5 = r1.get(1);

        LogicalObject alphaImplBeta = ((FollowExpr) parent.getValue().getExpression()).getArg1();

        var list = generateTreeMP(child2Lvl4, alphaImplBeta, alpha, "Ax", "Ax");

        var res = new ArrayList<>(Arrays.asList(child1Lvl5, child2Lvl5));
        res.add(list.get(0));
        res.add(list.get(1));

        return res;
    }

    public List<Vertex> generateTreeAx10(Vertex parent) {
        var child1Lvl1 = turnstileRight(parent, "I->");
        var child1Lvl2 = turnstileRight(child1Lvl1, "E_|_");

        var child1ValueLvl3 = new VertexValue(new ArrayList<>(child1Lvl2.getValue().getHypothesis()),
                new Neg(),
                child1Lvl2.getValue().getNum() + 1,
                "E->");

        var child1Lvl3 = new Vertex(child1ValueLvl3);
        child1Lvl2.setChild1(child1Lvl3);

        FollowExpr expr = (FollowExpr) parent.getValue().getExpression();
        LogicalObject alpha = expr.getArg1();

        FollowExpr expr1 = (FollowExpr) expr.getArg2();
        LogicalObject alphaNeg = expr1.getArg1();

        var child1ValueLvl4 = new VertexValue(new ArrayList<>(child1Lvl3.getValue().getHypothesis()),
                alphaNeg,
                child1Lvl3.getValue().getNum() + 1,
                "Ax");

        var child1Lvl4 = new Vertex(child1ValueLvl4);
        child1Lvl3.setChild1(child1Lvl4);

        var child2ValueLvl4 = new VertexValue(new ArrayList<>(child1Lvl3.getValue().getHypothesis()),
                alpha,
                child1Lvl3.getValue().getNum() + 1,
                "Ax");

        var child2Lvl4 = new Vertex(child2ValueLvl4);
        child1Lvl3.setChild2(child2Lvl4);

        return Arrays.asList(child1Lvl4, child2Lvl4);
    }

}


