//package tests;
//
//import detector.Mode;
//import detector.Sign;
//import detector.SignAll;
//import org.junit.Assert;
//import org.junit.Test;
//import parser.expressionClasses.LogicalObject;
//
//import java.util.ArrayList;
//
//public class SignAllTest {
//
//
//    @Test(timeout = 200)
//    public void test1() {
//        var input = new ArrayList<String>();
//        input.add("A |- A -> A");
//        input.add("A -> A -> A");
//        input.add("A");
//        input.add("A -> A");
//        SignAll signAll = new SignAll(input);
//        Assert.assertEquals("[1, Hyp, MP (A -> (A -> A)) A]", signAll.getSigns().toString());
//    }
//
//    @Test(timeout = 200)
//    public void test2() {
//        var input = new ArrayList<String>();
//        input.add("A -> B |- A -> A -> T");
//        input.add("(A->B)->(A->B->C)->(A->C)");
//        input.add("A->B");
//        input.add("(A->B->C)->(A->C)");
//        SignAll signAll = new SignAll(input);
//        Assert.assertEquals("[2, Hyp, MP ((A -> B) -> ((A -> (B -> C)) -> (A -> C))) (A -> B)]",
//                signAll.getSigns().toString());
//    }
//
//    @Test(timeout = 200)
//    public void test3() {
//        var input = new ArrayList<String>();
//        input.add("(A->C) -> B, (A->C), C |- A -> A -> T");
//        input.add("(A->C) -> B -> (A->C)");
//        input.add("(A->C)->B");
//        input.add("(A->C)");
//        input.add("C");
//        input.add("B -> (A->C)");
//        SignAll signAll = new SignAll(input);
//        Assert.assertEquals("[1, Hyp, Hyp, Hyp, MP ((A -> C) -> (B -> (A -> C))) (A -> C)]",
//                signAll.getSigns().toString());
//    }
//}
