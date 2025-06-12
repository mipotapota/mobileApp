package com.example.realapp;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Menu4Activity extends AppCompatActivity {

    private static final String TAG = "VocalPitchAnalyzer";
    private static final int PERMISSION_REQUEST_CODE = 1001;
    private static final int SAMPLE_RATE = 44100;
    private static final int BUFFER_SIZE = AudioRecord.getMinBufferSize(
            SAMPLE_RATE, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);

    private AudioRecord audioRecord;
    private boolean isRecording = false;
    private Thread recordingThread;
    private Handler mainHandler;

    // 확장된 피치 범위 (50Hz ~ 2000Hz)
    private static final double MIN_FREQUENCY = 50.0;
    private static final double MAX_FREQUENCY = 2000.0;

    // UI Components (XML과 일치하도록 수정)
    private Button btnToggleRecording; // XML의 btn_toggle_recording과 일치
    private ImageButton btnBack;
    private TextView tvCurrentPitch;
    private TextView tvCurrentNote;
    private TextView tvFrequency;
    private TextView tvAveragePitch;
    private TextView tvPitchRange;
    private TextView tvRecordingStatus;
    private ProgressBar pitchMeter;
    private ImageView pitchIndicator;
    private View pitchBar;

    // Pitch Analysis
    private List<Double> pitchHistory = new ArrayList<>();
    private DecimalFormat decimalFormat = new DecimalFormat("#0.0");

    // Musical Notes
    private String[] noteNames = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
    private double A4_FREQUENCY = 440.0;

    // 애니메이션
    private ValueAnimator recordingAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu4);

        initializeViews();
        setupClickListeners();
        mainHandler = new Handler(Looper.getMainLooper());

        // 권한 확인 및 요청
        if (checkAudioPermission()) {
            initializeAudioRecord();
        } else {
            requestAudioPermission();
        }
    }

    @SuppressLint("WrongViewCast")
    private void initializeViews() {
        // XML ID와 일치하도록 수정
        btnToggleRecording = findViewById(R.id.btn_toggle_recording);
        btnBack = findViewById(R.id.btn_back);
        tvCurrentPitch = findViewById(R.id.tv_current_pitch);
        tvCurrentNote = findViewById(R.id.tv_current_note);
        tvFrequency = findViewById(R.id.tv_frequency);
        tvAveragePitch = findViewById(R.id.tv_average_pitch);
        tvPitchRange = findViewById(R.id.tv_pitch_range);
        tvRecordingStatus = findViewById(R.id.tv_recording_status);
        pitchMeter = findViewById(R.id.pitch_meter);
        pitchIndicator = findViewById(R.id.pitch_indicator);
        pitchBar = findViewById(R.id.pitch_bar);

        // 초기 상태 설정
        updateUI();
    }

    private void setupClickListeners() {
        btnToggleRecording.setOnClickListener(v -> {
            if (isRecording) {
                stopRecording();
            } else {
                startRecording();
            }
        });
        btnBack.setOnClickListener(v -> finish());
    }

    private boolean checkAudioPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                == PackageManager.PERMISSION_GRANTED;
    }

    private void requestAudioPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.RECORD_AUDIO},
                PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initializeAudioRecord();
                Toast.makeText(this, "마이크 권한이 허용되었습니다.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "마이크 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                tvRecordingStatus.setText("마이크 권한이 필요합니다");
            }
        }
    }

    private void initializeAudioRecord() {
        try {
            int bufferSize = Math.max(BUFFER_SIZE * 4, 8192); // 버퍼 크기 더 크게

            if (audioRecord != null) {
                if (audioRecord.getState() == AudioRecord.STATE_INITIALIZED) {
                    audioRecord.release();
                }
            }

            audioRecord = new AudioRecord(
                    MediaRecorder.AudioSource.MIC,
                    SAMPLE_RATE,
                    AudioFormat.CHANNEL_IN_MONO,
                    AudioFormat.ENCODING_PCM_16BIT,
                    bufferSize
            );

            // AudioRecord 상태 확인
            if (audioRecord.getState() != AudioRecord.STATE_INITIALIZED) {
                Log.e(TAG, "AudioRecord 초기화 실패");
                Toast.makeText(this, "오디오 레코드 초기화 실패", Toast.LENGTH_SHORT).show();
                audioRecord = null;
            } else {
                Log.d(TAG, "AudioRecord 초기화 성공");
            }
        } catch (SecurityException e) {
            Log.e(TAG, "오디오 레코드 초기화 실패: " + e.getMessage());
            Toast.makeText(this, "오디오 레코드 초기화 실패: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            audioRecord = null;
        }
    }

    private void startRecording() {
        if (!checkAudioPermission()) {
            requestAudioPermission();
            return;
        }

        if (audioRecord == null) {
            initializeAudioRecord();
            if (audioRecord == null || audioRecord.getState() != AudioRecord.STATE_INITIALIZED) {
                Toast.makeText(this, "오디오 레코드가 초기화되지 않았습니다.", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        if (audioRecord.getRecordingState() == AudioRecord.RECORDSTATE_RECORDING) {
            Toast.makeText(this, "이미 녹음 중입니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        isRecording = true;
        pitchHistory.clear();

        try {
            audioRecord.startRecording();
            Log.d(TAG, "녹음 시작됨");

            recordingThread = new Thread(this::recordAudio);
            recordingThread.start();

            updateUI();
            animateRecordingIndicator();

        } catch (IllegalStateException e) {
            Log.e(TAG, "녹음 시작 실패: " + e.getMessage());
            Toast.makeText(this, "녹음 시작 실패: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            isRecording = false;
            updateUI();
        }
    }

    private void stopRecording() {
        isRecording = false;
        Log.d(TAG, "녹음 중지 요청");

        // 애니메이션 중지
        if (recordingAnimator != null) {
            recordingAnimator.cancel();
            recordingAnimator = null;
        }

        if (audioRecord != null) {
            try {
                if (audioRecord.getRecordingState() == AudioRecord.RECORDSTATE_RECORDING) {
                    audioRecord.stop();
                }
            } catch (IllegalStateException e) {
                Log.w(TAG, "AudioRecord 정지 중 오류: " + e.getMessage());
            }
        }

        if (recordingThread != null) {
            try {
                recordingThread.join(1000); // 1초 타임아웃
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            recordingThread = null;
        }

        updateUI();
        resetDisplayValues();
    }

    private void resetDisplayValues() {
        tvCurrentPitch.setText("0.0 Hz");
        tvCurrentNote.setText("-");
        tvFrequency.setText("0 Hz");
        pitchMeter.setProgress(0);

        // 피치 인디케이터를 시작 위치로 이동하고 기본 이미지로 설정
        if (pitchIndicator != null) {
            pitchIndicator.setTranslationX(0);
            pitchIndicator.setImageResource(R.drawable.keki); // 기본 이미지로 설정
            // 둥근 배경과 속성들을 다시 설정
            pitchIndicator.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_background));
            pitchIndicator.setClipToOutline(true);
            pitchIndicator.setScaleType(ImageView.ScaleType.CENTER_CROP);
            pitchIndicator.setBackgroundColor(Color.parseColor("#e74c3c"));
        }
    }

    private void recordAudio() {
        int actualBufferSize = Math.max(BUFFER_SIZE, 4096);
        short[] buffer = new short[actualBufferSize];
        Log.d(TAG, "녹음 스레드 시작, 버퍼 크기: " + actualBufferSize);

        while (isRecording && audioRecord != null &&
                audioRecord.getRecordingState() == AudioRecord.RECORDSTATE_RECORDING) {
            try {
                int bytesRead = audioRecord.read(buffer, 0, buffer.length);
                Log.v(TAG, "읽은 바이트: " + bytesRead);

                if (bytesRead > 0) {
                    // 신호 강도 먼저 확인
                    double rms = calculateRMS(buffer, bytesRead);
                    Log.v(TAG, "RMS: " + rms);

                    double pitch = 0;
                    if (rms > 0.005) { // 임계값 낮춤
                        pitch = calculatePitch(buffer, bytesRead);
                        Log.v(TAG, "계산된 피치: " + pitch);
                    }

                    final double finalPitch = pitch;
                    final double finalRMS = rms;

                    // UI 업데이트를 메인 스레드에서 실행
                    mainHandler.post(() -> {
                        if (finalPitch > 0) {
                            pitchHistory.add(finalPitch);

                            // 최근 50개 데이터만 유지
                            if (pitchHistory.size() > 50) {
                                pitchHistory.remove(0);
                            }

                            updatePitchDisplay(finalPitch);
                            updatePitchVisualization(finalPitch);
                        } else {
                            updateNoPitchDisplay();
                        }

                        // 디버깅용 RMS 표시
                        Log.v(TAG, "RMS: " + finalRMS + ", Pitch: " + finalPitch);
                    });

                    // 업데이트 주기 조정
                    Thread.sleep(100);
                }
            } catch (Exception e) {
                Log.e(TAG, "녹음 중 오류: " + e.getMessage());
                e.printStackTrace();
                break;
            }
        }

        Log.d(TAG, "녹음 스레드 종료");
    }

    private double calculateRMS(short[] buffer, int length) {
        double rms = 0;
        for (int i = 0; i < length; i++) {
            double sample = buffer[i] / 32768.0;
            rms += sample * sample;
        }
        return Math.sqrt(rms / length);
    }

    private double calculatePitch(short[] buffer, int length) {
        // 자기상관함수를 사용한 피치 검출 (확장된 범위)
        double[] doubleBuffer = new double[length];
        for (int i = 0; i < length; i++) {
            doubleBuffer[i] = buffer[i] / 32768.0;
        }

        // 확장된 피치 범위 설정 (50Hz ~ 2000Hz)
        int minPeriod = SAMPLE_RATE / (int)MAX_FREQUENCY;  // 최대 2000Hz
        int maxPeriod = SAMPLE_RATE / (int)MIN_FREQUENCY;  // 최소 50Hz

        double maxCorrelation = 0;
        int bestPeriod = 0;

        // 자기상관함수 계산
        for (int period = minPeriod; period <= maxPeriod && period < length / 2; period++) {
            double correlation = 0;
            double norm1 = 0, norm2 = 0;

            for (int i = 0; i < length - period; i++) {
                correlation += doubleBuffer[i] * doubleBuffer[i + period];
                norm1 += doubleBuffer[i] * doubleBuffer[i];
                norm2 += doubleBuffer[i + period] * doubleBuffer[i + period];
            }

            // 정규화된 상관계수 계산
            if (norm1 > 0 && norm2 > 0) {
                correlation = correlation / Math.sqrt(norm1 * norm2);
            }

            if (correlation > maxCorrelation) {
                maxCorrelation = correlation;
                bestPeriod = period;
            }
        }

        Log.v(TAG, "최대 상관계수: " + maxCorrelation + ", 최적 주기: " + bestPeriod);

        // 임계값을 더 낮춤
        if (bestPeriod > 0 && maxCorrelation > 0.15) {
            double frequency = (double) SAMPLE_RATE / bestPeriod;

            // 확장된 합리적인 범위의 주파수만 반환
            if (frequency >= MIN_FREQUENCY && frequency <= MAX_FREQUENCY) {
                return frequency;
            }
        }

        return 0; // 피치를 찾을 수 없음
    }

    private void updatePitchDisplay(double pitch) {
        tvCurrentPitch.setText(decimalFormat.format(pitch) + " Hz");
        tvFrequency.setText(decimalFormat.format(pitch) + " Hz");

        String note = frequencyToNote(pitch);
        tvCurrentNote.setText(note);

        // 진행률 바 업데이트 (확장된 범위: 50Hz ~ 2000Hz)
        int progress = (int) Math.min(100, Math.max(0, ((pitch - MIN_FREQUENCY) / (MAX_FREQUENCY - MIN_FREQUENCY)) * 100));
        pitchMeter.setProgress(progress);

        // 평균 피치 계산
        if (!pitchHistory.isEmpty()) {
            double sum = 0;
            double min = Double.MAX_VALUE;
            double max = Double.MIN_VALUE;

            for (double p : pitchHistory) {
                sum += p;
                min = Math.min(min, p);
                max = Math.max(max, p);
            }

            double average = sum / pitchHistory.size();
            tvAveragePitch.setText("평균: " + decimalFormat.format(average) + " Hz");
            tvPitchRange.setText("범위: " + decimalFormat.format(max - min) + " Hz");
        }
    }

    private void updateNoPitchDisplay() {
        // 피치가 감지되지 않을 때의 표시는 유지하되 너무 자주 변경하지 않음
        tvCurrentPitch.setText("-- Hz");
        tvCurrentNote.setText("--");
        tvFrequency.setText("-- Hz");
        // 진행률 바는 0으로 설정
        pitchMeter.setProgress(0);
    }

    private String frequencyToNote(double frequency) {
        if (frequency <= 0) return "-";

        // A4(440Hz)를 기준으로 한 반음 계산
        double A4 = 440.0;
        double semitone = 12 * Math.log(frequency / A4) / Math.log(2);
        int roundedSemitone = (int) Math.round(semitone);

        // 옥타브 계산
        int octave = 4 + (roundedSemitone + 9) / 12;
        int noteIndex = ((roundedSemitone % 12) + 12) % 12;

        // 음정 편차 계산 (센트)
        double cents = (semitone - roundedSemitone) * 100;
        String centsStr = "";
        if (Math.abs(cents) > 5) {
            centsStr = (cents > 0 ? " +" : " ") + (int)cents + "¢";
        }

        return noteNames[noteIndex] + octave + centsStr;
    }

    private void updatePitchVisualization(double pitch) {
        // 피치 바 업데이트 (확장된 범위: 50Hz ~ 2000Hz)
        final float normalizedPitch = Math.max(0, Math.min(1, (float) ((pitch - MIN_FREQUENCY) / (MAX_FREQUENCY - MIN_FREQUENCY))));

        // 피치 인디케이터 위치 업데이트
        if (pitchBar != null && pitchIndicator != null) {
            pitchBar.post(() -> {
                if (pitchBar.getWidth() > 0) {
                    float targetX = normalizedPitch * (pitchBar.getWidth() - pitchIndicator.getWidth());

                    ObjectAnimator animator = ObjectAnimator.ofFloat(pitchIndicator, "translationX", targetX);
                    animator.setDuration(100);
                    animator.start();

                    // 피치에 따른 색상 변경
                    int color = getColorForPitch(normalizedPitch);
                    pitchIndicator.setBackgroundColor(color);

                    // 피치에 따른 이미지 변경
                    updatePitchIndicatorImage(pitch);
                }
            });
        }
    }

    private void updatePitchIndicatorImage(double pitch) {
        if (pitchIndicator == null) return;

        // 3단계로 나누어 이미지 변경
        // 범위: 50Hz ~ 2000Hz
        double range = MAX_FREQUENCY - MIN_FREQUENCY; // 1950Hz
        double lowThreshold = MIN_FREQUENCY + (range * 0.33);  // 약 693Hz
        double highThreshold = MIN_FREQUENCY + (range * 0.66); // 약 1336Hz

        if (pitch <= lowThreshold) {
            // 낮은 주파수 (50Hz ~ 693Hz): keki
            pitchIndicator.setImageResource(R.drawable.keki);
        } else if (pitch <= highThreshold) {
            // 중간 주파수 (693Hz ~ 1336Hz): tee
            pitchIndicator.setImageResource(R.drawable.tee);
        } else {
            // 높은 주파수 (1336Hz ~ 2000Hz): ang
            pitchIndicator.setImageResource(R.drawable.ang);
        }

        // 이미지 변경 후 둥근 배경과 속성들을 다시 설정
        pitchIndicator.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_background));
        pitchIndicator.setClipToOutline(true);
        pitchIndicator.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }

    private int getColorForPitch(float normalizedPitch) {
        // 낮은 음: 파란색, 높은 음: 빨간색
        float hue = (1 - normalizedPitch) * 240; // 240 (파란색) to 0 (빨간색)
        return Color.HSVToColor(new float[]{hue, 0.8f, 0.9f});
    }

    private void animateRecordingIndicator() {
        if (!isRecording) return;

        recordingAnimator = ValueAnimator.ofFloat(0.4f, 1.0f);
        recordingAnimator.setDuration(800);
        recordingAnimator.setRepeatCount(ValueAnimator.INFINITE);
        recordingAnimator.setRepeatMode(ValueAnimator.REVERSE);
        recordingAnimator.addUpdateListener(animation -> {
            if (isRecording && tvRecordingStatus != null) {
                float alpha = (Float) animation.getAnimatedValue();
                tvRecordingStatus.setAlpha(alpha);
            }
        });
        recordingAnimator.start();
    }

    private void updateUI() {
        if (isRecording) {
            btnToggleRecording.setText("중지");
            tvRecordingStatus.setText("● 녹음 중...");
            tvRecordingStatus.setVisibility(View.VISIBLE);
        } else {
            btnToggleRecording.setText("시작");
            tvRecordingStatus.setText("녹음 준비");
            tvRecordingStatus.setVisibility(View.VISIBLE);
            tvRecordingStatus.setAlpha(1.0f);
        }

        // 버튼 색상 또는 스타일 변경이 필요한 경우 여기에 추가
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopRecording();

        if (audioRecord != null) {
            if (audioRecord.getState() == AudioRecord.STATE_INITIALIZED) {
                audioRecord.release();
            }
            audioRecord = null;
        }

        if (recordingAnimator != null) {
            recordingAnimator.cancel();
            recordingAnimator = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isRecording) {
            stopRecording();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 필요한 경우 여기에 추가 로직
    }
}