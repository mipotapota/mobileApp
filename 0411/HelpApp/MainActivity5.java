package com.example.help;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity5 extends AppCompatActivity {
    ImageView imageView;
    ImageButton prevButton, nextButton;
    TextView imageCounter;

    // 이미지 리소스 배열 생성 (총 12개)
    private int[] imageResources = {
            R.drawable.one,
            R.drawable.two,
            R.drawable.three,
            R.drawable.four,    // 추가 이미지들 (파일명에 맞게 수정 필요)
            R.drawable.five,
            R.drawable.six,
            R.drawable.seven,
            R.drawable.eight,
            R.drawable.nine,
            R.drawable.ten,
            R.drawable.eleven,
            R.drawable.twelven,
            R.drawable.thirtin
    };

    private int currentImageIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main5);

        imageView = findViewById(R.id.imageView);
        prevButton = findViewById(R.id.prevButton);
        nextButton = findViewById(R.id.nextButton);
        imageCounter = findViewById(R.id.imageCounter);

        // 초기 이미지와 카운터 설정
        imageView.setImageResource(imageResources[currentImageIndex]);
        updateCounter();
    }

    // 이전 이미지 버튼 클릭 시
    public void prevImage(View v) {
        currentImageIndex--;
        if (currentImageIndex < 0) {
            currentImageIndex = imageResources.length - 1; // 마지막 이미지로 순환
        }
        imageView.setImageResource(imageResources[currentImageIndex]);
        updateCounter();
    }

    // 다음 이미지 버튼 클릭 시
    public void nextImage(View v) {
        currentImageIndex++;
        if (currentImageIndex >= imageResources.length) {
            currentImageIndex = 0; // 첫 이미지로 순환
        }
        imageView.setImageResource(imageResources[currentImageIndex]);
        updateCounter();
    }

    // 카운터 텍스트 업데이트
    private void updateCounter() {
        imageCounter.setText((currentImageIndex + 1) + " / " + imageResources.length);
    }
}