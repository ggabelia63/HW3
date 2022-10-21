package com.example.hw3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {
    private lateinit var resultTextView: TextView
    private lateinit var equalsButton: Button

    private var operand: Double = 0.0
    private var operation: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.init()

        equalsButton.setOnClickListener {

            val secOperandText: String = resultTextView.text.toString()
            var secOperand: Double = 0.0

            if (secOperandText.isNotEmpty() && !secOperandText.contains("√")) {
                secOperand = if (isNegative(secOperandText)) {
                    secOperandText.replace("(", "").replace(")", "").toDouble()
                } else {
                    secOperandText.toDouble()
                }
            }

            if (secOperandText.isNotEmpty() && secOperandText.contains("√"))
                operand = secOperandText.replace("√", "").toDouble()

            when (this.operation) {
                "+" -> resultTextView.text = formatResult((operand + secOperand).toString())
                "-" -> resultTextView.text = formatResult((operand - secOperand).toString())
                "*" -> resultTextView.text = formatResult((operand * secOperand).toString())
                "/" -> resultTextView.text = formatResult((operand / secOperand).toString())
                "%" -> resultTextView.text = formatResult((operand / 100 * secOperand).toString())
                "√" -> resultTextView.text = formatResult((sqrt(operand).toString()))
            }

        }

    }

    private fun init() {
        resultTextView = findViewById(R.id.resultTextView)
        equalsButton = findViewById(R.id.equalsButton)
    }

    private fun formatResult(result: String): String {
        val res = result.split(".")
        return if (res[1].length == 1 && res[1] == "0") {
            res[0]
        } else result
    }



    fun numberClick(view: View) {
        if (view is Button) {

            val number = view.text.toString()
            var result = resultTextView.text.toString()

            if (result == "0") {
                result = ""
            }

            resultTextView.text = if (isOperator(resultTextView.text.toString())) {
                resultTextView.text = null
                number
            } else {
                if (result == "(-")
                    (result + number) + ")"
                else (result + number)
            }

        }

    }

    fun plusMinusClicked(view: View) {
        if (view is Button) {
            var result = resultTextView.text.toString()

            if (result == "0") {
                result = ""
            }

            resultTextView.text = if (isNegative(result)) {
                result.replace("-", "").replace("(", "").replace(")", "")
            } else {
                "(-${(result)})"
            }
        }
    }

    private fun isNegative(res: String): Boolean =
        res.contains("-") && res.contains("(") && res.contains(")")

    private fun isOperator(txt: String): Boolean =
        txt == "+" || txt == "-" || txt == "*" || txt == "/" || txt == "%"

    fun operationClick(view: View) {

        if (view is Button) {
            val restTxt = resultTextView.text.toString()
            if (restTxt.isNotEmpty()) {
                operand =
                    if (restTxt.contains("-") && restTxt.contains("(") && restTxt.contains(")")) {
                        restTxt.replace("(", "").replace(")", "").toDouble()
                    } else {
                        resultTextView.text.toString().toDouble()
                    }
            }

            operation = view.text.toString()

            resultTextView.text = operation
        }

    }

    fun clear(view: View) {
        operand = 0.0
        operation = ""
        resultTextView.text = null
    }

}