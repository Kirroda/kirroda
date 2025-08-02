package com.kirroda.krcalc

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    
    private lateinit var display: TextView
    private lateinit var calculatorEngine: CalculatorEngine
    private var currentExpression = ""
    private var isResultDisplayed = false
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        calculatorEngine = CalculatorEngine()
        initializeViews()
        setupClickListeners()
    }
    
    private fun initializeViews() {
        display = findViewById(R.id.tvDisplay)
        updateDisplay()
    }
    
    private fun setupClickListeners() {
        // Number buttons
        val numberButtons = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        )
        
        numberButtons.forEach { buttonId ->
            findViewById<Button>(buttonId).setOnClickListener { view ->
                val number = (view as Button).text.toString()
                onNumberClick(number)
            }
        }
        
        // Operator buttons
        findViewById<Button>(R.id.btnAdd).setOnClickListener { onOperatorClick("+") }
        findViewById<Button>(R.id.btnSubtract).setOnClickListener { onOperatorClick("−") }
        findViewById<Button>(R.id.btnMultiply).setOnClickListener { onOperatorClick("×") }
        findViewById<Button>(R.id.btnDivide).setOnClickListener { onOperatorClick("÷") }
        findViewById<Button>(R.id.btnPercentage).setOnClickListener { onOperatorClick("%") }
        
        // Bracket buttons
        findViewById<Button>(R.id.btnOpenBracket).setOnClickListener { onBracketClick("(") }
        findViewById<Button>(R.id.btnCloseBracket).setOnClickListener { onBracketClick(")") }
        
        // Special buttons
        findViewById<Button>(R.id.btnDecimal).setOnClickListener { onDecimalClick() }
        findViewById<Button>(R.id.btnEquals).setOnClickListener { onEqualsClick() }
        findViewById<Button>(R.id.btnClear).setOnClickListener { onClearClick() }
        findViewById<Button>(R.id.btnDelete).setOnClickListener { onDeleteClick() }
    }
    
    private fun onNumberClick(number: String) {
        if (isResultDisplayed) {
            currentExpression = ""
            isResultDisplayed = false
        }
        
        if (calculatorEngine.isValidInput(currentExpression, number)) {
            currentExpression += number
            updateDisplay()
        }
    }
    
    private fun onOperatorClick(operator: String) {
        if (isResultDisplayed) {
            isResultDisplayed = false
        }
        
        if (currentExpression.isEmpty() && operator == "−") {
            // Allow negative numbers at the start
            currentExpression += operator
            updateDisplay()
            return
        }
        
        if (currentExpression.isNotEmpty()) {
            // Replace last operator if the last character is an operator
            val lastChar = currentExpression.last().toString()
            val operators = listOf("+", "−", "×", "÷", "%")
            
            if (lastChar in operators) {
                currentExpression = currentExpression.dropLast(1) + operator
            } else {
                currentExpression += operator
            }
            updateDisplay()
        }
    }
    
    private fun onBracketClick(bracket: String) {
        if (isResultDisplayed) {
            currentExpression = ""
            isResultDisplayed = false
        }
        
        when (bracket) {
            "(" -> {
                // Allow opening bracket after operators or at start
                if (currentExpression.isEmpty() || 
                    currentExpression.last() in "+(−×÷") {
                    currentExpression += bracket
                    updateDisplay()
                }
            }
            ")" -> {
                // Allow closing bracket if there are unmatched opening brackets
                val openCount = currentExpression.count { it == '(' }
                val closeCount = currentExpression.count { it == ')' }
                if (openCount > closeCount && currentExpression.isNotEmpty() &&
                    currentExpression.last() !in "+(−×÷") {
                    currentExpression += bracket
                    updateDisplay()
                }
            }
        }
    }
    
    private fun onDecimalClick() {
        if (isResultDisplayed) {
            currentExpression = "0"
            isResultDisplayed = false
        }
        
        if (calculatorEngine.isValidInput(currentExpression, ".")) {
            if (currentExpression.isEmpty() || 
                currentExpression.last() in "+(−×÷") {
                currentExpression += "0."
            } else {
                currentExpression += "."
            }
            updateDisplay()
        }
    }
    
    private fun onEqualsClick() {
        if (currentExpression.isNotEmpty()) {
            val result = calculatorEngine.evaluateExpression(currentExpression)
            currentExpression = result
            isResultDisplayed = true
            updateDisplay()
        }
    }
    
    private fun onClearClick() {
        currentExpression = ""
        isResultDisplayed = false
        updateDisplay()
    }
    
    private fun onDeleteClick() {
        if (isResultDisplayed) {
            onClearClick()
            return
        }
        
        if (currentExpression.isNotEmpty()) {
            currentExpression = currentExpression.dropLast(1)
            updateDisplay()
        }
    }
    
    private fun updateDisplay() {
        val displayText = calculatorEngine.formatDisplayText(currentExpression)
        display.text = displayText
    }
    
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("current_expression", currentExpression)
        outState.putBoolean("is_result_displayed", isResultDisplayed)
    }
    
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        currentExpression = savedInstanceState.getString("current_expression", "")
        isResultDisplayed = savedInstanceState.getBoolean("is_result_displayed", false)
        updateDisplay()
    }
}