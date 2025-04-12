package com.example.switchtest;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private Switch switchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        switchButton = findViewById(R.id.switchButton);

        //스위치 상태 변경 리스너 추가
        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //스위치 상태에 따라 전구 이미지 변경
                if(isChecked){
                    imageView.setImageResource(R.drawable.on_1);
                }else{
                    imageView.setImageResource(R.drawable.off_1);
                }
            }
        });
    }
}