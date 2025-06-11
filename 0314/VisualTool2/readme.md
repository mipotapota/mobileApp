# 📱 전화 걸기 앱 (Call Taxi App)

이 간단한 Android 애플리케이션은 버튼을 클릭하면 특정 전화번호로 전화를 걸도록 `Intent`를 사용하는 기능을 포함합니다.

## 📂 파일 구성

```
📁 app/
 └── java/com/example/ex03/
     └── MainActivity.java
 └── res/layout/
     └── activity_main.xml
```

## 🔧 기능 설명

### 🧠 MainActivity.java

```java
package com.example.ex03;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
    }

    public void onClicked(View v) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:080-8212-5396"));
        startActivity(intent);
    }
}
```

- `onClicked()` 메서드는 버튼 클릭 시 호출됩니다.
- `Intent.ACTION_VIEW`와 `"tel:"` 스킴을 이용해 전화 앱을 실행하고, 지정된 번호(`080-8212-5396`)를 표시합니다.
- 실제 통화를 하지는 않고, 전화 앱으로 이동하여 번호만 표시합니다.

---

### 🖼 activity_main.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <Button
        android:id="@+id/button"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:onClick="onClicked"
        android:text="모범택시에 전화걸기"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />
</androidx.constraintlayout.widget.ConstraintLayout>
```

- "모범택시에 전화걸기"라는 텍스트의 버튼을 가운데에 배치합니다.
- 버튼을 클릭하면 `onClicked()` 함수가 실행됩니다.

---

## 🚀 실행 결과

- 사용자가 버튼을 클릭하면 시스템 전화 앱이 실행되고 `080-8212-5396` 번호가 자동으로 입력됩니다.

---

## 🛠️ 퍼미션 참고

> ⚠️ **전화를 직접 걸고 싶다면** 아래 퍼미션이 필요합니다:

### AndroidManifest.xml

```xml
<uses-permission android:name="android.permission.CALL_PHONE" />
```

### Java 코드 수정 예시

```java
Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:080-8212-5396"));
```

또한 Android 6.0 이상에서는 **런타임 퍼미션 요청**도 추가해야 합니다.

---

## ✅ 요구 사항

- Android Studio Bumblebee 이상
- 최소 SDK: 21 (Android 5.0 Lollipop)
- Java 또는 Kotlin 지원
- 인터넷 불필요

---

## 📄 라이선스

MIT License © 2025 [Your Name]

