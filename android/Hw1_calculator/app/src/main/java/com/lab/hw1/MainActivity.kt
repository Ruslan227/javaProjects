package com.lab.hw1

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.lab.hw1.parser.Parser
import com.lab.hw1.parser.exceptions.ParsingException

const val RESULT = "result"

class MainActivity : AppCompatActivity() {
    private lateinit var output: TextView
    private lateinit var buttonOne: Button
    private lateinit var buttonTwo: Button
    private lateinit var buttonThree: Button
    private lateinit var buttonFour: Button
    private lateinit var buttonFive: Button
    private lateinit var buttonSix: Button
    private lateinit var buttonSeven: Button
    private lateinit var buttonEight: Button
    private lateinit var buttonNine: Button
    private lateinit var buttonZero: Button
    private lateinit var buttonCopy: Button
    private lateinit var buttonPlus: Button
    private lateinit var buttonMinus: Button
    private lateinit var buttonMultiply: Button
    private lateinit var buttonDivide: Button
    private lateinit var buttonC: Button
    private lateinit var buttonDel: Button
    private lateinit var buttonEq: Button
    private lateinit var buttonLeftBr: Button
    private lateinit var buttonRightBr: Button
    private lateinit var buttonDot: Button
    private val parser = Parser()
    private val textToZero = { output.text = "0" }

    private fun initialiseButtons() {
        output = findViewById(R.id.textView1)
        buttonOne = createButton(R.id.one)
        buttonTwo = createButton(R.id.two)
        buttonThree = createButton(R.id.three)
        buttonFour = createButton(R.id.four)
        buttonFive = createButton(R.id.five)
        buttonSix = createButton(R.id.six)
        buttonSeven = createButton(R.id.seven)
        buttonEight = createButton(R.id.eight)
        buttonNine = createButton(R.id.nine)
        buttonZero = createButton(R.id.zero)
        buttonPlus = createButton(R.id.plus) { addToExpression("+") }
        buttonMinus = createButton(R.id.minus) { addToExpression("-") }
        buttonMultiply = createButton(R.id.multiply) { addToExpression("*") }
        buttonDivide = createButton(R.id.divide) { addToExpression("/") }
        buttonLeftBr = createButton(R.id.left_br)
        buttonRightBr = createButton(R.id.right_br)
        buttonDot = createButton(R.id.dot) { addToExpression(".") }

        buttonCopy = createButton(R.id.copy) {
            val clipBoard: ClipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip: ClipData = ClipData.newPlainText("COPIED_CALC_RESULT", output.text.toString())
            clipBoard.setPrimaryClip(clip)
        }

        buttonC = createButton(R.id.C, textToZero)

        buttonDel = createButton(R.id.del) {
            if (output.text.length == 1)
                textToZero.invoke()
            else
                output.text = output.text.dropLast(1)
        }
        buttonEq = createButton(R.id.equals) {
            output.text =
                try {
                    parser.parse(output.text as String)
                        .evaluate()
                        .toString()
                } catch (e: ParsingException) {
                    // ignore
                    output.text
                }
        }
    }

    private fun doIfNotZero(ifBody: () -> Unit, elseBody: () -> Unit) {
        if (output.text.length == 1 && output.text[0] == '0')
            ifBody.invoke()
        else
            elseBody.invoke()
    }

    /**
     * @param: id - button's id
     * @param: action - to use unique button's method
     * @returns: created Button filled with id and text
     */
    private fun createButton(
        id: Int,
        action: (() -> Unit)? = null
    ): Button {
        val button = findViewById<Button>(id)
        button.setOnClickListener {
            if (action == null) {
                doIfNotZero({ output.text = "" }, {})
                addToExpression(button.text as String)
            } else {
                action.invoke()
            }
        }
        return button
    }

    @SuppressLint("SetTextI18n")
    private fun addToExpression(s: String) {
        output.text = output.text.toString() + s
    }

    private fun report(s: String) = Log.d("MAIN_ACTIVITY", s)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout)
        report("onCreate")
        initialiseButtons()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        report("onSaveInstanceState")
        outState.putString(RESULT, output.text.toString())
        super.onSaveInstanceState(outState)
    }

    @SuppressLint("SetTextI18n")
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        report("RESTORE")
        super.onRestoreInstanceState(savedInstanceState)
        output.text = savedInstanceState.getString(RESULT)
    }

    override fun onStart() {
        super.onStart()
        report("onStart")
    }

    override fun onResume() {
        super.onResume()
        report("onResume")
    }

    override fun onStop() {
        super.onStop()
        report("onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        report("onDestroy")
    }
}