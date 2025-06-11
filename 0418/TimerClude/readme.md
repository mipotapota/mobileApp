# ⏲️ TimerClaude - 10초 타이머 앱

이 Android 앱은 **기본 10초 타이머 기능**과 함께 **알람 사운드 재생**, **알림(Notification)**, **타이머 연장 다이얼로그**를 포함하는 타이머 앱입니다.

---

## ✅ 주요 기능

- 10초 카운트다운 타이머 실행
- 타이머 종료 시:
  - 사운드 알람 재생
  - 시스템 알림(Notification) 생성
  - 팝업(AlertDialog)을 통해 **10초 연장 여부 확인**
- 연장을 선택하면 다시 10초 타이머가 실행됨

---

## 📱 화면 UI (`activity_main.xml`)

```xml
<androidx.constraintlayout.widget.ConstraintLayout ... >

    <TextView
        android:id="@+id/timerTextView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="10"
        android:textSize="60sp"
        android:textStyle="bold"
        app:layout_constraint... />

    <Button
        android:id="@+id/startButton"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="타이머 시작"
        app:layout_constraint... />

</androidx.constraintlayout.widget.ConstraintLayout>
```

- 중앙에 **초 표시** `TextView`  
- 아래에 **타이머 시작/중지 버튼**

---

## 🧠 핵심 동작 로직 (`MainActivity.java`)

```java
startButton.setOnClickListener(v -> {
    if (isTimerRunning) {
        stopTimer();
    } else {
        startTimer();
    }
});
```

### 타이머 동작 흐름

1. **startTimer(int seconds)**: 1초 단위로 갱신되는 `CountDownTimer` 실행  
2. **onFinish()**:
   - `MediaPlayer`로 알람 사운드 재생  
   - Notification 생성 (`NotificationCompat.Builder`)  
   - AlertDialog로 "10초 연장하시겠습니까?" 팝업 표시

---

## 🔔 알림(Notification) 생성

```java
private void showNotification() {
    NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("타이머 완료")
            .setContentText("10초 타이머가 완료되었습니다.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true);

    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    notificationManager.notify(NOTIFICATION_ID, builder.build());
}
```

- Android 8.0 이상에서는 NotificationChannel 필요

---

## 🔊 사운드 알람 (`res/raw/alarm_sound.mp3` 필요)

```java
mediaPlayer = MediaPlayer.create(this, R.raw.alarm_sound);
mediaPlayer.start();
```

- 타이머 완료 시 효과음 재생  
- 연장 시 기존 소리 중단 후 다시 로딩

---

## 🧼 리소스 해제

```java
@Override
protected void onDestroy() {
    if (mediaPlayer != null) {
        mediaPlayer.release();
        mediaPlayer = null;
    }
    if (countDownTimer != null) {
        countDownTimer.cancel();
    }
    super.onDestroy();
}
```

---

## 📁 파일 구성 요약

| 파일명 | 설명 |
|--------|------|
| `MainActivity.java` | 메인 로직: 타이머, 사운드, 알림, 다이얼로그 |
| `activity_main.xml` | UI 레이아웃 (TextView + Button) |
| `res/raw/alarm_sound.mp3` | 사용자 정의 알람 사운드 (직접 추가 필요) |
| `res/drawable/ic_launcher_foreground.xml` | 알림 아이콘 |

---

## 🛠️ 필요 권한

- 별도의 퍼미션은 필요하지 않지만, **Android 13+ (Tiramisu)** 이상에서 알림 사용을 위해 `POST_NOTIFICATIONS` 권한 선언 필요할 수 있음.

```xml
<uses-permission android:name="android.permission.POST_NOTIFICATIONS"/>
```

---

## 🪪 라이선스

MIT License
