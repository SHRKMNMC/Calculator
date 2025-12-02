package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvDisplay;
    private double memory = 0;
    private String currentOperator = "";
    private double currentValue = 0;
    private boolean isNewOp = true;
    private String operationText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvDisplay = findViewById(R.id.tvDisplay);

        int[] numberButtons = {R.id.btn0,R.id.btn1,R.id.btn2,R.id.btn3,R.id.btn4,R.id.btn5,R.id.btn6,R.id.btn7,R.id.btn8,R.id.btn9};
        int[] operatorButtons = {R.id.btnPlus,R.id.btnMinus,R.id.btnMultiply,R.id.btnDivide};

        // Click en números
        View.OnClickListener numberClick = v -> {
            Button b = (Button)v;
            if (isNewOp || tvDisplay.getText().toString().equals("0")) {
                tvDisplay.setText(b.getText().toString());
                operationText = b.getText().toString();
                isNewOp = false;
            } else {
                tvDisplay.append(b.getText().toString());
                operationText += b.getText().toString();
            }
        };

        // Click en operadores
        View.OnClickListener operatorClick = v -> {
            Button b = (Button)v;
            double input = Double.parseDouble(tvDisplay.getText().toString());

            if (!currentOperator.isEmpty()) {
                calculate(input);
            } else {
                currentValue = input;
            }

            currentOperator = b.getText().toString();
            isNewOp = true;
            operationText += " " + currentOperator + " ";
            tvDisplay.setText(operationText);
        };

        for(int id : numberButtons) findViewById(id).setOnClickListener(numberClick);
        for(int id : operatorButtons) findViewById(id).setOnClickListener(operatorClick);

        // Igual
        findViewById(R.id.btnEquals).setOnClickListener(v -> {
            calculate(Double.parseDouble(tvDisplay.getText().toString()));
            currentOperator = "";
            isNewOp = true;
        });

        // Limpiar
        findViewById(R.id.btnClear).setOnClickListener(v -> {
            tvDisplay.setText("0");
            currentValue = 0;
            currentOperator = "";
            isNewOp = true;
            operationText = "";
        });

        // Punto decimal
        findViewById(R.id.btnDot).setOnClickListener(v -> {
            if(!tvDisplay.getText().toString().contains(".")) {
                tvDisplay.append(".");
                operationText += ".";
            }
        });

        // Cambio de signo
        findViewById(R.id.btnPlusMinus).setOnClickListener(v -> {
            double val = Double.parseDouble(tvDisplay.getText().toString()) * -1;
            tvDisplay.setText(formatResult(val));
            operationText = formatResult(val);
        });

        // Porcentaje
        findViewById(R.id.btnPercent).setOnClickListener(v -> {
            double val = Double.parseDouble(tvDisplay.getText().toString()) / 100;
            tvDisplay.setText(formatResult(val));
            operationText = formatResult(val);
        });

        // Memoria
        findViewById(R.id.btnMC).setOnClickListener(v -> {
            memory = 0;
        });

        findViewById(R.id.btnMR).setOnClickListener(v -> {
            tvDisplay.setText(formatResult(memory));
            operationText = formatResult(memory);
            isNewOp = true;
        });

        findViewById(R.id.btnMPlus).setOnClickListener(v -> {
            double current = Double.parseDouble(tvDisplay.getText().toString());
            memory += current;
            isNewOp = true;
        });

        findViewById(R.id.btnMMinus).setOnClickListener(v -> {
            double current = Double.parseDouble(tvDisplay.getText().toString());
            memory -= current;
            isNewOp = true;
        });
    }

    // Cálculo de operaciones básicas
    private void calculate(double input) {
        switch(currentOperator) {
            case "+": currentValue += input; break;
            case "−": currentValue -= input; break;
            case "×": currentValue *= input; break;
            case "÷":
                if(input != 0) currentValue /= input;
                else {
                    tvDisplay.setText("Error");
                    currentValue = 0;
                    currentOperator = "";
                    isNewOp = true;
                    operationText = "";
                    return;
                }
                break;
            default:
                currentValue = input;
        }
        tvDisplay.setText(formatResult(currentValue));
        operationText = formatResult(currentValue);
        isNewOp = true;
    }

    // Formato para mostrar números
    private String formatResult(double val) {
        if(val == (long) val)
            return String.format("%d",(long)val);
        else
            return String.format("%s", val);
    }
}
