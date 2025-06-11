# 🎨 LinearLayout 색상 변경 메뉴 앱

이 프로젝트는 **메뉴 항목을 통해 LinearLayout의 배경색을 변경하는 Android 앱**입니다.

---

## ✅ 주요 기능

- 화면에 표시된 LinearLayout을 사용자가 메뉴를 통해 다양한 색상으로 변경할 수 있음
- 메뉴 항목에 아이콘과 색상 이름 표시
- ConstraintLayout 내에 LinearLayout 포함

---

## 📁 프로젝트 구성

### 📄 XML: `activity_main.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    tools:context=".MainActivity">

    <LinearLayout
        android:id="@+id/layout"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="horizontal">
    </LinearLayout>

</androidx.constraintlayout.widget.ConstraintLayout>
```

- `ConstraintLayout` 안에 `LinearLayout`을 수평 방향(`horizontal`)으로 배치
- `LinearLayout`의 ID는 `layout`으로 지정하여 코드에서 참조 가능

---

### 📄 XML: `menu.xml` (예: `res/menu/menu_main.xml`)

```xml
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">

    <item
        android:id="@+id/blue"
        android:icon="@android:drawable/star_big_on"
        android:title="파랑색"
        app:showAsAction="never" />
        
    <item
        android:id="@+id/green"
        android:icon="@android:drawable/ic_btn_speak_now"
        android:title="초록색"
        app:showAsAction="never"/>
        
    <item
        android:id="@+id/red"
        android:icon="@android:drawable/checkbox_on_background"
        android:title="빨강색"
        app:showAsAction="never"/>

</menu>
```

- 세 가지 색상(파랑, 초록, 빨강)을 선택할 수 있는 메뉴 항목 제공
- 각 항목에 Android 기본 아이콘 부착
- `showAsAction="never"`로 인해 액션바가 아닌 메뉴로 표시

---

## ▶️ 구현 예시 (Java 코드 힌트)

```java
@Override
public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    LinearLayout layout = findViewById(R.id.layout);
    switch (item.getItemId()) {
        case R.id.blue:
            layout.setBackgroundColor(Color.BLUE);
            return true;
        case R.id.green:
            layout.setBackgroundColor(Color.GREEN);
            return true;
        case R.id.red:
            layout.setBackgroundColor(Color.RED);
            return true;
        default:
            return super.onOptionsItemSelected(item);
    }
}
```

> 위 코드는 `MainActivity.java`에 들어갈 수 있으며, 메뉴 선택 시 `layout` 배경색을 변경하는 핵심 로직입니다.

---

## 🪪 라이센스

MIT License
