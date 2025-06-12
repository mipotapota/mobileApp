package com.example.realapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnMenu1, btnMenu2, btnMenu3, btnMenu4;
    private ImageView imgLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 버튼 및 이미지뷰 초기화
        initializeViews();

        // 버튼 클릭 리스너 설정
        setupButtonListeners();
    }

    private void initializeViews() {
        btnMenu1 = findViewById(R.id.btn_menu1);
        btnMenu2 = findViewById(R.id.btn_menu2);
        btnMenu3 = findViewById(R.id.btn_menu3);
        btnMenu4 = findViewById(R.id.btn_menu4);
        imgLogo = findViewById(R.id.img_logo);
    }

    private void setupButtonListeners() {

        btnMenu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 오늘의 한마디 페이지로 이동
                Intent intent = new Intent(MainActivity.this, Menu1Activity.class);
                startActivity(intent);
            }
        });

        btnMenu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 오늘의 기분 페이지로 이동
                Intent intent = new Intent(MainActivity.this, Menu2Activity.class);
                startActivity(intent);
            }
        });

        btnMenu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 메트로놈 페이지로 이동
                Intent intent = new Intent(MainActivity.this, Menu3Activity.class);
                startActivity(intent);
            }
        });

        btnMenu4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Vocal Pitch 페이지로 이동
                Intent intent = new Intent(MainActivity.this, Menu4Activity.class);
                startActivity(intent);
            }
        });
    }
}