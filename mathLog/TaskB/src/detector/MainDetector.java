package detector;

import parser.expressionClasses.LogicalObject;

public class MainDetector {
    private final AxiomChecker axiomChecker = new AxiomChecker();
    private final ModusPonens mp = new ModusPonens();
    private final ZeroLine zeroLine;

    public MainDetector(ZeroLine zeroLine) {
        this.zeroLine = zeroLine;
    }

    public Sign detect(LogicalObject obj) {
        Sign res = axiomChecker.detectAxiom(obj);
        boolean isHyp = zeroLine.getHypothesis().contains(obj);
        if (res.getMode() != Mode.ERROR || isHyp) {
            mp.putInMap1(obj);
            return res.getMode() != Mode.ERROR? res : new Sign(isHyp);
        }
        return mp.checkMP(obj);
    }
}
