package com.example.paintboard;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    private DrawingView drawingView;
    private ImageButton currentPaint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 툴바 설정
        Toolbar toolbar = findViewById(R.id.drawing_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.app_name);
        }

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 메뉴 인플레이션
        getMenuInflater().inflate(R.menu.drawing_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // 툴바 메뉴 아이템 처리
        int id = item.getItemId();

        if (id == R.id.action_brush) {
            // 브러시 모드로 변경
            drawingView.setErase(false);
            Toast.makeText(this, "브러시 모드", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_erase) {
            // 지우개 모드로 변경
            drawingView.setErase(true);
            Toast.makeText(this, "지우개 모드", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_new) {
            // 새 그림 시작
            drawingView.startNew();
            Toast.makeText(this, "새 그림", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
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