# 📝 스타일리시 메모장 앱

안드로이드 스튜디오의 Java를 이용하여 개발된 세련된 디자인의 메모장 애플리케이션입니다. 
Material Design 가이드라인을 준수하여 시각적으로 매력적인 UI를 제공합니다.

## ✨ 주요 기능

- 📝 메모 작성, 편집, 삭제 기능
- 📋 메모 복사 기능
- 🎨 세련된 머티리얼 디자인 UI
- 💾 SQLite 데이터베이스를 이용한 메모 저장
- 📅 날짜와 시간 자동 기록

## 📱 스크린샷

(여기에 앱 스크린샷 이미지 추가)

## 🛠️ 기술 스택

- 언어: Java
- 개발 환경: Android Studio
- 최소 SDK 버전: API 21 (Android 5.0 Lollipop)
- 데이터베이스: SQLite
- 디자인: Material Design Components

## 📁 프로젝트 구조

```
app/
├── java/
│   └── com.example.memoapp/
│       ├── MainActivity.java      # 메모 목록 화면
│       ├── EditMemoActivity.java  # 메모 편집 화면
│       ├── MemoAdapter.java       # 메모 목록 어댑터
│       ├── Memo.java              # 메모 데이터 모델
│       └── MemoDatabase.java      # SQLite 데이터베이스 관리
└── res/
    ├── drawable/
    │   ├── ic_add.xml             # 추가 아이콘
    │   ├── ic_copy.xml            # 복사 아이콘
    │   ├── ic_save.xml            # 저장 아이콘
    │   └── ic_delete.xml          # 삭제 아이콘
    ├── layout/
    │   ├── activity_main.xml      # 메인 화면 레이아웃
    │   ├── activity_edit_memo.xml # 메모 편집 화면 레이아웃
    │   └── item_memo.xml          # 메모 목록 아이템 레이아웃
    ├── menu/
    │   └── menu_edit.xml          # 메모 편집 메뉴
    └── values/
        ├── colors.xml             # 색상 정의
        └── styles.xml             # 스타일 정의
```

## 📌 앱 기능 상세 설명

### 메인 화면 (MainActivity)
- 작성된 모든 메모 목록을 카드뷰 형태로 표시
- 플로팅 액션 버튼으로 새 메모 작성
- 각 메모 항목에는 제목, 내용 미리보기, 작성/수정 날짜 표시
- 메모 복사 버튼 제공

### 메모 편집 화면 (EditMemoActivity)
- 제목과 내용을 편집할 수 있는 인터페이스
- 자동 저장 기능
- 메모 삭제 기능
- 뒤로 가기 시 자동 저장

### 데이터 관리 (MemoDatabase)
- SQLite 데이터베이스 사용
- 메모 데이터 CRUD 작업 관리
- 시간순 정렬 기능

## 🔧 설치 방법

1. 이 저장소를 클론합니다:
```
git clone https://github.com/yourusername/MemoApp.git
```

2. Android Studio에서 프로젝트를 엽니다.

3. Gradle 동기화를 실행합니다.

4. 앱을 실행합니다.

## 🚀 개선 계획

- [ ] 다크 모드 지원
- [ ] 메모 검색 기능
- [ ] 메모 카테고리 분류
- [ ] 메모 공유 기능
- [ ] 백업 및 복원 기능

## 📄 라이선스

이 프로젝트는 MIT 라이선스 하에 배포됩니다. 자세한 내용은 [LICENSE](LICENSE) 파일을 참조하세요.

## 👤 작성자

Your Name - [이메일](mailto:your.email@example.com) - [GitHub](https://github.com/yourusername)

---

⭐️ 이 프로젝트가 마음에 드셨다면 별표를 눌러주세요![readme.md](https://github.com/user-attachments/files/20238729/readme.md)
