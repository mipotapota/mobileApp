package com.example.paintboard;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private DrawingView drawingView;
    private ImageButton currentPaint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawingView = findViewById(R.id.drawing_view);

        // 색상 팔레트 초기화
        LinearLayout paintLayout = findViewById(R.id.paint_colors);
        currentPaint = (ImageButton)paintLayout.getChildAt(0);
        currentPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));

        // 브러시 크기 조절 SeekBar
        SeekBar brushSize = findViewById(R.id.brush_size_bar);
        brushSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float newSize = (float) progress / 10;
                if(newSize < 1) newSize = 1;
                drawingView.setBrushSize(newSize);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // 지우개 버튼
        Button eraseBtn = findViewById(R.id.erase_btn);
        eraseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawingView.setErase(true);
            }
        });

        // 브러시 버튼 (지우개 모드에서 다시 그리기 모드로)
        Button brushBtn = findViewById(R.id.brush_btn);
        brushBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawingView.setErase(false);
            }
        });

        // 새로운 그림 버튼
        Button newBtn = findViewById(R.id.new_btn);
        newBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawingView.startNew();
            }
        });
    }

    // 색상 선택 메서드
    public void paintClicked(View view) {
        if(view != currentPaint) {
            ImageButton imgView = (ImageButton)view;
            String color = view.getTag().toString();
            drawingView.setColor(color);
            drawingView.setErase(false);

            // UI 업데이트
            imgView.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
            currentPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint));
            currentPaint = imgView;
        }
    }
}