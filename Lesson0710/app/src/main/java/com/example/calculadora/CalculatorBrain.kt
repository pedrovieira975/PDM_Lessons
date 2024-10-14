package com.example.calculadora

class CalculatorBrain {

    enum class Operation (op: String) {
        SUM("+"),
        SUB("-"),
        MULT("*"),
        DIV("/"),
//        SQRT("√"),
//        SIGNAL("±"),
        PERCENT("%"),
        EQUALS("=");


        companion object {
            fun getOp(value: String): Operation {
                return when (value) {
                    SUM.toString() -> SUM
                    SUB.toString() -> SUB
                    MULT.toString() -> MULT
                    DIV.toString() -> DIV
//                    SQRT.toString() -> SQRT
//                    SIGNAL.toString() -> SIGNAL
                    PERCENT.toString() -> PERCENT
                    EQUALS.toString() -> EQUALS
                    else -> {
                        EQUALS
                    }
                }
            }
        }
    }

    var operation : Operation? = null
    var operand : Double = 0.0

    fun doOperation(value: Double) : Double {
        val result = when (operation) {
            Operation.SUM -> operand + value
            Operation.SUB -> operand - value
            Operation.MULT -> operand * value
            Operation.DIV -> operand / value
//            Operation.SQRT -> Math.sqrt(operand)
//            Operation.SIGNAL -> -operand
            Operation.PERCENT -> operand / 100
            Operation.EQUALS -> operand
            null -> value
        }
        return result
    }
}