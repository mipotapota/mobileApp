package com.example.help;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity3 extends AppCompatActivity {
    private TextView displayIdTextView, displayPasswordTextView, statusTextView;
    String id, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main3);

        displayIdTextView = findViewById(R.id.displayIdTextView);
        displayPasswordTextView = findViewById(R.id.displayPasswordTextView);
        statusTextView = findViewById(R.id.loginSuccess);
        Button checkButton = findViewById(R.id.button); // 버튼 추가

        // 인텐트에서 아이디와 비밀번호 데이터 가져오기
        Intent intent = getIntent();
        if (intent != null) {
            id = intent.getStringExtra("ID");
            password = intent.getStringExtra("Password");

            // 화면에 아이디와 비밀번호 출력
            displayIdTextView.setText("아이디 : " + id);
            displayPasswordTextView.setText("비밀번호 : " + password);
        }

        // 버튼 클릭 리스너 추가
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check(); // check 메소드 호출
            }
        });
    }

    public void check() {
        // 로그인 로직 (더미 데이터 사용)
        Intent intent = new Intent();
        if (isUserValid(id, password)) {
            // 인증 성공 시 메인 액티비티로 이동
            intent.putExtra("status", "로그인 성공!!!");
            setResult(RESULT_OK, intent);
            finish(); // MainActivity2로 돌아감

            // 2초 후 MainActivity4로 전환
            new Handler().postDelayed(() -> {
                Intent nextIntent = new Intent(MainActivity3.this, MainActivity4.class);
                startActivity(nextIntent);
            }, 2000); // 2000ms = 2초
        } else {
            // 인증 실패 시 메세지 표기
            intent.putExtra("status", "로그인 실패!!!");
            setResult(RESULT_OK, intent);
            finish(); // MainActivity2로 돌아감
        }
    }

    private boolean isUserValid(String username, String password) {
        // 실제로는 여기에서 서버 또는 로컬 데이터베이스를 통해 인증을 확인해야 한다.
        // 이 예제에서는 더미 데이터를 사용하여 단순하게 인증 성공 여부를 판단한다.
        return username.equals("san") && password.equals("1111");
    }
}
