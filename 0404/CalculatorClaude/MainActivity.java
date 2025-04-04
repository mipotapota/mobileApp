package com.example.calculatorclaude;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editNumber1, editNumber2;
    private Button btnAdd, btnSubtract, btnMultiply, btnDivide;
    private TextView textResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 뷰 초기화
        editNumber1 = findViewById(R.id.editNumber1);
        editNumber2 = findViewById(R.id.editNumber2);
        btnAdd = findViewById(R.id.btnAdd);
        btnSubtract = findViewById(R.id.btnSubtract);
        btnMultiply = findViewById(R.id.btnMultiply);
        btnDivide = findViewById(R.id.btnDivide);
        textResult = findViewById(R.id.textResult);

        // 버튼 클릭 리스너 설정
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate('+');
            }
        });

        btnSubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate('-');
            }
        });

        btnMultiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate('*');
            }
        });

        btnDivide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate('/');
            }
        });
    }

    private void calculate(char operator) {
        // 입력값 가져오기
        String num1Str = editNumber1.getText().toString();
        String num2Str = editNumber2.getText().toString();

        // 입력값 검증
        if (num1Str.isEmpty() || num2Str.isEmpty()) {
            Toast.makeText(this, "두 숫자를 모두 입력해주세요", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double num1 = Double.parseDouble(num1Str);
            double num2 = Double.parseDouble(num2Str);
            double result = 0;

            // 연산자에 따른 계산 수행
            switch (operator) {
                case '+':
                    result = num1 + num2;
                    break;
                case '-':
                    result = num1 - num2;
                    break;
                case '*':
                    result = num1 * num2;
                    break;
                case '/':
                    if (num2 == 0) {
                        Toast.makeText(this, "0으로 나눌 수 없습니다", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    result = num1 / num2;
                    break;
            }

            // 결과 표시
            // 결과가 정수인지 확인
            if (result == (int) result) {
                textResult.setText(String.valueOf((int) result));
            } else {
                textResult.setText(String.valueOf(result));
            }

        } catch (NumberFormatException e) {
            Toast.makeText(this, "유효하지 않은 입력입니다", Toast.LENGTH_SHORT).show();
        }
    }
}