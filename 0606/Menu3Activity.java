package com.example.realapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class Menu3Activity extends AppCompatActivity {

    private ImageView ivPendulum;
    private TextView tvBpm, tvBeatCounter;
    private Button btnPlus, btnMinus, btnPlayPause, btnTap, btnTimeSignature;
    private ImageButton btnBack;  // ImageButton으로 별도 선언
    private SeekBar seekBarBpm;

    private int bpm = 120;
    private int maxBpm = 240;
    private int minBpm = 40;
    private boolean isPlaying = false;

    private Handler handler = new Handler();
    private Runnable beatRunnable;
    private int beatCounter = 1;
    private int timeSignature = 4;

    // 탭 템포 관련 변수
    private List<Long> tapTimes = new ArrayList<>();

    // 토스트 메시지 관리용 변수 추가
    private Toast currentToast;

    // SoundPool을 사용하여 사운드 재생
    private SoundPool soundPool;
    private int clickSoundId;
    private int accentSoundId; // 첫 박자용 강조 소리
    private ToneGenerator toneGenerator; // 백업용
    private AudioManager audioManager; // 볼륨 조절용

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu3);

        ivPendulum = findViewById(R.id.iv_pendulum);
        tvBpm = findViewById(R.id.tv_bpm);
        tvBeatCounter = findViewById(R.id.tv_beat_counter);
        btnPlus = findViewById(R.id.btn_plus);
        btnMinus = findViewById(R.id.btn_minus);
        btnPlayPause = findViewById(R.id.btn_play_pause);
        btnTap = findViewById(R.id.btn_tap);
        btnTimeSignature = findViewById(R.id.btn_time_signature);
        seekBarBpm = findViewById(R.id.seekbar_bpm);
        btnBack = findViewById(R.id.btn_back);

        // AudioManager 초기화
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        // SoundPool 초기화
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        soundPool = new SoundPool.Builder()
                .setMaxStreams(4)
                .setAudioAttributes(audioAttributes)
                .build();

        // 사운드 파일 로드
        try {
            clickSoundId = soundPool.load(this, R.raw.click, 1);
            accentSoundId = soundPool.load(this, R.raw.click, 1);
        } catch (Exception e) {
            // click.wav 파일이 없는 경우 ToneGenerator로 대체
            clickSoundId = -1;
            accentSoundId = -1;
            toneGenerator = new ToneGenerator(AudioManager.STREAM_MUSIC, ToneGenerator.MAX_VOLUME);
        }

        // 미디어 볼륨 컨트롤 활성화
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        // 뒤로가기 버튼 동작
        btnBack.setOnClickListener(v -> finish());

        // UI 업데이트 및 이벤트 설정
        updateBpmText();
        updateBeatCounterText();

        btnPlus.setOnClickListener(v -> {
            if (bpm < maxBpm) {
                bpm++;
                updateBpmText();
                seekBarBpm.setProgress(bpm - minBpm);
            }
        });

        btnMinus.setOnClickListener(v -> {
            if (bpm > minBpm) {
                bpm--;
                updateBpmText();
                seekBarBpm.setProgress(bpm - minBpm);
            }
        });

        seekBarBpm.setMax(maxBpm - minBpm);
        seekBarBpm.setProgress(bpm - minBpm);
        seekBarBpm.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                bpm = progress + minBpm;
                updateBpmText();
            }

            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        btnPlayPause.setOnClickListener(v -> {
            isPlaying = !isPlaying;
            btnPlayPause.setText(isPlaying ? "정지" : "시작");

            if (isPlaying) {
                startMetronome();
            } else {
                stopMetronome();
            }
        });

        // 탭 템포 버튼 기능 구현 (개선된 버전)
        btnTap.setOnClickListener(v -> {
            long currentTime = System.currentTimeMillis();
            tapTimes.add(currentTime);

            // 오래된 탭 기록 제거 (5초 전 기록은 삭제)
            tapTimes.removeIf(time -> currentTime - time > 5000);

            // 현재 박자 설정에 따른 최소 탭 수 계산
            int minTapsNeeded = getMinTapsForTimeSignature(timeSignature);

            if (tapTimes.size() >= minTapsNeeded) {
                calculateBpmFromTaps();
            } else {
                // 기존 토스트 취소하고 새로운 토스트 즉시 표시
                showTapToast("탭 " + tapTimes.size() + "/" + minTapsNeeded + " (" + timeSignature + "/4 박자)");
            }
        });

        // 박자 설정 버튼 (수정된 부분)
        btnTimeSignature.setOnClickListener(v -> {
            // 4/4 -> 3/4 -> 2/4 -> 4/4 순환
            if (timeSignature == 4) {
                timeSignature = 3;
            } else if (timeSignature == 3) {
                timeSignature = 2;
            } else {
                timeSignature = 4;
            }
            btnTimeSignature.setText(timeSignature + "/4");

            // 박자 변경 시 현재 박자 카운터 리셋
            beatCounter = 1;
            updateBeatCounterText();

            // 탭 템포 기록도 초기화 (박자가 바뀌면 기존 탭은 무효)
            tapTimes.clear();

            // 박자 변경 알림 토스트
            showTapToast("박자가 " + timeSignature + "/4로 변경되었습니다");
        });
    }

    // 박자별 필요한 탭 수를 반환하는 메서드 추가
    private int getMinTapsForTimeSignature(int timeSignature) {
        switch (timeSignature) {
            case 4: return 8; // 4/4 박자: 8번 탭 (2마디)
            case 3: return 6; // 3/4 박자: 6번 탭 (2마디)
            case 2: return 4; // 2/4 박자: 4번 탭 (2마디)
            default: return 8;
        }
    }

    // 토스트 메시지를 즉시 표시하는 메서드 추가
    private void showTapToast(String message) {
        // 기존 토스트가 있으면 취소
        if (currentToast != null) {
            currentToast.cancel();
        }

        // 새로운 토스트 생성 및 표시
        currentToast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        currentToast.show();
    }

    private void calculateBpmFromTaps() {
        int minTapsNeeded = getMinTapsForTimeSignature(timeSignature);
        if (tapTimes.size() < minTapsNeeded) return;

        // 최근 탭들의 평균 간격으로 BPM 계산
        long totalInterval = 0;
        int intervals = tapTimes.size() - 1;

        for (int i = 0; i < intervals; i++) {
            totalInterval += tapTimes.get(i + 1) - tapTimes.get(i);
        }

        if (intervals > 0) {
            double averageInterval = (double) totalInterval / intervals;
            int calculatedBpm = (int) Math.round(60000.0 / averageInterval);

            // BPM 범위 제한
            calculatedBpm = Math.max(minBpm, Math.min(maxBpm, calculatedBpm));

            bpm = calculatedBpm;
            updateBpmText();
            seekBarBpm.setProgress(bpm - minBpm);

            // BPM 설정 완료 토스트
            showTapToast("탭 템포 완료 (" + timeSignature + "/4): " + bpm + " BPM");

            // 탭 기록 초기화하여 새로운 탭 시작 준비
            tapTimes.clear();
        }
    }

    private void updateBpmText() {
        tvBpm.setText(String.valueOf(bpm));
    }

    private void updateBeatCounterText() {
        // 현재 박자 설정에 맞게 표시 (수정된 부분)
        tvBeatCounter.setText(beatCounter + " / " + timeSignature);
    }

    private void startMetronome() {
        beatRunnable = new Runnable() {
            @Override
            public void run() {
                // SoundPool을 사용하여 사운드 재생
                if (clickSoundId != -1) {
                    // WAV 파일 사용
                    float maxVolume = 1.0f;
                    int priority = 1;
                    int loop = 0;
                    float rate = 1.0f;

                    if (beatCounter == 1) {
                        // 첫 박자는 더 높은 우선순위로 재생
                        soundPool.play(accentSoundId, maxVolume, maxVolume, 2, loop, rate);
                    } else {
                        soundPool.play(clickSoundId, maxVolume, maxVolume, priority, loop, rate);
                    }
                } else {
                    // 백업용 ToneGenerator 사용
                    if (toneGenerator != null) {
                        if (beatCounter == 1) {
                            // 첫 박자는 더 높은 주파수와 긴 지속시간
                            toneGenerator.startTone(ToneGenerator.TONE_CDMA_HIGH_PBX_L, 200);
                        } else {
                            // 일반 박자는 짧은 지속시간
                            toneGenerator.startTone(ToneGenerator.TONE_CDMA_PIP, 150);
                        }
                    }
                }

                // 박자 카운터를 현재 박자 설정에 맞게 순환 (핵심 수정 부분)
                beatCounter = (beatCounter % timeSignature) + 1;
                updateBeatCounterText();

                long interval = 60000 / bpm;
                handler.postDelayed(this, interval);
            }
        };
        handler.post(beatRunnable);
    }

    private void stopMetronome() {
        handler.removeCallbacks(beatRunnable);
        beatCounter = 1;
        updateBeatCounterText();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 토스트 정리
        if (currentToast != null) {
            currentToast.cancel();
            currentToast = null;
        }

        if (soundPool != null) {
            soundPool.release();
        }
        if (toneGenerator != null) {
            toneGenerator.release();
        }
        handler.removeCallbacksAndMessages(null);
    }
}