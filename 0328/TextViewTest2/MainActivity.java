package com.example.textviewtest2;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    TextView tv1, tv2, tv3;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        tv1 = (TextView) findViewById(R.id.textView);
        tv2 = (TextView) findViewById(R.id.textView2);
        tv3 = (TextView) findViewById(R.id.textView3);

        tv1.setText("자바 코드로 변경하였습니다.");
        tv2.setTextColor(Color.BLUE);
        tv2.setTextSize(20); // 예시로 20으로 설정
        tv3.setTextScaleX(1.0f); // 원래 크기로 설정
        tv3.setTypeface(Typeface.SERIF, Typeface.ITALIC);
    }
}