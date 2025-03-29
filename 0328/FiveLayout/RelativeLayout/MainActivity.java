package com.example.relativelayout; // 패키지 이름을 실제 패키지에 맞게 수정하세요.

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText idInput;
    private EditText pwInput;
    private Button loginBtn;
    private Button joinBtn;
    private TextView displayText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // XML 레이아웃 파일 이름에 맞게 수정하세요.

        // UI 요소 초기화
        idInput = findViewById(R.id.idInput);
        pwInput = findViewById(R.id.pwInput);
        loginBtn = findViewById(R.id.loginBtn);
        joinBtn = findViewById(R.id.joinBtn);
        displayText = findViewById(R.id.displayText);

        // 로그인 버튼 클릭 리스너
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = idInput.getText().toString();
                String password = pwInput.getText().toString();
                displayText.setText("아이디: " + id + "\n패스워드: " + password);
            }
        });

        // 회원가입 버튼 클릭 리스너
        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 회원가입 로직을 여기에 추가하세요.
                displayText.setText("회원가입 버튼 클릭됨");
            }
        });
    }
}
