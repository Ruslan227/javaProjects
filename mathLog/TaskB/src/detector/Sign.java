package detector;


import parser.expressionClasses.LogicalObject;

public class Sign {
    private int num = 0;     // 0 - not Axiom;  if > 0 then Ax number
    private LogicalObject expr1 = null, expr2 = null;   // not Mp
    private boolean isHyp = false;
    private Mode mode = Mode.ERROR;

    public Sign() {     // not Ax, not Mp
    }

    public Sign(int num) {
        this.num = num;
        mode = Mode.AX;       // Ax state
    }

    public Sign(LogicalObject expr1, LogicalObject expr2) {
        this.expr1 = expr1;
        this.expr2 = expr2;
        mode = Mode.MP;       // Mp state
    }

    public Sign(boolean isHyp) {
        this.isHyp = isHyp;
        mode = Mode.HYP;
    }

    public Mode getMode() {
        return mode;
    }

    public int getNum() {
        return num;
    }

    public LogicalObject getExpr1() {
        return expr1;
    }

    public LogicalObject getExpr2() {
        return expr2;
    }

    public boolean isHyp() {
        return isHyp;
    }

    @Override
    public String toString() {
        switch (mode) {
            case MP:
                return "MP " + expr1.toString() + " " + expr2.toString();
            case AX:
                return Integer.toString(num);
            case HYP:
                return "Hyp";
            default:
                return "error";
        }
    }
}
