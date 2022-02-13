package detector;

import parser.expressionClasses.*;

public class AxiomChecker {

    public static boolean isFollow(LogicalObject obj) {
        return obj.getOperationSymbol().equals("->");
    }

    public static boolean isDis(LogicalObject obj) {
        return obj.getOperationSymbol().equals("|");
    }

    public static boolean isConj(LogicalObject obj) {
        return obj.getOperationSymbol().equals("&");
    }

    public Sign detectAxiom(LogicalObject expr) {
        if (check2Ax(expr))
            return new Sign(2);
        if (check8Ax(expr))
            return new Sign(8);
        if (check9Ax(expr))
            return new Sign(9);
        if (check3Ax(expr))
            return new Sign(3);
        if (check10Ax(expr))
            return new Sign(10);
        if (check1Ax(expr))
            return new Sign(1);

        if (check4Ax(expr))
            return new Sign(4);
        if (check5Ax(expr))
            return new Sign(5);
        if (check6Ax(expr))
            return new Sign(6);
        if (check7Ax(expr))
            return new Sign(7);
        return new Sign();
    }

    private boolean check2Ax(LogicalObject expr) {     // (A1 -> B1) -> (A2 -> B2 -> C1) -> (A3 -> C2)
        if (isFollow(expr)) {
            FollowExpr expr1 = (FollowExpr) expr;
            LogicalObject br1 = expr1.getArg1();
            if (isFollow(br1)) {
                FollowExpr br1Foll = (FollowExpr) br1;
                LogicalObject alpha1 = br1Foll.getArg1();
                LogicalObject beta1 = br1Foll.getArg2();
                if (isFollow(expr1.getArg2())) {
                    FollowExpr rightSide = (FollowExpr) expr1.getArg2();
                    LogicalObject br2 = rightSide.getArg1();
                    if (isFollow(br2)) {
                        FollowExpr br2Foll = (FollowExpr) br2;
                        LogicalObject alpha2 = br2Foll.getArg1();
                        if (isFollow(br2Foll.getArg2())) {
                            FollowExpr br2F = (FollowExpr) br2Foll.getArg2();
                            LogicalObject beta2 = br2F.getArg1();
                            LogicalObject gamma1 = br2F.getArg2();
                            if (isFollow(rightSide.getArg2())) {
                                FollowExpr br3Foll = (FollowExpr) rightSide.getArg2();
                                LogicalObject alpha3 = br3Foll.getArg1();
                                LogicalObject gamma2 = br3Foll.getArg2();
                                return alpha1.equals(alpha2) &&
                                        alpha1.equals(alpha3) &&
                                        beta1.equals(beta2) &&
                                        gamma1.equals(gamma2);
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean check8Ax(LogicalObject expr) {     // (A1 -> C1) -> (B1 -> C2) -> ((A2 | B2) -> C3)
        if (isFollow(expr)) {
            FollowExpr expr1 = (FollowExpr) expr;
            LogicalObject br1 = expr1.getArg1();
            if (isFollow(br1)) {
                FollowExpr br1Foll = (FollowExpr) br1;
                LogicalObject alpha1 = br1Foll.getArg1();
                LogicalObject gamma1 = br1Foll.getArg2();
                if (isFollow(expr1.getArg2())) {
                    FollowExpr rightSide = (FollowExpr) expr1.getArg2();
                    LogicalObject br2 = rightSide.getArg1();
                    if (isFollow(br2)) {
                        FollowExpr br2Foll = (FollowExpr) br2;
                        LogicalObject beta1 = br2Foll.getArg1();
                        LogicalObject gamma2 = br2Foll.getArg2();
                        if (isFollow(rightSide.getArg2())) {
                            FollowExpr br3Foll = (FollowExpr) rightSide.getArg2();
                            if (isDis(br3Foll.getArg1())) {
                                Dis d = (Dis) br3Foll.getArg1();
                                LogicalObject alpha2 = d.getArg1();
                                LogicalObject beta2 = d.getArg2();
                                LogicalObject gamma3 = br3Foll.getArg2();
                                return alpha1.equals(alpha2) &&
                                        beta1.equals(beta2) &&
                                        gamma1.equals(gamma2) &&
                                        gamma1.equals(gamma3);
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean check9Ax(LogicalObject expr) {       // (A1 -> B1) -> (A2 -> !B2) -> !A3
        if (isFollow(expr)) {
            FollowExpr expr1 = (FollowExpr) expr;
            LogicalObject br1 = expr1.getArg1();
            if (isFollow(br1)) {
                FollowExpr br1Foll = (FollowExpr) br1;
                LogicalObject alpha1 = br1Foll.getArg1();
                LogicalObject beta1 = br1Foll.getArg2();
                if (isFollow(expr1.getArg2())) {
                    FollowExpr rightSide = (FollowExpr) expr1.getArg2();
                    LogicalObject br2 = rightSide.getArg1();
                    if (isFollow(br2)) {
                        FollowExpr br2Foll = (FollowExpr) br2;
                        LogicalObject alpha2 = br2Foll.getArg1();
                        LogicalObject notBeta2 = br2Foll.getArg2();
                        LogicalObject notAlpha3 = rightSide.getArg2();
                        return alpha1.equals(alpha2) &&
                                (new Neg(alpha1)).equals(notAlpha3) &&
                                (new Neg(beta1)).equals(notBeta2);
                    }
                }
            }
        }
        return false;
    }

    private boolean check3Ax(LogicalObject expr) {      // A1 -> B1 -> (A2 & B2)
        if (isFollow(expr)) {
            FollowExpr expr1 = (FollowExpr) expr;
            LogicalObject alpha1 = expr1.getArg1();
            if (isFollow(expr1.getArg2())) {
                FollowExpr rightSide = (FollowExpr) expr1.getArg2();
                LogicalObject beta1 = rightSide.getArg1();
                LogicalObject br3 = rightSide.getArg2();
                if (isConj(br3)) {
                    Conj br3F = (Conj) br3;
                    LogicalObject alpha2 = br3F.getArg1();
                    LogicalObject beta2 = br3F.getArg2();
                    return alpha1.equals(alpha2) &&
                            beta1.equals(beta2);
                }
            }
        }
        return false;
    }

    private boolean check10Ax(LogicalObject expr) {     // A1 -> !A2 -> B
        if (isFollow(expr)) {
            FollowExpr expr1 = (FollowExpr) expr;
            LogicalObject alpha = expr1.getArg1();
            if (isFollow(expr1.getArg2())) {
                LogicalObject alphaNeg = ((FollowExpr) expr1.getArg2()).getArg1();
                return (new Neg(alpha)).equals(alphaNeg);
            }
        }
        return false;
    }

    private boolean check1Ax(LogicalObject expr) {     // A -> B -> A1
        if (isFollow(expr)) {
            FollowExpr expr1 = (FollowExpr) expr;
            LogicalObject alpha1 = expr1.getArg1();
            if (isFollow(expr1.getArg2())) {
                FollowExpr f = (FollowExpr) expr1.getArg2();
                LogicalObject alpha2 = f.getArg2();
                return alpha1.equals(alpha2);
            }
        }
        return false;
    }


    private boolean check4Ax(LogicalObject expr) {      // (A1 & B) -> A2
        if (isFollow(expr)) {
            FollowExpr expr1 = (FollowExpr) expr;
            LogicalObject alpha2 = expr1.getArg2();
            if (isConj(expr1.getArg1())) {
                Conj c = (Conj) expr1.getArg1();
                LogicalObject alpha1 = c.getArg1();
                return alpha1.equals(alpha2);
            }
        }
        return false;
    }

    private boolean check5Ax(LogicalObject expr) {      // (A & B1) -> B2
        if (isFollow(expr)) {
            FollowExpr expr1 = (FollowExpr) expr;
            LogicalObject beta2 = expr1.getArg2();
            if (isConj(expr1.getArg1())) {
                Conj c = (Conj) expr1.getArg1();
                LogicalObject beta1 = c.getArg2();
                return beta1.equals(beta2);
            }
        }
        return false;
    }

    private boolean check6Ax(LogicalObject expr) {       // A1 -> (A2 | B)
        if (isFollow(expr)) {
            FollowExpr expr1 = (FollowExpr) expr;
            LogicalObject alpha1 = expr1.getArg1();
            if (isDis(expr1.getArg2())) {
                Dis d = (Dis) expr1.getArg2();
                LogicalObject alpha2 = d.getArg1();
                return alpha1.equals(alpha2);
            }
        }
        return false;
    }

    private boolean check7Ax(LogicalObject expr) {      // B1 -> (A | B2)
        if (isFollow(expr)) {
            FollowExpr expr1 = (FollowExpr) expr;
            LogicalObject beta1 = expr1.getArg1();
            if (isDis(expr1.getArg2())) {
                Dis d = (Dis) expr1.getArg2();
                LogicalObject beta2 = d.getArg2();
                return beta1.equals(beta2);
            }
        }
        return false;
    }
}
