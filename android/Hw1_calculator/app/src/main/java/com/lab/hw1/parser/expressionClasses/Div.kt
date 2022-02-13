package com.lab.hw1.parser.expressionClasses

class Div(
    private val arg1: ExpressionClass,
    private val arg2: ExpressionClass
) : Binary(arg1, arg2, "/", { a, b -> a / b })