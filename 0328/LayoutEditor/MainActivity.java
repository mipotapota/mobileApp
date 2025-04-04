package com.example.layouteditor;

import android.graphics.Color; // 추가된 import
import android.os.Bundle;
import android.view.View; // 추가된 import
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    // 레이아웃 선언
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // 1. 레이아웃 객체 생성
        layout = findViewById(R.id.layout);

        // 2. 버튼 객체 생성
        Button changeBtn = findViewById(R.id.changeBtn);

        // 3. 버튼 클릭시 이벤트
        changeBtn.setOnClickListener(new View.OnClickListener() { // 수정된 부분
            @Override
            public void onClick(View view) {
                // rgb에 들어갈 변수 객체 생성
                int red = (int)(Math.random() * 255);
                int blue = (int)(Math.random() * 255);
                int green = (int)(Math.random() * 255);

                // 레이아웃 색상 변경
                layout.setBackgroundColor(Color.rgb(red, green, blue)); // 수정된 부분
            }
        });
    }
}
