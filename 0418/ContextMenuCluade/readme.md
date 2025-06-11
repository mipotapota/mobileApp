# Context Menu Background Color Changer (Android)

이 프로젝트는 Android의 **컨텍스트 메뉴(Context Menu)** 기능을 이용하여 텍스트를 길게 누르면 배경색을 바꿀 수 있는 예제 앱입니다.

---

## 📱 앱 기능

- 텍스트를 **길게 터치**하면 컨텍스트 메뉴가 나타납니다.
- 사용자 선택에 따라 앱의 **배경색이 변경**됩니다.
- 제공되는 색상 옵션:
  - 빨간색
  - 초록색
  - 파란색
  - 노란색
  - 흰색

---

## 🛠 주요 기능 요약

- `TextView`에 컨텍스트 메뉴 연결
- `LinearLayout` 배경색을 메뉴 선택에 따라 실시간으로 변경
- 직관적인 UI와 간결한 코드 구조

---

## 📂 프로젝트 구조

### 📄 Java: `MainActivity.java`

```java
package com.example.contextmenuclaude;

import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private LinearLayout mainLayout;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 레이아웃과 텍스트뷰 초기화
        mainLayout = findViewById(R.id.main_layout);
        textView = findViewById(R.id.text_view);

        // 텍스트뷰에 컨텍스트 메뉴 등록
        registerForContextMenu(textView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        // 컨텍스트 메뉴 아이템 추가
        menu.setHeaderTitle("배경색 선택");
        menu.add(0, 1, 0, "빨간색");
        menu.add(0, 2, 0, "초록색");
        menu.add(0, 3, 0, "파란색");
        menu.add(0, 4, 0, "노란색");
        menu.add(0, 5, 0, "흰색");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // 선택된 메뉴 아이템에 따라 배경색 변경
        switch (item.getItemId()) {
            case 1: // 빨간색
                mainLayout.setBackgroundColor(Color.RED);
                return true;
            case 2: // 초록색
                mainLayout.setBackgroundColor(Color.GREEN);
                return true;
            case 3: // 파란색
                mainLayout.setBackgroundColor(Color.BLUE);
                return true;
            case 4: // 노란색
                mainLayout.setBackgroundColor(Color.YELLOW);
                return true;
            case 5: // 흰색
                mainLayout.setBackgroundColor(Color.WHITE);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
```

---

### 📄 XML: `activity_main.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/main_layout"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:gravity="center"
    android:background="#FFFFFF"
    tools:context=".MainActivity">

    <TextView
        android:id="@+id/text_view"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:gravity="center"
        android:padding="16dp"
        android:text="화면을 길게 누르면 컨텍스트 메뉴가 나타납니다."
        android:textSize="18sp" />
</LinearLayout>
```

---

## ▶️ 실행 방법

1. Android Studio로 프로젝트를 열고 실행합니다.
2. 앱 실행 후 텍스트를 길게 눌러보세요.
3. 나타나는 메뉴에서 색상을 선택하면 화면 배경색이 변경됩니다.

---

## 💡 참고사항

- 이 예제는 Android 컨텍스트 메뉴 기능의 기본적인 사용법을 익히기에 적합합니다.
- 배경색은 `LinearLayout` 전체에 적용됩니다.
- 향후 기능 확장을 통해 사용자 지정 색상 선택, 다크모드 지원 등도 고려할 수 있습니다.

---

## 🪪 라이센스

MIT License
