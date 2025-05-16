package com.example.snow;

import android.os.Bundle;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private com.example.snow.SnowfallView snowfallView;
    private SeekBar snowCountSeekBar;
    private SeekBar snowSizeSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        snowfallView = findViewById(R.id.snowfallView);
        snowCountSeekBar = findViewById(R.id.snowCountSeekBar);
        snowSizeSeekBar = findViewById(R.id.snowSizeSeekBar);

        // 시크바 이벤트 리스너 설정
        snowCountSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                snowfallView.setSnowflakeCount(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        snowSizeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // 최소 크기 2로 설정
                snowfallView.setSnowflakeSize(Math.max(2, progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        // 초기값 설정
        snowfallView.setSnowflakeCount(snowCountSeekBar.getProgress());
        snowfallView.setSnowflakeSize(Math.max(2, snowSizeSeekBar.getProgress()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        snowfallView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        snowfallView.pause();
    }
}