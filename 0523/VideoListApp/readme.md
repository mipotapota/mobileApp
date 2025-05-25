# Video List App 📹

Android 기기에 저장된 모든 동영상 파일을 검색하여 리스트로 표시하는 앱입니다.

## 📱 앱 소개

이 앱은 Android의 MediaStore API를 사용하여 기기에 저장된 동영상 파일들을 스캔하고, 사용자에게 깔끔한 리스트 형태로 보여줍니다. 파일명과 크기 정보를 함께 표시하여 사용자가 저장된 동영상들을 한눈에 파악할 수 있습니다.

## ✨ 주요 기능

- 📂 기기에 저장된 모든 동영상 파일 자동 검색
- 📋 동영상 파일명을 리스트뷰로 표시
- 📊 파일 크기 정보 표시 (KB, MB, GB 단위)
- 🔤 파일명 알파벳순 정렬
- 🔐 Android 버전별 적절한 권한 관리
- 💬 사용자 친화적인 상태 메시지

## 🖼️ 스크린샷

앱 실행 시 기기에 저장된 동영상 파일들이 다음과 같이 표시됩니다:
![스크린샷 2025-05-25 212215](https://github.com/user-attachments/assets/55838848-f008-4013-ad1a-290da205b5c1)

```
All Video Files
├── VID_20231022_103648.mp4 (15.2 MB)
├── VID_20231022_135756.mp4 (8.7 MB)
└── VID_20231022_165805.mp4 (22.1 MB)
```

## 🛠️ 기술 스택

- **언어**: Java
- **플랫폼**: Android
- **최소 SDK**: API 21 (Android 5.0)
- **권한**: READ_EXTERNAL_STORAGE (API ≤ 32), READ_MEDIA_VIDEO (API ≥ 33)
- **주요 API**: MediaStore, ContentResolver, Cursor

## 📋 요구사항

- Android 5.0 (API 21) 이상
- 외부 저장소 접근 권한
- 테스트용 동영상 파일 (카메라 앱으로 촬영 가능)

## 🚀 설치 및 실행

### 1. 프로젝트 클론
```bash
git clone https://github.com/yourusername/video-list-app.git
cd video-list-app
```

### 2. Android Studio에서 열기
1. Android Studio 실행
2. "Open an existing Android Studio project" 선택
3. 클론한 프로젝트 폴더 선택

### 3. 빌드 및 실행
1. 에뮬레이터 또는 실제 기기 연결
2. `Run` 버튼 클릭 또는 `Shift + F10`

### 4. 테스트 준비
앱을 테스트하려면 먼저 기기에 동영상 파일이 있어야 합니다:
- 에뮬레이터의 카메라 앱으로 몇 개의 동영상 촬영
- 또는 실제 기기에 저장된 동영상 파일 사용

## 📁 프로젝트 구조

```
app/src/main/
├── java/com/example/videolistapp/
│   └── MainActivity.java          # 메인 액티비티
├── res/
│   ├── layout/
│   │   └── activity_main.xml      # 메인 레이아웃
│   └── values/
│       └── strings.xml            # 문자열 리소스
└── AndroidManifest.xml            # 앱 매니페스트
```

## 🔐 권한 처리

이 앱은 Android 버전에 따라 다른 권한을 요청합니다:

- **Android 13+ (API 33+)**: `READ_MEDIA_VIDEO`
- **Android 12 이하 (API ≤ 32)**: `READ_EXTERNAL_STORAGE`

권한이 거부되면 앱에서 사용자에게 적절한 메시지를 표시합니다.

## 📝 주요 코드 설명

### ContentResolver를 사용한 동영상 파일 검색
```java
Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
Cursor cursor = resolver.query(uri, projection, null, null, sortOrder);
```

### 파일 정보 추출
```java
int nameIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME);
String fileName = cursor.getString(nameIndex);

int sizeIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE);
long fileSize = cursor.getLong(sizeIndex);
```

## 🐛 트러블슈팅

### 동영상이 표시되지 않는 경우
1. **권한 확인**: 앱 설정에서 저장소 권한이 허용되어 있는지 확인
2. **동영상 파일 존재**: 기기에 실제로 동영상 파일이 저장되어 있는지 확인
3. **에뮬레이터**: 에뮬레이터 사용 시 Extended Controls에서 카메라 기능 활성화

### 권한 요청이 나타나지 않는 경우
- 앱 정보에서 권한을 수동으로 재설정
- 앱 데이터 삭제 후 재설치

## 🔧 개발 환경

- **IDE**: Android Studio Arctic Fox 이상
- **Build Tool**: Gradle
- **Target SDK**: API 34
- **Min SDK**: API 21

## 📚 참고 자료

- [Android MediaStore 가이드](https://developer.android.com/training/data-storage/shared/media)
- [Android 권한 요청](https://developer.android.com/training/permissions/requesting)
- [ContentResolver 사용법](https://developer.android.com/reference/android/content/ContentResolver)

## 🤝 기여하기

1. 이 리포지토리를 포크합니다
2. 새로운 기능 브랜치를 생성합니다 (`git checkout -b feature/AmazingFeature`)
3. 변경사항을 커밋합니다 (`git commit -m 'Add some AmazingFeature'`)
4. 브랜치에 푸시합니다 (`git push origin feature/AmazingFeature`)
5. Pull Request를 생성합니다

## 📄 라이선스

이 프로젝트는 MIT 라이선스 하에 배포됩니다. 자세한 내용은 `LICENSE` 파일을 참조하세요.

## 👨‍💻 개발자

- **이름**: [Your Name]
- **이메일**: [your.email@example.com]
- **GitHub**: [@yourusername](https://github.com/yourusername)

## 📈 버전 히스토리

- **v1.0.0** (2024-XX-XX)
  - 초기 릴리즈
  - 기본적인 동영상 파일 리스트 기능
  - Android 버전별 권한 처리
  - 파일 크기 정보 표시

---

⭐ 이 프로젝트가 도움이 되었다면 스타를 눌러주세요!
