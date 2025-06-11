# 🟠 5억년 버튼 앱

이 앱은 "5억년 버튼"이라는 흥미로운 콘셉트를 기반으로 한 간단한 Android 앱입니다. 사용자는 버튼을 누르면 100만 엔이 지급된다는 메시지를 보게 됩니다.

## 🛠 기술 스택
- Java (MainActivity.java에서 기능 구현 가능)
- Android SDK
- Android Studio
- ConstraintLayout

---

## ✨ 주요 기능 (예정)

- 버튼을 누르면 특수한 반응(예: 다이얼로그, 토스트 메시지, 애니메이션 등)을 추가할 수 있습니다.
- 현재는 UI만 구성되어 있으며, 클릭 이벤트는 연결되어 있지 않습니다.

---

## 📄 화면 구성 (activity_main.xml)

| 컴포넌트 | 설명 |
|---------|------|
| `TextView (id: textView)` | 메시지: "버튼을 누르면 100만엔이 지급됩니다." |
| `Button (id: button)` | "5억년 버튼" 텍스트가 있는 버튼 |

### 💡 UI 디자인 특징
- 중앙 정렬된 텍스트와 버튼
- ConstraintLayout 기반으로 다양한 해상도에 유연하게 대응 가능

---

## 💡 향후 구현 아이디어

- 버튼 클릭 시 100만 엔 수령 여부를 묻는 팝업 다이얼로그 표시
- 실제 5억년 버튼 콘셉트처럼 윤리적/철학적 선택을 반영한 시나리오 추가
- 버튼을 여러 번 누르면 누적 금액 계산

---

## 📷 스크린샷 (예정)
> 앱 실행 후 레이아웃 모습은 심플한 텍스트와 버튼으로 구성됨

---

## 📂 프로젝트 구조

```
com.example.termpaper01
├── MainActivity.java (예정)
└── res
    └── layout
        └── activity_main.xml
```

---

## 📝 작성자
- 김산 @ [GitHub](https://github.com/mipotapota)  
