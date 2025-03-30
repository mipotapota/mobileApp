package com.example.layoutbycode;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private LinearLayout mainLayout;
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        mainLayout = findViewById(R.id.mainLayout);
        addButton = findViewById(R.id.addButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //텍스트 뷰를 동적으로 생성하고 설정
                TextView textView = new TextView(MainActivity.this);
                textView.setText("동적으로 추가된 텍스트 뷰");

                //레이아웃 파라미터를 설정하여 레이아웃에 추가
                LinearLayout.LayoutParams layoutParams =
                        new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT );
                textView.setLayoutParams(layoutParams);

                mainLayout.addView(textView);
            }
        });
    }
}