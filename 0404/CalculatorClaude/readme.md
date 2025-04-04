# 안드로이드 계산기 앱

간단한 두 숫자 계산기 안드로이드 애플리케이션입니다. 이 앱은 두 개의 숫자를 입력 받아 기본적인 사칙연산(+, -, *, /)을 수행합니다.

## 주요 기능

- 두 숫자 입력 필드
- 덧셈, 뺄셈, 곱셈, 나눗셈 연산 버튼
- 계산 결과 표시
- 입력값 검증 (빈 필드 검사, 0으로 나누기 방지)
- 오류 메시지 표시

## 스크린샷
![스크린샷 2025-04-04 162700](https://github.com/user-attachments/assets/72d913f7-c90a-42db-98fe-365260c51a50)
![스크린샷 2025-04-04 162715](https://github.com/user-attachments/assets/183702ec-21e0-4111-954f-ba8bc304d653)![스크린샷 2025-04-04 162724](https://github.com/user-attachments/assets/4a2cf0e6-576c-4ec7-9425-c8e866b3a37a)![스크린샷 2025-04-04 162730](https://github.com/user-attachments/assets/10eb3974-66f1-4ea9-894c-45cb77cbe8ad)![스크린샷 2025-04-04 162737](https://github.com/user-attachments/assets/dd8e3a37-56f0-4d97-bbbc-8212b8dfb47e)

## 구현 방법

### 레이아웃 (activity_main.xml)

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="16dp"
    tools:context=".MainActivity">

    <TextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Calculator"
        android:textSize="24sp"
        android:textColor="@android:color/white"
        android:background="#4CAF50"
        android:padding="8dp"/>

    <EditText
        android:id="@+id/editNumber1"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="16dp"
        android:hint="Number 1"
        android:inputType="numberDecimal"
        android:background="@drawable/edit_text_border"
        android:padding="12dp"/>

    <EditText
        android:id="@+id/editNumber2"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="16dp"
        android:hint="Number 2"
        android:inputType="numberDecimal"
        android:background="@drawable/edit_text_border"
        android:padding="12dp"/>

    <GridLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="16dp"
        android:columnCount="1"
        android:rowCount="4">

        <Button
            android:id="@+id/btnAdd"
            android:layout_width="80dp"
            android:layout_height="wrap_content"
            android:text="+"
            android:textSize="18sp"/>

        <Button
            android:id="@+id/btnSubtract"
            android:layout_width="80dp"
            android:layout_height="wrap_content"
            android:text="-"
            android:textSize="18sp"/>

        <Button
            android:id="@+id/btnMultiply"
            android:layout_width="80dp"
            android:layout_height="wrap_content"
            android:text="*"
            android:textSize="18sp"/>

        <Button
            android:id="@+id/btnDivide"
            android:layout_width="80dp"
            android:layout_height="wrap_content"
            android:text="/"
            android:textSize="18sp"/>
    </GridLayout>

    <TextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Result"
        android:layout_marginTop="16dp"/>

    <TextView
        android:id="@+id/textResult"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="8dp"
        android:text=""
        android:background="@drawable/edit_text_border"
        android:padding="12dp"
        android:textSize="18sp"/>

</LinearLayout>
```

### Drawable 리소스 (edit_text_border.xml)

```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android"
    android:shape="rectangle">
    <stroke
        android:width="1dp"
        android:color="#FF0000" />
    <corners android:radius="4dp" />
    <solid android:color="#FFFFFF" />
</shape>
```

### 자바 코드 (MainActivity.java)

```java
package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText editNumber1, editNumber2;
    private Button btnAdd, btnSubtract, btnMultiply, btnDivide;
    private TextView textResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 뷰 초기화
        editNumber1 = findViewById(R.id.editNumber1);
        editNumber2 = findViewById(R.id.editNumber2);
        btnAdd = findViewById(R.id.btnAdd);
        btnSubtract = findViewById(R.id.btnSubtract);
        btnMultiply = findViewById(R.id.btnMultiply);
        btnDivide = findViewById(R.id.btnDivide);
        textResult = findViewById(R.id.textResult);

        // 버튼 클릭 리스너 설정
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate('+');
            }
        });

        btnSubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate('-');
            }
        });

        btnMultiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate('*');
            }
        });

        btnDivide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate('/');
            }
        });
    }

    private void calculate(char operator) {
        // 입력값 가져오기
        String num1Str = editNumber1.getText().toString();
        String num2Str = editNumber2.getText().toString();

        // 입력값 검증
        if (num1Str.isEmpty() || num2Str.isEmpty()) {
            Toast.makeText(this, "두 숫자를 모두 입력해주세요", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double num1 = Double.parseDouble(num1Str);
            double num2 = Double.parseDouble(num2Str);
            double result = 0;

            // 연산자에 따른 계산 수행
            switch (operator) {
                case '+':
                    result = num1 + num2;
                    break;
                case '-':
                    result = num1 - num2;
                    break;
                case '*':
                    result = num1 * num2;
                    break;
                case '/':
                    if (num2 == 0) {
                        Toast.makeText(this, "0으로 나눌 수 없습니다", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    result = num1 / num2;
                    break;
            }

            // 결과 표시
            // 결과가 정수인지 확인
            if (result == (int) result) {
                textResult.setText(String.valueOf((int) result));
            } else {
                textResult.setText(String.valueOf(result));
            }
            
        } catch (NumberFormatException e) {
            Toast.makeText(this, "유효하지 않은 입력입니다", Toast.LENGTH_SHORT).show();
        }
    }
}
```

## Drawable 리소스 파일 작성 방법

drawable 리소스 파일은 Android에서 UI 컴포넌트의 모양과 스타일을 정의하는 XML 파일입니다.

### 생성 위치
- 프로젝트의 `res/drawable` 디렉토리

### 새 Drawable 파일 생성 방법
1. Android Studio에서 `res` 폴더 우클릭
2. `New` > `Android Resource File` 선택
3. Resource type을 `Drawable`로 설정
4. 파일 이름 입력 (예: `edit_text_border`)
5. Root element는 보통 `shape`로 설정
6. `OK` 클릭하여 생성

### 주요 shape 유형
- `rectangle`: 직사각형 (기본값)
- `oval`: 타원형
- `line`: 선
- `ring`: 고리 모양

### 주요 속성들
- `<solid>`: 배경 색상 지정
  ```xml
  <solid android:color="#FFFFFF" />
  ```

- `<stroke>`: 테두리 선 지정
  ```xml
  <stroke 
      android:width="2dp"
      android:color="#FF0000" 
      android:dashWidth="5dp"
      android:dashGap="3dp" />
  ```

- `<corners>`: 모서리 둥글기 지정
  ```xml
  <corners android:radius="8dp" />
  ```

- `<gradient>`: 그라데이션 효과
  ```xml
  <gradient
      android:startColor="#FFFF0000"
      android:endColor="#80FF00FF"
      android:angle="45"
      android:type="linear" />
  ```

- `<padding>`: 내부 여백 설정
  ```xml
  <padding
      android:left="10dp"
      android:top="10dp"
      android:right="10dp"
      android:bottom="10dp" />
  ```

- `<size>`: 크기 지정
  ```xml
  <size
      android:width="100dp"
      android:height="100dp" />
  ```

## 설치 및 실행 방법

1. Android Studio 설치
2. 프로젝트 클론 또는 다운로드
3. Android Studio에서 프로젝트 열기
4. 에뮬레이터나 실제 기기에서 앱 실행

## 개발 환경

- Android Studio
- Java
- 최소 SDK 버전: API 21 (Android 5.0 Lollipop)

## 라이센스

이 프로젝트는 MIT 라이센스에 따라 배포됩니다. 자세한 내용은 [LICENSE](LICENSE) 파일을 참조하세요.
