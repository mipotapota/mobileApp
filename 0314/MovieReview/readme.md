# 📱 영화 별점 평가 앱 (TermPaper01)

이 앱은 사용자가 시청한 영화에 대해 별점을 매기고, 해당 별점을 실시간으로 화면에 표시하는 간단한 Android 애플리케이션입니다.

## 🛠 기술 스택
- Java
- Android SDK
- Android Studio
- ConstraintLayout
- RatingBar

---

## ✨ 주요 기능

- **별점 평가 기능 (RatingBar)**  
  사용자가 0.5점 단위로 최대 5점까지 평가할 수 있습니다.

- **실시간 별점 출력 (TextView)**  
  별점을 조정할 때마다 현재 별점이 `TextView`에 반영됩니다.

- **제출 버튼 (Button)**  
  "제출하기!" 버튼이 포함되어 있으나, 현재 `onClick` 메서드는 구현되어 있지 않습니다.

---

## 📄 화면 구성 (activity_main.xml)

| 컴포넌트 | 설명 |
|---------|------|
| `TextView (id: textView)` | 영화 평가 요청 메시지를 표시 |
| `RatingBar (id: ratingBar)` | 사용자의 별점 입력을 받음 |
| `TextView (id: textView2)` | 현재 선택한 별점을 출력 |
| `Button (id: button)` | "제출하기!" 버튼, `onClicked()` 호출 예정 |

### 💡 UI 디자인 특징
- 텍스트와 버튼에 **회전 효과** 적용 (`rotation` 속성)
- 따뜻한 톤의 색상 사용 (`#fbc997`, `#FF9800`, `#a513d1`)
- **ConstraintLayout** 기반의 유연한 UI 배치

---

## 💻 MainActivity.java 요약

- `EdgeToEdge.enable(this)`를 통해 엣지-투-엣지 UI 활성화
- `RatingBar`의 값이 변경되면 `textView2`에 즉시 반영 (`setOnRatingBarChangeListener`)

```java
ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
    @Override
    public void onRatingChanged(RatingBar ratingBar1, float rating, boolean fromUser) {
        textView2.setText("별점 : " + rating);
    }
});
```

---

## 🔧 향후 개선 사항
- `onClicked(View view)` 메서드를 구현하여 별점 제출 시 서버로 전송하거나 토스트 메시지를 띄우는 기능 추가
- 별점 외에 영화 제목 또는 코멘트 입력 기능 추가
- 테마 및 애니메이션 강화로 더 나은 사용자 경험 제공

---

## 📷 스크린샷 (예정)
> 추후 실제 앱 실행 화면 추가 가능

---

## 📂 프로젝트 구조

```
com.example.termpaper01
├── MainActivity.java
└── res
    └── layout
        └── activity_main.xml
```

---

## 📝 작성자
- 김산 @ [GitHub](https://github.com/mipotapota)  
