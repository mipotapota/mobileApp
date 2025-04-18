package com.example.tempconverter;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        text = (EditText) findViewById(R.id.edit_Input);
    }

    public void onClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_change:
                RadioButton celsiusButton = (RadioButton) findViewById(R.id.celsius);
                RadioButton fahrenheitButton = (RadioButton) findViewById(R.id.fahrenheit);

                if (text.getText().length() == 0) {
                    Toast.makeText(this, "정확한 값을 입력하시오.", Toast.LENGTH_LONG).show();
                    return;
                }

                float inputValue = Float.parseFloat(text.getText().toString());
                if (celsiusButton.isChecked()){
                    text.setText(String.valueOf(convertFahrenheitToCelsius(inputValue)));
                    celsiusButton.setChecked(false);
                    fahrenheitButton.setChecked(true);
                }else{
                    text.setText(String.valueOf(convertCelsiusToFahrenheit(inputValue)));
                    fahrenheitButton.setChecked(false);
                    celsiusButton.setChecked(true);
                }
                break;
        }
    }

    private float convertFahrenheitToCelsius(float fahrenheit){
        return ((fahrenheit - 32) * 5 / 9.0f);
    }

    private float convertCelsiusToFahrenheit(float celsius){
        return ((celsius * 9) / 5) + 32.0f;
    }
}