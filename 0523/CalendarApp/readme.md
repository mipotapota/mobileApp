# 📅 Android Calendar App

간단하고 직관적인 안드로이드 캘린더 앱입니다. 일정 관리와 알림 기능을 제공합니다.

## ✨ 주요 기능

- 📱 **직관적인 캘린더 뷰**: 월별 캘린더로 날짜별 일정 확인
- ➕ **일정 추가**: 제목, 설명, 날짜, 시간을 설정하여 일정 생성  
- ✏️ **일정 수정**: 기존 일정을 클릭하여 수정 가능
- 🗑️ **일정 삭제**: 길게 누르거나 수정 화면에서 삭제 가능  
- 🔔 **스마트 알림**: 설정한 시간에 자동 푸시 알림
- 💾 **로컬 저장**: SQLite 데이터베이스를 통한 안전한 데이터 보관
- 📋 **일정 목록**: 선택한 날짜의 모든 일정을 한눈에 확인

## 📱 스크린샷

*[여기에 앱 스크린샷을 추가하세요]*

## 🔧 기술 스택

- **언어**: Java
- **플랫폼**: Android (API 21+)
- **데이터베이스**: SQLite
- **알림**: AlarmManager + BroadcastReceiver
- **UI**: Material Design Components

## 📋 요구사항

- **Android 5.0 (API 21) 이상**
- **타겟 SDK**: Android 14 (API 35)
- **최소 RAM**: 1GB
- **저장공간**: 50MB

## 🚀 설치 및 실행

### 1. 프로젝트 클론
```bash
git clone https://github.com/your-username/android-calendar-app.git
cd android-calendar-app
```

### 2. Android Studio에서 열기
1. Android Studio 실행
2. "Open an existing Android Studio project" 선택
3. 클론한 프로젝트 폴더 선택

### 3. 의존성 설치
```gradle
// app/build.gradle.kts에 이미 포함됨
implementation("androidx.work:work-runtime:2.9.0")
```

### 4. 빌드 및 실행
1. Sync Project with Gradle Files (`Ctrl+Shift+O`)
2. Build → Rebuild Project
3. Run 'app' (`Shift+F10`)

## 📁 프로젝트 구조

```
app/
├── src/main/java/com/example/calendarapp/
│   ├── MainActivity.java          # 메인 화면 (캘린더 + 일정 목록)
│   ├── EventActivity.java         # 일정 추가 화면
│   ├── Event.java                 # 일정 데이터 모델
│   ├── DatabaseHelper.java        # SQLite 데이터베이스 관리
│   ├── NotificationReceiver.java  # 알림 처리
│   └── EventAdapter.java          # 리스트뷰 어댑터
├── src/main/res/layout/
│   ├── activity_main.xml          # 메인 화면 레이아웃
│   └── activity_event.xml         # 일정 추가 화면 레이아웃
└── src/main/AndroidManifest.xml   # 앱 권한 및 컴포넌트 설정
```

## 🔐 권한

앱이 요청하는 권한들:

- `SCHEDULE_EXACT_ALARM`: 정확한 시간에 알림 설정
- `POST_NOTIFICATIONS`: 알림 표시
- `WAKE_LOCK`: 화면이 꺼진 상태에서도 알림 동작

## 🎯 사용법

### 일정 추가하기
1. 메인 화면에서 **+** 버튼 클릭
2. 일정 제목과 설명 입력
3. 날짜와 시간 선택
4. 알림이 필요하면 "알림 받기" 체크
5. **저장** 버튼 클릭

### 일정 수정하기
1. 메인 화면에서 수정할 일정 클릭
2. 수정 화면에서 내용 변경
3. **수정** 버튼 클릭

### 일정 삭제하기
- **방법 1**: 메인 화면에서 일정을 길게 누르고 삭제 확인
- **방법 2**: 일정 클릭 → 수정 화면 → **삭제** 버튼 클릭

### 알림 받기
- 일정 추가 시 "알림 받기"를 체크하면 설정한 시간에 자동 알림

## 🔄 향후 개발 계획

- [x] 일정 수정/삭제 기능
- [ ] 반복 일정 설정
- [ ] 카테고리별 색상 구분
- [ ] 위젯 지원
- [ ] 데이터 백업/복원
- [ ] 다크 모드 지원

## 🐛 버그 리포트

버그를 발견하시면 [Issues](https://github.com/your-username/android-calendar-app/issues)에 신고해 주세요.

## 🤝 기여하기

1. 프로젝트를 Fork 하세요
2. 새로운 브랜치를 생성하세요 (`git checkout -b feature/AmazingFeature`)
3. 변경사항을 커밋하세요 (`git commit -m 'Add some AmazingFeature'`)
4. 브랜치에 Push 하세요 (`git push origin feature/AmazingFeature`)
5. Pull Request를 생성하세요

## 📄 라이선스

이 프로젝트는 MIT 라이선스 하에 배포됩니다. 자세한 내용은 [LICENSE](LICENSE) 파일을 참조하세요.

## 👨‍💻 개발자

- **Your Name** - *Initial work* - [YourGitHub](https://github.com/your-username)

## 🙏 감사의 말

- Android Developers 공식 문서
- Material Design Guidelines
- Stack Overflow 커뮤니티

---

⭐ 이 프로젝트가 도움이 되었다면 별표를 눌러주세요!
