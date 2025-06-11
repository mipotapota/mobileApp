# Context Menu Example (Android)

이 프로젝트는 Android의 **컨텍스트 메뉴(Context Menu)** 기능을 활용하여 텍스트 뷰의 배경색을 변경하는 간단한 예제입니다.

---

## 📱 화면 설명

텍스트를 길게 누르면 컨텍스트 메뉴가 표시되며, 다음 세 가지 배경색 중 하나로 변경할 수 있습니다:

- RED
- GREEN
- BLUE

---

## 🛠 주요 기능

- **TextView에 컨텍스트 메뉴 등록**
- **메뉴 선택에 따른 배경색 실시간 변경**
- **간결하고 직관적인 코드 구조**

---

## 📂 프로젝트 구조

### Java (MainActivity.java)

```java
package com.example.contextmenu;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    TextView text;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        text = (TextView) findViewById(R.id.TextView1);
        registerForContextMenu(text);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("컨텍스트메뉴");
        menu.add(0, 1, 0, "배경색: RED");
        menu.add(0, 2, 0, "배경색: Green");
        menu.add(0, 3, 0, "배경색: BLUE");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id==1){
            text.setBackgroundColor(Color.RED);
            return true;
        }else if (id==2){
            text.setBackgroundColor(Color.GREEN);
            return true;
        }else if (id==3){
            text.setBackgroundColor(Color.BLUE);
            return true;
        }
        return super.onContextItemSelected(item);
    }
}
```

---

### XML (activity_main.xml)

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/LinearLayout01"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <TextView
        android:id="@+id/TextView1"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Only I can change my life. No one can do it for me."
        android:textSize="200px"
        android:typeface="serif"/>
</LinearLayout>
```

---

## 🧪 실행 방법

1. Android Studio에서 프로젝트를 열고 실행합니다.
2. 앱이 실행되면 텍스트를 길게 눌러보세요.
3. 원하는 색상을 선택하면 배경색이 변경됩니다.

---

## 📌 참고사항

- `EdgeToEdge.enable(this)`는 Android의 경계 없는 UI 설정용 함수입니다 (API 30 이상).
- 텍스트 크기는 `px` 단위로 크게 설정되어 있어 실제 디바이스에서는 조정이 필요할 수 있습니다.

---

## 📃 라이센스

MIT License
