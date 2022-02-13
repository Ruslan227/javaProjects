package com.lab.hw1.parser.expressionClasses

import com.lab.hw1.parser.expressionClasses.Binary
import com.lab.hw1.parser.expressionClasses.ExpressionClass

class Sub(
    private val arg1: ExpressionClass,
    private val arg2: ExpressionClass
) : Binary(arg1, arg2, "-", { a, b -> a - b })