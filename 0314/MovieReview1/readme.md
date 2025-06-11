# 📱 영화 별점 평가 앱 (TermPaper01)

이 앱은 사용자가 시청한 영화에 대해 별점을 매기고, 별점을 제출하면 감사 메시지를 띄운 후 앱을 종료하는 간단한 Android 애플리케이션입니다.

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
  버튼 클릭 시 토스트 메시지를 띄운 후 앱을 종료합니다.

---

## 📄 화면 구성 (activity_main.xml)

| 컴포넌트 | 설명 |
|---------|------|
| `TextView (id: textView)` | 영화 평가 요청 메시지를 표시 |
| `RatingBar (id: ratingBar)` | 사용자의 별점 입력을 받음 |
| `TextView (id: textView2)` | 현재 선택한 별점을 출력 |
| `Button (id: button)` | "제출하기!" 버튼, 클릭 시 `onClicked()` 호출 |

### 💡 UI 디자인 특징
- 텍스트와 버튼에 **회전 효과** 적용 (`rotation` 속성)
- 따뜻한 톤의 색상 사용 (`#fbc997`, `#FF9800`, `#a513d1`)
- **ConstraintLayout** 기반의 유연한 UI 배치

---

## 💻 MainActivity.java 요약

- `EdgeToEdge.enable(this)`를 통해 엣지-투-엣지 UI 활성화
- `RatingBar`의 값이 변경되면 `textView2`에 즉시 반영 (`setOnRatingBarChangeListener`)
- 버튼 클릭 시 토스트 메시지 출력 후 앱 종료

```java
public void onClicked(View view) {
    Toast.makeText(this, "참여해주셔서 감사합니다", Toast.LENGTH_SHORT).show();
    finish();
}
```

---

## 🔧 향후 개선 사항
- 제출 후 별점 데이터 저장 기능 추가 (예: SQLite, Firebase 등)
- 영화 제목, 장르, 감상평 입력 기능 추가
- 더 세련된 UI 애니메이션 및 전환 효과 도입

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
