package com.kirroda.krcalc

import org.junit.Test
import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    
    private val calculatorEngine = CalculatorEngine()
    
    @Test
    fun addition_isCorrect() {
        assertEquals("4", calculatorEngine.evaluateExpression("2+2"))
    }
    
    @Test
    fun subtraction_isCorrect() {
        assertEquals("5", calculatorEngine.evaluateExpression("10−5"))
    }
    
    @Test
    fun multiplication_isCorrect() {
        assertEquals("15", calculatorEngine.evaluateExpression("3×5"))
    }
    
    @Test
    fun division_isCorrect() {
        assertEquals("3", calculatorEngine.evaluateExpression("15÷5"))
    }
    
    @Test
    fun bodmas_isCorrect() {
        assertEquals("14", calculatorEngine.evaluateExpression("2+3×4"))
    }
    
    @Test
    fun brackets_isCorrect() {
        assertEquals("20", calculatorEngine.evaluateExpression("(2+3)×4"))
    }
    
    @Test
    fun percentage_isCorrect() {
        assertEquals("0.5", calculatorEngine.evaluateExpression("50%"))
    }
    
    @Test
    fun complex_expression_isCorrect() {
        assertEquals("10", calculatorEngine.evaluateExpression("(10+5)×2÷3"))
    }
    
    @Test
    fun division_by_zero_returns_error() {
        assertEquals("Error", calculatorEngine.evaluateExpression("5÷0"))
    }
    
    @Test
    fun invalid_expression_returns_error() {
        assertEquals("Error", calculatorEngine.evaluateExpression("2++3"))
    }
}