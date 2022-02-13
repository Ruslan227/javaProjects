package solver;

import detector.MainDetector;
import parser.expressionClasses.LogicalExpression;
import parser.expressionClasses.Zero;
import parser.expressionClasses.binary.Mul;
import parser.expressionClasses.unary.Apostrophe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static solver.Prover.*;
import static solver.Utils.mergeStreams;

// proves ?x.n=2*x for given n

public class Main {
    public static List<String> getProof(int n) {
        Stream<String> toProve = Stream.of(String.format("?x.%s=0''*x", Utils.getNumber(n)));
        // @a.@b.(a=b->b=a)
        var universalAEqBFollowBEqA = universalAEqBFollowBEqA();
        // 2*0=0
        var base = mergeStreams(
                addUniversalQuantifiers("a*0=0", "a"),
                sub("@a.a*0=0", TWO.toString())
        );
        // 2*n/2=n
        Stream<String> mainProof = Stream.empty();
        LogicalExpression x = new Zero();
        LogicalExpression y = x;
        for (int i = 0; i < n / 2; i++) {
            mainProof = Stream.concat(mainProof, cycleOut(x, y));
            x = new Apostrophe(x);
            y = Utils.addApostrophes(y, 2);
        }
        var e_2x = new Mul(TWO, x);
        var penultimateProof = proofBEqAFromUniversalAEqBFollowBEqA(e_2x, y);
        var lastProof = Stream.of(
                String.format("%s = %s -> ?x.%s = 0''*x", y, e_2x, y),
                String.format("?x.%s = 0''*x", y)
        );
        return mergeStreams(
                toProve,
                universalAEqBFollowBEqA,
                base,
                mainProof,
                penultimateProof,
                lastProof
        ).map(line -> parser.parse(line).toString()).collect(Collectors.toList());
    }

    public static List<String> getAnnotatedProof(int n) {
        return MainDetector.annotateProof(getProof(n));
    }

//    private static String convertApostrophes(String expression) {
//        var sb = new StringBuilder();
//        for (int i = 0; i < expression.length(); i++) {
//            char cur = expression.charAt(i);
//            if (cur != '0' ) {
//                sb.append(cur);
//                continue;
//            }
//            int acc = 0;
//            while (++i < expression.length() && expression.charAt(i) == '\'' ) {
//                ++acc;
//            }
//            --i;
//            sb.append(acc);
//        }
//        return sb.toString();
//    }

    public static void main(String[] args) {
        try (var reader = new BufferedReader(new InputStreamReader(System.in))) {
            String n = reader.readLine();
            if (!Utils.isEvenNatural(n)) {
                System.out.println("Expected 1 argument: even natural number");
                return;
            }
            List<String> proof = getProof(Integer.parseInt(n));
            proof.set(0, "|-" + proof.get(0));
            proof.forEach(System.out::println);
        } catch (IOException e) {
            System.err.println("Problem occurred during reading");
        }
    }
}
