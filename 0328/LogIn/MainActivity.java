package com.example.contraints;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText editTextId;
    private EditText editTextPassword;
    private Button buttonLogin;
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextId = findViewById(R.id.editTextId);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewResult = findViewById(R.id.textViewResult);

        buttonLogin.setOnClickListener(v -> {
            String id = editTextId.getText().toString();
            String password = editTextPassword.getText().toString();

            if (id.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "아이디와 패스워드를 입력해주세요", Toast.LENGTH_SHORT).show();
                return;
            }

            // 로그인 처리 로직
            textViewResult.setText("로그인 시도: " + id);
        });
    }
}