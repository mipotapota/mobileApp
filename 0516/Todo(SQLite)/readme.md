# 📝 StylishTodo

Java & SQLite 기반의 디자인 중심 **To-Do List 앱**입니다. 안드로이드 스튜디오로 제작되었으며, 할 일과 날짜를 함께 관리할 수 있습니다.  

<img src="preview.png" alt="앱 미리보기" width="300" />

---

## 📱 주요 기능

- ✅ 할 일 추가
- 📅 날짜 선택 기능 (DatePicker 사용)
- 🗂 리스트 형태의 할 일 관리
- ❌ 삭제 기능
- 💾 SQLite로 데이터 영구 저장
- 🎨 깔끔한 디자인 UI (XML 커스텀)

---

## 🛠 개발 환경

- **언어:** Java  
- **DB:** SQLite (내장 DB)
- **IDE:** Android Studio
- **Min SDK:** 21 (Android 5.0 Lollipop)

---

## 📂 프로젝트 구조

StylishTodo/
├── java/com/example/stylishtodo/
│ ├── MainActivity.java // 메인 액티비티
│ ├── Task.java // 할 일 데이터 모델
│ ├── TaskAdapter.java // 리스트뷰 어댑터
│ └── DBHelper.java // SQLite 데이터베이스 헬퍼
├── res/layout/
│ ├── activity_main.xml // 메인 화면 UI
│ └── list_item_task.xml // 리스트 아이템 UI
└── AndroidManifest.xml

---

## ![스크린샷 2025-05-16 150605](https://github.com/user-attachments/assets/3b4d5314-1758-44cc-990e-70c6d26bc42a)![스크린샷 2025-05-16 150615](https://github.com/user-attachments/assets/105e6d67-a407-40c3-a9f5-1258983010c7)

| 메인 화면 | 할 일 추가 |
|-----------|-------------|
| ![main](screenshots/main.png) | ![add](screenshots/add.png) |

> 🔔 스크린샷은 `/screenshots` 폴더를 만들어 직접 추가해주세요.

---

## 🧪 사용 방법

1. Android Studio에서 프로젝트를 불러옵니다.
2. 에뮬레이터 또는 실제 기기에 앱을 실행합니다.
3. 화면 하단의 ➕ 버튼을 눌러 할 일을 입력하고 날짜를 선택합니다.
4. 저장된 할 일은 리스트에 표시되며, 삭제 아이콘으로 삭제할 수 있습니다.

---

## 📌 향후 업데이트 계획 (Optional)

- ✅ 할 일 완료 체크박스 기능
- ⭐ 중요도 설정 (별점 또는 색상)
- 🔔 알림 설정 기능
- 🔃 날짜별 정렬 및 필터 기능
- ☁️ 클라우드 연동 (Firebase 등)

---

## 🧑‍💻 개발자 정보

**김산 (San Kim)**  
- 💼 Full-stack Developer / UI·UX Designer  
- 🌐 [포트폴리오 바로가기](https://your-portfolio-link.com)  
- ✉️ email@example.com

---

## 📄 라이선스

MIT License  
자유롭게 사용, 수정 및 배포할 수 있습니다. PR 또는 Issue 제안도 환영합니다.

