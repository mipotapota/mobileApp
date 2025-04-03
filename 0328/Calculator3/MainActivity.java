package com.example.calculator3;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText firstName;
    EditText secondName;
    EditText value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Button bPlus = (Button)findViewById(R.id.b1);

        firstName = findViewById(R.id.fn);
        secondName = findViewById(R.id.sn);
        value = findViewById(R.id.v);
    }

    public void plus(View e) {
        String a = firstName.getText().toString();
        String b = secondName.getText().toString();
        int result =Integer.parseInt(a) + Integer.parseInt(b);
        value.setText("" + result);
    }
    public void minus(View e) {
        String a = firstName.getText().toString();
        String b = secondName.getText().toString();
        int result = Integer.parseInt(a) - Integer.parseInt(b);
        value.setText("" + result);
    }

    public void multiply(View e) {
        String a = firstName.getText().toString();
        String b = secondName.getText().toString();
        int result = Integer.parseInt(a) * Integer.parseInt(b);
        value.setText("" + result);
    }

    public void divide(View e) {
        String a = firstName.getText().toString();
        String b = secondName.getText().toString();
        int result = Integer.parseInt(a) / Integer.parseInt(b);
        value.setText("" + result);
    }
}

