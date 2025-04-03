package com.example.calculator2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    private TextView display;
    private StringBuilder currentInput = new StringBuilder();
    private Stack<Double> numbers = new Stack<>();
    private Stack<Character> operators = new Stack<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.display);

        initializeButtons();
    }

    private void initializeButtons() {
        int[] buttonIds = {
                R.id.button_divide, R.id.button_multiply, R.id.button_minus, R.id.button_plus,
                R.id.button_7, R.id.button_8, R.id.button_9, R.id.button_back,
                R.id.button_4, R.id.button_5, R.id.button_6, R.id.button_ac,
                R.id.button_1, R.id.button_2, R.id.button_3,
                R.id.button_0, R.id.button_dot, R.id.button_equals
        };

        for (int id : buttonIds) {
            Button button = findViewById(id);
            button.setOnClickListener(new ButtonClickListener());
        }
    }

    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Button button = (Button) v;
            String buttonText = button.getText().toString();

            switch (buttonText) {
                case "AC":
                    currentInput.setLength(0);
                    numbers.clear();
                    operators.clear();
                    break;
                case "=":
                    calculateResult();
                    break;
                case "<":
                    if (currentInput.length() > 0) {
                        currentInput.deleteCharAt(currentInput.length() - 1);
                    }
                    break;
                default:
                    currentInput.append(buttonText);
                    break;
            }

            display.setText(currentInput.toString());
        }
    }

    private void calculateResult() {
        String input = currentInput.toString();
        if (input.isEmpty()) return;

        // 간단한 수식 파싱
        char[] chars = input.toCharArray();
        StringBuilder number = new StringBuilder();

        for (char c : chars) {
            if (Character.isDigit(c) || c == '.') {
                number.append(c);
            } else {
                if (number.length() > 0) {
                    numbers.push(Double.parseDouble(number.toString()));
                    number.setLength(0);
                }
                operators.push(c);
            }
        }

        if (number.length() > 0) {
            numbers.push(Double.parseDouble(number.toString()));
        }

        // 계산 수행
        double result = performCalculations();
        currentInput.setLength(0);
        currentInput.append(result);
        display.setText(currentInput.toString());

        // 스택 초기화
        numbers.clear();
        operators.clear();
    }

    private double performCalculations() {
        // 곱셈과 나눗셈 먼저 처리
        for (int i = 0; i < operators.size(); i++) {
            char operator = operators.get(i);
            if (operator == '*' || operator == '/') {
                double leftOperand = numbers.get(i);
                double rightOperand = numbers.get(i + 1);

                double tempResult = calculate(leftOperand, rightOperand, operator);

                // 결과를 스택에 저장하고 연산자 및 피연산자 제거
                numbers.set(i, tempResult);
                numbers.remove(i + 1);
                operators.remove(i);
                i--; // 인덱스를 조정
            }
        }

        // 덧셈과 뺄셈 처리
        double result = numbers.pop();
        while (!operators.isEmpty()) {
            char operator = operators.pop();
            double nextNumber = numbers.pop();

            // 뺄셈 처리 시 올바른 순서로 결과를 계산
            result = calculate(result, nextNumber, operator);
        }
        return result;
    }

    private double calculate(double left, double right, char operator) {
        switch (operator) {
            case '+':
                return left + right;
            case '-':
                return right - left;
            case 'x':
                return left * right;
            case '/':
                return (right != 0) ? left / right : 0; // 0으로 나누기 방지
            default:
                return 0;
        }
    }
}