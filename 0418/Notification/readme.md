# 🥚 Android Egg Timer Notification App

이 프로젝트는 **EditText**로 입력한 시간만큼 타이머를 작동시키고, 완료되면 **알림(Notification)** 으로 사용자에게 알려주는 Android 앱입니다.

---

## ✅ 주요 기능

- 사용자가 분:초 형식으로 시간을 입력하면 타이머가 작동합니다.
- 타이머가 종료되면 알림(Notification)을 통해 알려줍니다.
- 알림 클릭 시 Google 홈페이지로 이동합니다.
- ConstraintLayout을 이용한 UI 구성

---

## 📷 화면 구성

- 앱 제목 텍스트 (`Egg Timer`)
- 계란 이미지
- 시간 입력 필드 (`EditText`, 기본값 01:00)
- 시작 버튼 (`Button`)

---

## 📁 프로젝트 구조

### 📄 Java: `MainActivity.java`

```java
package com.example.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class MainActivity extends AppCompatActivity {

    private EditText mEdiText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        mEdiText = (EditText) findViewById(R.id.edit);
        createNotificationChannel();
    }

    String NOTIFICATION_CHANNEL_ID = "my_channel_id_01";

    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "My Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            );
            notificationChannel.setDescription("Channel description");
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    public void sendNotification(){
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com/"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        notificationBuilder.setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Egg Timer")
                .setContentText("계란 삶기가 완료되었습니다.")
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notificationBuilder.build());
    }

    public void startTimer(View view){
        String s = mEdiText.getText().toString();
        int min = Integer.parseInt(s.substring(0, 2));
        int sec = Integer.parseInt(s.substring(3, 5));
        new CountDownTimer(min * 60 * 1000 + sec * 1000, 1000) {
            public void onTick(long millisUntilFinished){
                long minutes = millisUntilFinished / (60 * 1000);
                long seconds = (millisUntilFinished / 1000) % 60;
                mEdiText.setText(String.format("%02d:%02d", minutes, seconds));
            }

            public void onFinish(){
                mEdiText.setText("done!");
                sendNotification();
            }
        }.start();
    }
}
```

---

### 📄 XML: `activity_main.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:layout_marginTop="20dp"
    android:orientation="vertical"
    tools:context=".MainActivity">

    <TextView
        android:id="@+id/textView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="76dp"
        android:text="Egg Timer"
        android:textSize="40sp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <ImageView
        android:id="@+id/imageView1"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="32dp"
        app:srcCompat="@drawable/boiled_egg"
        android:contentDescription="삶은 계란 이미지"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/textView" />

    <EditText
        android:id="@+id/edit"
        android:layout_width="127dp"
        android:layout_height="41dp"
        android:layout_marginTop="32dp"
        android:gravity="center"
        android:text="01:00"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/imageView1" />

    <Button
        android:id="@+id/button"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:onClick="startTimer"
        android:text="계란 삶기 시작"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/edit" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

---

## ▶️ 실행 방법

1. Android Studio로 프로젝트를 엽니다.
2. 에뮬레이터 또는 실기기에서 실행합니다.
3. `EditText`에 시간을 입력 후 `계란 삶기 시작` 버튼을 누릅니다.
4. 시간이 완료되면 알림이 도착합니다.

---

## ℹ️ 참고사항

- 알림 채널은 Android 8.0(Oreo) 이상에서 필수입니다.
- 알림 아이콘과 계란 이미지 리소스를 `res/drawable` 폴더에 넣어야 합니다 (`ic_launcher_background`, `boiled_egg` 등).
- 입력 형식은 `MM:SS`로 제한되어 있습니다.

---

## 🪪 라이센스

MIT License
