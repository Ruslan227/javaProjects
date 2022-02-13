package com.lab.hw1.parser.expressionClasses

open class Binary(
    private val arg1: ExpressionClass,
    private val arg2: ExpressionClass,
    private val operationStr: String,
    private val operation: (Double, Double) -> Double
) : ExpressionClass {


    override fun evaluate(): Double {
        return operation.invoke(arg1.evaluate(), arg2.evaluate())
    }

    override fun toString(): String {
        return "$arg1 $operationStr $arg2"
    }


}