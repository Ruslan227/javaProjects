package output;

import detector.Sign;
import detector.SignAll;
import output.axiomsTreeGenerators.TreeGeneratorAxiom;
import output.vertex.Vertex;
import output.vertex.VertexValue;
import parser.Parser;
import parser.expressionClasses.LogicalObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static Parser p = new Parser();
    public SignAll signAll;
    private TreeGeneratorAxiom generator = new TreeGeneratorAxiom();

    public Main(List<String> input) {
        signAll = new SignAll(input, p);
    }

    public static void main(String[] args) throws IOException {
        final String sInp = "tests/pack1/test0_in.txt";
        final String sOut = "tests/myRes.txt";
        try (var reader = new BufferedReader(new InputStreamReader(System.in));
             var writer = new BufferedWriter(new OutputStreamWriter(System.out))) {
            var input = new ArrayList<String>();
            String s = reader.readLine();
            while (s != null) {
                if (s.isEmpty())
                    break;
                input.add(s);
                s = reader.readLine();
            }

            var tree = new Main(input);

            if (tree.signAll.isProofMistake()) {
                writer.write("The proof does not prove the required expression");
            } else if (tree.signAll.getMistake() != -1) {
                writer.write("Proof is incorrect at line " + tree.signAll.getMistake());
            } else {
                LogicalObject expr = p.parse(input.get(input.size() - 1));
                tree.run(expr, writer);
            }
        }
    }

    private String getRuleName(LogicalObject expr) {
        Sign sign = signAll.getSigns().get(expr);
        String ruleName = "";
        switch (sign.getMode()) {
            case MP:
                ruleName = "E->";
                break;
            case HYP:
                ruleName = "Ax";
                break;
            case AX:
                ruleName = "I->";
                break;
        }
        return ruleName;
    }

    private Vertex createRoot(LogicalObject expr) {
        var val = new VertexValue(new ArrayList<>(signAll.getZeroLine().getHypothesis()),
                expr,
                0,
                getRuleName(expr));

        return new Vertex(val);
    }

    public void run(LogicalObject rootExpr, BufferedWriter writer) throws IOException {
        Vertex root = createRoot(rootExpr);
        createTree(root);
        createHypothesis(root);
        print(root, writer);
    }

    private void createTree(Vertex root) {
        Sign sign = signAll.getSigns().get(root.getValue().getExpression());
        List<Vertex> list = new ArrayList<>();
        if (sign == null)
            return;

        switch (sign.getMode()) {
            case MP:
                list = generator.generateTreeMP(root,
                        sign.getExpr1(),
                        sign.getExpr2(),
                        getRuleName(sign.getExpr1()),
                        getRuleName(sign.getExpr2()));
                break;
            case AX:
                switch (sign.getNum()) {
                    case 1:
                        list = generator.generateTreeAx1(root);
                        break;
                    case 2:
                        list = generator.generateTreeAx2(root);
                        break;
                    case 3:
                        list = generator.generateTreeAx3(root);
                        break;
                    case 4:
                        list = generator.generateTreeAx4(root);
                        break;
                    case 5:
                        list = generator.generateTreeAx5(root);
                        break;
                    case 6:
                        list = generator.generateTreeAx6(root);
                        break;
                    case 7:
                        list = generator.generateTreeAx7(root);
                        break;
                    case 8:
                        list = generator.generateTreeAx8(root);
                        break;
                    case 9:
                        list = generator.generateTreeAx9(root);
                        break;
                    case 10:
                        list = generator.generateTreeAx10(root);
                        break;
                }
                break;
            case HYP:
                return;
        }
        for (Vertex v : list) {
            if (!v.getValue().getRuleName().equals("Ax"))
                createTree(v);
        }
    }

    private ArrayList<LogicalObject> findHypToPush(List<LogicalObject> hyp1, List<LogicalObject> hyp2, boolean isFirstChild) {
        var h1 = new ArrayList<>(hyp1);
        var h2 = new ArrayList<>(hyp2);
        if (isFirstChild) {
            h1.retainAll(h2);
            var h3 = new ArrayList<>(hyp2);
            h3.removeAll(h1);
            return h3;
        }
        h2.retainAll(h1);
        var h3 = new ArrayList<>(hyp1);
        h3.removeAll(h2);
        return h3;
    }

    private Vertex createHypothesis(Vertex root) {
        Sign sign = signAll.getSigns().get(root.getValue().getExpression());

        switch (sign.getMode()) {
            case MP:
                var child1 = createHypothesis(root.getChild1());
                var child2 = createHypothesis(root.getChild2());
                var hyp1 = child1.getValue().getHypothesis();
                var hyp2 = child2.getValue().getHypothesis();
                if (!hyp1.equals(hyp2)) {
                    child1.propogateHyp(findHypToPush(hyp1, hyp2, true));
                    child2.propogateHyp(findHypToPush(hyp1, hyp2, false));
                }
                root.getValue().setHypothesis(hyp1);
                break;
            case AX:
                break;
            case HYP:
                if (!root.getValue().getHypothesis().contains(root.getValue().getExpression())) {
                    var hyp = new ArrayList<LogicalObject>();
                    hyp.add(root.getValue().getExpression());
                    root.getValue().addHypothesis(hyp);
                }
                break;
        }

        return root;
    }

    private void print(Vertex root, BufferedWriter writer) throws IOException {
        if (root.getChild1() != null)
            print(root.getChild1(), writer);
        if (root.getChild2() != null)
            print(root.getChild2(), writer);
        if (root.getChild3() != null)
            print(root.getChild3(), writer);
        writer.write(root.getValue().toString());
        writer.newLine();
    }
}

//A->B, !B |- !A
//A->B
//!B
//!B -> A -> !B
//A -> !B
//(A -> B) -> (A -> !B) -> !A
//(A -> !B) -> !A
//!A

//A |- A -> A
//        A -> A -> A
//        A
//        A -> A

//C->A, B|C|- A
//        C->A
//        B|C
//        A