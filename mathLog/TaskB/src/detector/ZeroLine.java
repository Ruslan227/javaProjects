package detector;

import parser.Parser;
import parser.expressionClasses.LogicalObject;
import parser.expressionClasses.Var;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class ZeroLine {
    private Parser p;
    private HashSet<LogicalObject> hypothesis;
    private LogicalObject proofWantedConclusion;

    public ZeroLine(String line0, Parser p) {
        this.p = p;
        parseZeroLine(line0);
    }

    private void parseZeroLine(String line0) {
        String[] arr = line0
                .replace("|-", "⊢")
                .replace("->", "→")
                .split("[,⊢]");
        List<LogicalObject> parsedArr = Arrays.stream(arr).map(p::parse).collect(Collectors.toList());
        proofWantedConclusion = parsedArr.get(parsedArr.size() - 1);
        if (parsedArr.get(0).equals(new Var("")))
            hypothesis = new HashSet<>();
        else
            hypothesis = new HashSet<>(parsedArr.subList(0, arr.length - 1));
    }

    public HashSet<LogicalObject> getHypothesis() {
        return hypothesis;
    }

    public LogicalObject getProofWantedConclusion() {
        return proofWantedConclusion;
    }
}
