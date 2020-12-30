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


    //TextView değişkeni
    private TextView mCalculatorDisplay;

    //Calculator class ı
    private Calculator mCalculator;
    //digitler
    private static final String DIGITS = "0123456789.";
    //en son operatör e basılıp basılmadığını tutar.
    private Boolean isOperand = true;


    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCalculator = new Calculator();
        mCalculatorDisplay = (TextView) findViewById(R.id.textView);

        //tüm butonlar için listener ekle
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

    //onClick eventini dinlemek için override ediyoruz
    @Override
    public void onClick(View v) {
        String calculatorText = mCalculatorDisplay.getText().toString();

        //basılan tuştaki digit i yada operatörü almak için
        String buttonPressed = ((Button) v).getText().toString();

        // digitlardan birine basıldı.
        if (DIGITS.contains(buttonPressed)) {

            //. ya basıldıysa
            if (buttonPressed.equals(".")) {
                String lastChar = calculatorText.substring(calculatorText.length() - 1);

                //son karakter operatör ve . değil ise . koy
                if (!lastChar.equals("*") && !lastChar.equals("/") && !lastChar.equals("+") && !lastChar.equals("-") && !lastChar.equals(".")) {
                    mCalculatorDisplay.append(buttonPressed);
                }

                //sayı basıldıysa
            } else {
                //sadece 0 var ise basılan sayıyı 0 yerine yaz yok ise basılan sayıyı ekle
                if (calculatorText.equals("0")) {
                    mCalculatorDisplay.setText(buttonPressed);
                } else {
                    mCalculatorDisplay.append(buttonPressed);
                }

                isOperand = false;
            }


        } else {
            //clear a basıldıysa 0 yaz
            if (buttonPressed.equals("Clear")) {
                mCalculatorDisplay.setText("0");

                //sonuç istendiyse Calculatır class ına işlemleri göndererek sonucu yazdır.
            } else if (buttonPressed.equals("=")) {
                //birden fazla . girildiğinde,(örneğin "2.2.2 * 2") programın çökmemesi için hata yakalama
                try {
                    mCalculatorDisplay.setText(mCalculator.calculateResult(mCalculatorDisplay.getText().toString()));
                } catch (NumberFormatException e) {
                    mCalculatorDisplay.setText("0");
                    Toast.makeText(MainActivity.this,
                            "Hatalı format girdiniz lütfen tekrar deneyin.", Toast.LENGTH_LONG).show();
                }

                //işaret değişimine basıldıysa ve ekranda operatör yok ise işareti değiştir. operatör var ise hiçbir şey yapma.
            } else if (buttonPressed.equals("+/-")) {
                if (!calculatorText.contains("+") && !calculatorText.contains("-") && !calculatorText.contains("*") && !calculatorText.contains("/") && !calculatorText.equals("0")) {
                    mCalculatorDisplay.setText("-" + calculatorText);
                }
                if (calculatorText.substring(0, 1).equals("-")) {
                    String newCalculatorText = calculatorText.substring(1);
                    mCalculatorDisplay.setText(newCalculatorText);
                }

                // "+-*/" tuşlarına basıldı ise
            } else {
                //daha önce operatör eklenmediyse ekler.
                if (!isOperand) {
                    mCalculatorDisplay.append(buttonPressed);
                    isOperand = true;
                }
            }


        }

    }


}
