package com.example.theguessinggame;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    EditText et;
    TextView tv1; // TextView 변수 선언
    int rn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et = findViewById(R.id.editTextText);
        tv1 = findViewById(R.id.textView1); // 초기화 추가
        generateRandomNumber(); // 초기 랜덤 숫자 생성
    }

    private void generateRandomNumber() {
        rn = new Random().nextInt(100) + 1;
    }

    public void playrandomnumber(View v) {
        String str = et.getText().toString();

        if (str.isEmpty()) {
            tv1.setText("숫자를 입력하세요.");
            return;
        }

        int user;
        try {
            user = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            tv1.setText("유효한 숫자를 입력하세요.");
            return;
        }

        // 1부터 100 사이의 숫자인지 확인
        if (user < 1 || user > 100) {
            tv1.setText("1부터 100까지의 숫자를 입력하세요.");
            return;
        }

        if (user > rn) {
            tv1.setText("Too High!!");
        } else if (user < rn) {
            tv1.setText("Too Low!!");
        } else {
            tv1.setText("You got it!!");
        }
        et.setText("");
    }
}
