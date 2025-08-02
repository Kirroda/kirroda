package com.kirroda.krcalc

import net.objecthunter.exp4j.ExpressionBuilder
import java.text.DecimalFormat

class CalculatorEngine {
    
    private val decimalFormat = DecimalFormat("0.##########")
    
    fun evaluateExpression(expression: String): String {
        try {
            if (expression.isEmpty() || expression == "0") {
                return "0"
            }
            
            // Handle percentage conversion
            val processedExpression = processPercentages(expression)
            
            // Replace display symbols with actual operators
            val normalizedExpression = processedExpression
                .replace("×", "*")
                .replace("÷", "/")
                .replace("−", "-")
            
            // Validate brackets
            if (!areBracketsBalanced(normalizedExpression)) {
                return "Error"
            }
            
            // Build and evaluate expression
            val expr = ExpressionBuilder(normalizedExpression).build()
            val result = expr.evaluate()
            
            // Check for invalid results
            if (result.isNaN() || result.isInfinite()) {
                return "Error"
            }
            
            // Format result
            return if (result == result.toLong().toDouble()) {
                result.toLong().toString()
            } else {
                decimalFormat.format(result)
            }
            
        } catch (e: Exception) {
            return "Error"
        }
    }
    
    private fun processPercentages(expression: String): String {
        var processed = expression
        val percentageRegex = """(\d+(?:\.\d+)?)%""".toRegex()
        
        processed = percentageRegex.replace(processed) { matchResult ->
            val number = matchResult.groupValues[1].toDouble()
            val percentage = number / 100.0
            percentage.toString()
        }
        
        return processed
    }
    
    private fun areBracketsBalanced(expression: String): Boolean {
        var count = 0
        for (char in expression) {
            when (char) {
                '(' -> count++
                ')' -> {
                    count--
                    if (count < 0) return false
                }
            }
        }
        return count == 0
    }
    
    fun isValidInput(current: String, newInput: String): Boolean {
        val combined = current + newInput
        
        // Check for consecutive operators
        val operators = listOf("+", "−", "×", "÷")
        for (op in operators) {
            if (combined.contains("$op$op") || 
                combined.contains("$op.") && newInput == "." ||
                combined.endsWith("$op.")) {
                return false
            }
        }
        
        // Check for multiple decimals in same number
        val lastNumberStart = maxOf(
            combined.lastIndexOf("+"),
            combined.lastIndexOf("−"),
            combined.lastIndexOf("×"),
            combined.lastIndexOf("÷"),
            combined.lastIndexOf("(")
        ) + 1
        
        val currentNumber = combined.substring(lastNumberStart)
        if (newInput == "." && currentNumber.contains(".")) {
            return false
        }
        
        return true
    }
    
    fun formatDisplayText(input: String): String {
        if (input.isEmpty()) return "0"
        return input
    }
}