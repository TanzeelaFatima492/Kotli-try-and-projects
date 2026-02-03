package com.example.advancedcalculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var tvResult: TextView
    private lateinit var tvInput: TextView

    private var input = ""
    private var operator = ""
    private var oldNumber = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvResult = findViewById(R.id.tvresult)
        tvInput = findViewById(R.id.tvInput)

        val buttons = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3,
            R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7,
            R.id.btn8, R.id.btn9
        )

        for (id in buttons) {
            findViewById<Button>(id).setOnClickListener {
                val value = (it as Button).text.toString()
                input += value
                tvInput.text = input
            }
        }

        val operatorButtons = mapOf(
            R.id.btnAdd to "+",
            R.id.btnSubtract to "-",
            R.id.btnMultiply to "×",
            R.id.btnDivide to "÷"
        )

        for ((id, op) in operatorButtons) {
            findViewById<Button>(id).setOnClickListener {
                if (input.isEmpty()) return@setOnClickListener
                oldNumber = input
                operator = op
                input = ""
                tvInput.text = "$oldNumber $operator "
            }
        }

        findViewById<Button>(R.id.btnEqual).setOnClickListener {
            if (oldNumber.isNotEmpty() && input.isNotEmpty()) {
                val num1 = oldNumber.toDouble()
                val num2 = input.toDouble()
                val result = when (operator) {
                    "+" -> num1 + num2
                    "-" -> num1 - num2
                    "×" -> num1 * num2
                    "÷" -> if (num2 != 0.0) num1 / num2 else Double.NaN
                    else -> 0.0
                }

                tvResult.text = result.toString()
                tvInput.text = "$oldNumber $operator $input"
                input = result.toString()
                oldNumber = ""
                operator = ""
            }
        }

        findViewById<Button>(R.id.btnClear).setOnClickListener {
            input = ""
            oldNumber = ""
            operator = ""
            tvInput.text = ""
            tvResult.text = ""
        }
    }
}
