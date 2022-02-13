package detector;

import parser.Parser;
import parser.expressionClasses.LogicalObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SignAll {
    private final List<String> input;
    private final Parser p;
    private final Map<LogicalObject, Sign> signs = new HashMap<>();
    private List<LogicalObject> objects;
    private int mistake = -1;
    private boolean proofMistake = false;
    private ZeroLine zeroLine;

    public SignAll(List<String> input, Parser p) {
        this.input = input;
        this.p = p;
        parse();
    }

    public int getMistake() {
        return mistake;
    }

    public ZeroLine getZeroLine() {
        return zeroLine;
    }

    public boolean isProofMistake() {
        return proofMistake;
    }

    private void parse() {
        if (input.size() == 0)
            mistake = 1;
        else {
            objects = input.subList(1, input.size()).stream().map(p::parse).collect(Collectors.toList());
            zeroLine = new ZeroLine(input.get(0), p);

            if (!objects.get(objects.size() - 1).equals(zeroLine.getProofWantedConclusion())) {
                proofMistake = true;
            } else {
                MainDetector detector = new MainDetector(zeroLine);

                for (int i = 0; i < objects.size(); i++) {
                    signs.computeIfAbsent(objects.get(i), detector::detect);
                    if (signs.get(objects.get(i)).getMode() == Mode.ERROR) {
                        mistake = i + 2;
                        break;
                    }
                }
            }
        }
    }

    public List<LogicalObject> getObjects() {
        return objects;
    }

    public Map<LogicalObject, Sign> getSigns() {
        return signs;
    }
}

