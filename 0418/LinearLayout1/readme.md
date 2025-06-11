# Android LinearLayout 버튼 배치 예제

이 프로젝트는 Android의 **LinearLayout**을 사용하여 버튼들을 수평(horizontal)으로 나란히 배치하는 간단한 예제입니다.

---

## 📱 앱 기능

- 세 개의 버튼을 수평으로 정렬한 UI 예제입니다.
- 각 버튼은 고유한 텍스트와 색상을 가집니다.
- 레이아웃에 여백(`margin`)이 적용되어 간격이 깔끔하게 정돈되어 있습니다.

---

## 🛠 주요 특징

- `LinearLayout`의 `orientation="horizontal"` 설정을 통해 버튼을 가로로 배치
- 일부 버튼에 `backgroundTint` 속성을 지정하여 색상 강조
- 최소한의 코드로 레이아웃 구성 이해 가능

---

## 📂 프로젝트 구성

### 📄 Java: `MainActivity.java`

```java
package com.example.linearlayout1;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // 시스템 UI와의 경계 제거
        setContentView(R.layout.activity_main);
    }
}
```

---

### 📄 XML: `activity_main.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="horizontal"
    android:layout_margin="20dp">

    <Button
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:id="@+id/button01"
        android:text="버튼 1"
        android:backgroundTint="#FFBF00" />

    <Button
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:id="@+id/button02"
        android:text="버튼 2" />

    <Button
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:id="@+id/button03"
        android:text="버튼 3"
        android:backgroundTint="#088A68" />

</LinearLayout>
```

---

## ▶️ 실행 방법

1. Android Studio에서 이 프로젝트를 열고 실행하세요.
2. 앱을 실행하면 세 개의 버튼이 수평으로 나란히 배치된 화면이 표시됩니다.

---

## 💡 참고사항

- `EdgeToEdge.enable(this)`는 Android 10(API 29) 이상에서 전체 화면 UI를 구성할 때 유용합니다.
- 버튼들의 색상은 `backgroundTint` 속성으로 쉽게 커스터마이징 가능합니다.
- 이 구조를 기반으로 버튼 클릭 이벤트나 레이아웃 변경 등 기능을 확장할 수 있습니다.

---

## 🪪 라이센스

MIT License
