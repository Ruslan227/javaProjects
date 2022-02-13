package tests;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import parser.Parser;

public class ParserTest {
    private static Parser p = new Parser();

    @Test
    public void test1() {
        Assertions.assertEquals("(A->((Z|(D&E))->(@p.A)))",
                p.parse("A -> (Z | (D & E)) -> (@p.(A))").toString());
    }

    @Test
    public void test2() {
        Assertions.assertEquals("((@y.((y+(0*0’))=y))->(?x.(@y.(x=y))))",
                p.parse("(@y.y+0*0’=y)->(?x.@y.x=y)").toString());
    }

    @Test
    public void test3() {
        Assertions.assertEquals("(@y.((x+(0’*y’)’’’)=a))",
                p.parse("(@y.((x+(0’*y’)’’’)=a))").toString());
    }

    @Test
    public void test4() {
        Assertions.assertEquals("(?x.(@y.((x+(0’’*y’’))=z’)))",
                p.parse("?x.@y.(x+(0’’*y’’) = z’)").toString());
    }

    @Test
    public void test5() {
        Assertions.assertEquals("(@y.((x+(0’*y’)’’’)=a))",
                p.parse("((((@y.((((x))+((((0’)*(y’))))’’’)=(a))))))").toString());
    }

    @Test
    public void test6() {
        Assertions.assertEquals("((@y.(((0+f)+(0’*y’)’’’)=a))->(?x.(x=x)))",
                p.parse("(@y.(((0+f)+(0’*y’)’’’)=a))->(?x.(((x)=(x))))").toString());
    }

    @Test
    public void test7() {
        Assertions.assertEquals("(@a.(@b.(@c.((a=b)->((b=c)->(a=c))))))",
                p.parse("@a.@b.@c.a=b->b=c->a=c").toString());
    }

    @Test
    public void test8() {
        Assertions.assertEquals("(((0=0)->((0=0)->(0=0)))->((a=b)->(a’=b’)))",
                p.parse("(((0=0)->((0=0)->(0=0)))->((a=b)->(a’=b’)))").toString());
    }

    @Test
    public void test9() {
        Assertions.assertEquals("((@a.(@b.(@c.((a=b)->((a=c)->(b=c))))))->(@b.(@c.(((a+0)=b)->(((a+0)=c)->(b=c))))))",
                p.parse("((@a.(@b.(@c.((a=b)->((a=c)->(b=c))))))->(@b.(@c.(((a+0)=b)->(((a+0)=c)->(b=c))))))").toString());
    }

    @Test
    public void test10() {
        Assertions.assertEquals("(((0=0)->((0=0)->(0=0)))->(@a.(@b.((a=b)->((a=a)->(b=a))))))",
                p.parse("(((0=0)->((0=0)->(0=0)))->(@a.(@b.((a=b)->((a=a)->(b=a))))))").toString());
    }

    @Test
    public void test11() {
        Assertions.assertEquals("(((a+b’)=(a+b)’)->(((0=0)->((0=0)->(0=0)))->((a+b’)=(a+b)’)))",
                p.parse("(((a+b’)=(a+b)’)->(((0=0)->((0=0)->(0=0)))->((a+b’)=(a+b)’)))").toString());
    }

    @Test
    public void test12() {
        Assertions.assertEquals("((a*b’)=((a*b)+a))",
                p.parse("((a*b’)=((a*b)+a))").toString());
    }

    @Test
    public void test13() {
        Assertions.assertEquals("(((a*b’)=((a*b)+a))->(((0=0)->((0=0)->(0=0)))->((a*b’)=((a*b)+a))))",
                p.parse("(((a*b’)=((a*b)+a))->(((0=0)->((0=0)->(0=0)))->((a*b’)=((a*b)+a))))").toString());
    }

    @Test
    public void test14() {
        Assertions.assertEquals("(((@a.(@b.(@c.((a=b)->((a=c)->(b=c))))))->(@b.(@c.(((0’’*0)=b)->(((0’’*0)=c)->(b=c))))))->(((0’’*0)=0’)->((@a.(@b.(@c.((a=b)->((a=c)->(b=c))))))->(@b.(@c.(((0’’*0)=b)->(((0’’*0)=c)->(b=c))))))))",
                p.parse("(((@a.(@b.(@c.((a=b)->((a=c)->(b=c))))))->(@b.(@c.(((0’’*0)=b)->(((0’’*0)=c)->(b=c))))))->(((0’’*0)=0’)->((@a.(@b.(@c.((a=b)->((a=c)->(b=c))))))->(@b.(@c.(((0’’*0)=b)->(((0’’*0)=c)->(b=c))))))))").toString());
    }

    @Test
    public void test15() {
        Assertions.assertEquals("((((0’’*0)=0’)->(0’=0))->((((0’’*0)=0’)->(!(0’=0)))->(!((0’’*0)=0’))))",
                p.parse("((((0’’*0)=0’)->(0’=0))->((((0’’*0)=0’)->(!(0’=0)))->(!((0’’*0)=0’))))").toString());
    }

    @Test
    public void test16() {
        Assertions.assertEquals("((((A&B)->A)->((((A&B)->A)->(((A&B)->(!A))->(!(A&B))))->((!A)->(((A&B)->A)->(((A&B)->(!A))->(!(A&B)))))))->(((A&B)->A)->((!A)->(((A&B)->A)->(((A&B)->(!A))->(!(A&B)))))))",
                p.parse("((((A&B)->A)->((((A&B)->A)->(((A&B)->(!A))->(!(A&B))))->((!A)->(((A&B)->A)->(((A&B)->(!A))->(!(A&B)))))))->(((A&B)->A)->((!A)->(((A&B)->A)->(((A&B)->(!A))->(!(A&B)))))))").toString());
    }

    @Test
    public void test17() {
        Assertions.assertEquals("((((!B)->((A&B)->B))->(((!B)->(((A&B)->B)->(((A&B)->(!B))->(!(A&B)))))->((!B)->(((A&B)->(!B))->(!(A&B))))))->(((A&B)->B)->(((!B)->((A&B)->B))->(((!B)->(((A&B)->B)->(((A&B)->(!B))->(!(A&B)))))->((!B)->(((A&B)->(!B))->(!(A&B))))))))",
                p.parse("((((!B)->((A&B)->B))->(((!B)->(((A&B)->B)->(((A&B)->(!B))->(!(A&B)))))->((!B)->(((A&B)->(!B))->(!(A&B))))))->(((A&B)->B)->(((!B)->((A&B)->B))->(((!B)->(((A&B)->B)->(((A&B)->(!B))->(!(A&B)))))->((!B)->(((A&B)->(!B))->(!(A&B))))))))").toString());
    }

    @Test
    public void test18() {
        Assertions.assertEquals("((((0’’*0)=0’)->((@b.(@c.(((0’’*0)=b)->(((0’’*0)=c)->(b=c)))))->(@c.(((0’’*0)=0’)->(((0’’*0)=c)->(0’=c))))))->(((0’’*0)=0’)->(@c.(((0’’*0)=0’)->(((0’’*0)=c)->(0’=c))))))",
                p.parse("((((0’’*0)=0’)->((@b.(@c.(((0’’*0)=b)->(((0’’*0)=c)->(b=c)))))->(@c.(((0’’*0)=0’)->(((0’’*0)=c)->(0’=c))))))->(((0’’*0)=0’)->(@c.(((0’’*0)=0’)->(((0’’*0)=c)->(0’=c))))))").toString());
    }

}
