package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText firstNumber;
    EditText secondNumber;
    EditText value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Button bPlus = (Button) findViewById(R.id.button1);

        firstNumber = findViewById(R.id.firstNumber);
        secondNumber = findViewById(R.id.secondNumber);
        value = findViewById(R.id.value);
    }

    public void plus(View e) {
        String a = firstNumber.getText().toString();
        String b = secondNumber.getText().toString();
        int result = Integer.parseInt(a) + Integer.parseInt(b);
        value.setText("" + result);
    }
    public void minus(View e) {
        String a = firstNumber.getText().toString();
        String b = secondNumber.getText().toString();
        int result = Integer.parseInt(a) - Integer.parseInt(b);
        value.setText("" + result);
    }
    public void multiply(View e) {
        String a = firstNumber.getText().toString();
        String b = secondNumber.getText().toString();
        int result = Integer.parseInt(a) * Integer.parseInt(b);
        value.setText("" + result);
    }
    public void divide(View e) {
        String a = firstNumber.getText().toString();
        String b = secondNumber.getText().toString();
        int result = Integer.parseInt(a) / Integer.parseInt(b);
        value.setText("" + result);
    }
}