package com.lab.hw1.parser.expressionClasses

class Const(private val arg: Double) : ExpressionClass {

    override fun evaluate(): Double {
        return arg
    }

    @Override
    override fun toString(): String {
        return arg.toString()
    }
}