package com.example.timerclaude;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class MainActivity extends AppCompatActivity {

    private TextView timerTextView;
    private Button startButton;
    private CountDownTimer countDownTimer;
    private MediaPlayer mediaPlayer;
    private boolean isTimerRunning = false;

    private static final String CHANNEL_ID = "timer_notification_channel";
    private static final int NOTIFICATION_ID = 1;
    private static final int EXTENSION_TIME = 10; // 연장 시간(초)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerTextView = findViewById(R.id.timerTextView);
        startButton = findViewById(R.id.startButton);

        // 알림 채널 생성
        createNotificationChannel();

        // 효과음 준비
        mediaPlayer = MediaPlayer.create(this, R.raw.alarm_sound);

        startButton.setOnClickListener(v -> {
            if (isTimerRunning) {
                stopTimer();
            } else {
                startTimer();
            }
        });
    }

    private void startTimer() {
        startTimer(10); // 기본 10초
    }

    private void startTimer(int seconds) {
        isTimerRunning = true;
        startButton.setText("타이머 중지");

        countDownTimer = new CountDownTimer(seconds * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // 남은 초를 표시
                int secondsRemaining = (int) (millisUntilFinished / 1000);
                timerTextView.setText(String.valueOf(secondsRemaining));
            }

            @Override
            public void onFinish() {
                timerTextView.setText("0");
                isTimerRunning = false;
                startButton.setText("타이머 시작");

                // 타이머 종료 시 효과음 재생
                playAlarmSound();

                // 알림 표시
                showNotification();

                // 대화상자로 연장 여부 물어보기
                showExtensionDialog();
            }
        }.start();
    }

    private void showExtensionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("타이머 연장")
                .setMessage(EXTENSION_TIME + "초 더 타이머를 연장하시겠습니까?")
                .setPositiveButton("연장하기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 효과음 중지
                        if (mediaPlayer.isPlaying()) {
                            mediaPlayer.stop();
                            mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.alarm_sound);
                        }

                        // 타이머 연장
                        startTimer(EXTENSION_TIME);
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setCancelable(false) // 뒤로가기 버튼으로 대화상자를 닫을 수 없음
                .show();
    }

    private void stopTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        timerTextView.setText("10");
        isTimerRunning = false;
        startButton.setText("타이머 시작");
    }

    private void playAlarmSound() {
        // 이미 재생 중이면 처음부터 다시 재생
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer = MediaPlayer.create(this, R.raw.alarm_sound);
        }
        mediaPlayer.start();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "타이머 알림",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("타이머 완료 알림을 위한 채널입니다");

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void showNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("타이머 완료")
                .setContentText("10초 타이머가 완료되었습니다.")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
