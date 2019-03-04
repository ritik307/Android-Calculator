package com.first.ritik.calculator123

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
//import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

import kotlinx.android.synthetic.main.activity_main.* //watch Lecture 86
import kotlinx.android.synthetic.main.activity_main.view.*


private const val STATE_PENDING_OPERATION = "PendingOperation"
private const val STATE_OPERAND1 = "Operand1"
private const val STATE_CHANGE = "Operand1_Stored"
private const val TAG="MainActivity"

class MainActivity : AppCompatActivity() {
//    because line 11 perform this by default
//    private lateinit var result: EditText
//    private lateinit var newNumber: EditText
//    private val displayOperation by lazy(LazyThreadSafetyMode.NONE) { findViewById<TextView>(R.id.operation) } // ONE CAN USE lazy ONLY WITH vals

    private var operand1: Double? = null
    //private var operand2:Double?=null  because we only used it in a single function block so no need tot create var for that
    private var pendingOperation = "="
    //private var  flag =false //using it for making value -ve

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Log.d(TAG,"onCreate: called")
        setContentView(R.layout.activity_main)

//        because of line 11
//        result = findViewById(R.id.result)
//        newNumber = findViewById(R.id.newNumber)
//
//        val button0: Button = findViewById(R.id.button0)
//        val button1: Button = findViewById(R.id.button1)
//        val button2: Button = findViewById(R.id.button2)
//        val button3: Button = findViewById(R.id.button3)
//        val button4: Button = findViewById(R.id.button4)
//        val button5: Button = findViewById(R.id.button5)
//        val button6: Button = findViewById(R.id.button6)
//        val button7: Button = findViewById(R.id.button7)
//        val button8: Button = findViewById(R.id.button8)
//        val button9: Button = findViewById(R.id.button9)
//        val buttonDot: Button = findViewById(R.id.buttonDot)
//
//        val buttonEquals = findViewById<Button>(R.id.buttonEquals)
//        val buttonDivide = findViewById<Button>(R.id.buttonDivide)
//        val buttonMultiply = findViewById<Button>(R.id.buttonMultiply)
//        val buttonMinus = findViewById<Button>(R.id.buttonMinus)
//        val buttonPlus = findViewById<Button>(R.id.buttonPlus)

        val listener = View.OnClickListener { v ->
            val b = v as Button
            val temp=newNumber.text
            newNumber.append(b.text)
        }
        val acListener=View.OnClickListener { v->
            newNumber.setText("")
        }
        button0.setOnClickListener(listener)
        button1.setOnClickListener(listener)
        button2.setOnClickListener(listener)
        button3.setOnClickListener(listener)
        button4.setOnClickListener(listener)                     // ctrl+Q for documentation for the function
        button5.setOnClickListener(listener)
        button6.setOnClickListener(listener)
        button7.setOnClickListener(listener)
        button8.setOnClickListener(listener)
        button9.setOnClickListener(listener)
        buttonDot.setOnClickListener(listener)
        AC.setOnClickListener(acListener)

        val opListener = View.OnClickListener { v ->
            val op = (v as Button).text.toString()
            try {
                val value = newNumber.text.toString().toDouble()
                performOperation(value, op)
            } catch (e: NumberFormatException) {
                newNumber.setText("")
            }
            pendingOperation = op
            operation.text= pendingOperation
            //displayOperation.text = pendingOperation
        }

        buttonEquals.setOnClickListener(opListener)
        buttonDivide.setOnClickListener(opListener)
        buttonMultiply.setOnClickListener(opListener)
        buttonMinus.setOnClickListener(opListener)
        buttonPlus.setOnClickListener(opListener)

        val negListener=View.OnClickListener {v->
            val value=newNumber.text.toString()
            if(value.isEmpty())
            {
                newNumber.setText("-")
            }
            else
            {
                try {
                    var doubleValue= value.toDouble()
                    doubleValue *= -1
                    newNumber.setText(doubleValue.toString())
                }
                catch (e:java.lang.NumberFormatException)
                {
                    newNumber.setText("")
                }
            }
        }
        buttonNegitive.setOnClickListener(negListener)
    }

    private fun performOperation(value: Double, operation: String) {
        if (operand1 == null) {
            operand1 = value
            //flag=false
        } else {
            //operand2=value
            if (pendingOperation == "=") {
                pendingOperation = operation
                Log.d(TAG,"--------------------------------------------------===: called---------------------------")
                //flag=false
            }

            when (pendingOperation) {
                "=" -> operand1 = value//operand2
                "/" -> operand1 = if (/*operand2*/value == 0.0) {
                    Double.NaN
                } else {
                    operand1!! / value //operand2
                }
                "*" -> operand1 = operand1!! * value//operand2!!
                "+" -> operand1 = operand1!! + value//operand2!!
                "-" -> operand1 = operand1!! - value//operand2!!

            }
        }
        result.setText(operand1.toString())
        newNumber.setText("")
        //displayOperation.text=operation    because of line 11
    }

    override fun onSaveInstanceState(outState: Bundle) {  //Lecture 85 for explanation
        super.onSaveInstanceState(outState)
        //Log.d(TAG,"......................................onSaveInstance: called...................................")
        if (operand1 != null) {
           // Log.d(TAG,"......................................Running0...................................")
            outState.putDouble(STATE_OPERAND1, operand1!!)
            outState.putBoolean(STATE_CHANGE, true)
        }
        outState.putString(STATE_PENDING_OPERATION, pendingOperation!!)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {   //////////////changed Bundle? to Bundle
        super.onRestoreInstanceState(savedInstanceState)
       // Log.d(TAG,"......................................onRestoreInstance: called...................................")
        if (savedInstanceState.getBoolean(STATE_CHANGE, false)) {
            operand1 = savedInstanceState.getDouble(STATE_OPERAND1)
        } else {
            operand1 = null
        }
        pendingOperation = savedInstanceState.getString(STATE_PENDING_OPERATION)
        operation.text=pendingOperation
        //displayOperation.text = pendingOperation        because of line 11

    }

}
