package com.calculator;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {



    private TextView mCalculatorDisplay;
    private Calculator mCalculator;
    private static final String DIGITS = "0123456789.";
    private Boolean isOperand = true;

    DecimalFormat df = new DecimalFormat("@###########");

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCalculator = new Calculator();
        mCalculatorDisplay = (TextView) findViewById(R.id.textView);

        df.setMinimumFractionDigits(0);
        df.setMinimumIntegerDigits(1);
        df.setMaximumIntegerDigits(8);

        findViewById(R.id.btn0).setOnClickListener(this);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
        findViewById(R.id.btn5).setOnClickListener(this);
        findViewById(R.id.btn6).setOnClickListener(this);
        findViewById(R.id.btn7).setOnClickListener(this);
        findViewById(R.id.btn8).setOnClickListener(this);
        findViewById(R.id.btn9).setOnClickListener(this);
        findViewById(R.id.btnTopla).setOnClickListener(this);
        findViewById(R.id.btnCikar).setOnClickListener(this);
        findViewById(R.id.btnCarp).setOnClickListener(this);
        findViewById(R.id.btnBol).setOnClickListener(this);
        findViewById(R.id.btnIsaretDegis).setOnClickListener(this);
        findViewById(R.id.btnNoktaKoy).setOnClickListener(this);
        findViewById(R.id.btnTemizle).setOnClickListener(this);
        findViewById(R.id.btnSonuc).setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {

        String buttonPressed = ((Button) v).getText().toString();

        if (DIGITS.contains(buttonPressed)) {

            // digit was pressed

            if (buttonPressed.equals(".")) {
                // ERROR PREVENTION
                // This will avoid error if only the decimal is hit before an operator, by placing a leading zero
                // before the decimal
                mCalculatorDisplay.setText(0 + buttonPressed);
            } else {
                if (mCalculatorDisplay.getText().toString().equals("0")) {
                    mCalculatorDisplay.setText(buttonPressed);
                } else {
                    mCalculatorDisplay.append(buttonPressed);
                }

                isOperand = false;
            }


        } else {
            if(buttonPressed.equals("Clear")) {
                mCalculatorDisplay.setText("0");

            } else if(buttonPressed.equals("=")) {
                mCalculatorDisplay.setText(mCalculator.calculateResult(mCalculatorDisplay.getText().toString()));
            }
            else if(buttonPressed.equals("+/-")){
                String calculatorText = mCalculatorDisplay.getText().toString();
                if(!calculatorText.contains("+") && !calculatorText.contains("-") && !calculatorText.contains("*") && !calculatorText.contains("/") ) {
                    mCalculatorDisplay.setText("-" + calculatorText);
                }
                if(calculatorText.substring(0,1).equals("-") ) {
                    String newCalculatorText = calculatorText.substring(1);
                    mCalculatorDisplay.setText(newCalculatorText);
                }
            }
            else {
                //üst üste operatör eklenmemesi için
                if (!isOperand) {
                    mCalculatorDisplay.append(buttonPressed);
                    isOperand = true;
                }
            }
            // operation was pressed


            //mCalculatorBrain.performOperation(buttonPressed);
            //mCalculatorDisplay.setText(df.format(mCalculatorBrain.getResult()));

        }

    }


}
