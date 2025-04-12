package com.example.activityforresult;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {
    private TextView displayIdTextView, displayPasswordTextView, statusTextView;
    String id, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);

        displayIdTextView = findViewById(R.id.displayIdTextView);
        displayPasswordTextView = findViewById(R.id.displayPasswordTextView);
        statusTextView = findViewById(R.id.loginSuccess);

        Intent intent = getIntent();
        if (intent != null) {
            id = intent.getStringExtra("ID");
            password = intent.getStringExtra("Password");

            displayIdTextView.setText("아이디 : " + id);
            displayPasswordTextView.setText("비밀번호 : " + password);
        }
    }
    public void check(View e){
        Intent intent = new Intent();
        if (isUserValid(id, password)) {

            intent.putExtra("status", "로그인 성공!!!!");
        }else{
            intent.putExtra("status", "로그인 실패!!!!");
        }
        setResult(RESULT_OK, intent);
        finish();
    }
    private boolean isUserValid(String username, String password){
        return username.equals("kim") && password.equals("1234");
    }
}