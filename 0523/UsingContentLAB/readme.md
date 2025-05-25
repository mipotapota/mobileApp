# 📸 Android 이미지 뷰어 앱 (UsingContent)

안드로이드 기기에 저장된 모든 이미지를 탐색할 수 있는 간단하고 직관적인 이미지 뷰어 앱입니다.

## ✨ 주요 기능

- 📱 **전체 이미지 탐색**: 기기의 내부/외부 저장소에 있는 모든 이미지 표시
- 🔄 **이미지 네비게이션**: Previous/Next 버튼으로 쉬운 이미지 탐색
- 🔐 **동적 권한 관리**: Android 버전별 적절한 저장소 권한 자동 요청
- 📊 **이미지 정보 표시**: 파일명, 크기 등 상세 정보 제공
- 🎨 **사용자 친화적 UI**: 모던하고 직관적인 인터페이스
- 🔄 **순환 탐색**: 마지막 이미지에서 다음을 누르면 첫 번째 이미지로 이동
- 💾 **메모리 최적화**: 대용량 이미지도 안전하게 로드

## 📱 스크린샷
### ![스크린샷 2025-05-25 220720](https://github.com/user-attachments/assets/c9a5ae8a-fc72-4d6e-b366-ff82689e7959)
### ![스크린샷 2025-05-25 220729](https://github.com/user-attachments/assets/95a44c74-d5a3-4e19-a6a3-2d59e4a6531a)![스크린샷 2025-05-25 220744](https://github.com/user-attachments/assets/221da748-6e50-4e6b-9464-17af79a07cc0)

## 🛠️ 기술 스택

- **언어**: Java
- **플랫폼**: Android (API 21+)
- **아키텍처**: Single Activity
- **권한**: 
  - `READ_MEDIA_IMAGES` (Android 13+)
  - `READ_EXTERNAL_STORAGE` (Android 6-12)
  - `WRITE_EXTERNAL_STORAGE` (선택적, Android 6-9)

## 📋 요구사항

- **최소 SDK**: API 21 (Android 5.0)
- **타겟 SDK**: API 33 (Android 13)
- **권한**: 저장소 접근 권한 필요

## 🚀 설치 및 실행

### 1. 프로젝트 클론
```bash
git clone https://github.com/yourusername/UsingContent.git
cd UsingContent
```

### 2. Android Studio에서 열기
1. Android Studio 실행
2. "Open an existing Android Studio project" 선택
3. 클론한 폴더 선택

### 3. 빌드 및 실행
1. 기기 또는 에뮬레이터 연결
2. `Run` 버튼 클릭 또는 `Shift + F10`

## 📖 사용법

### 첫 실행
1. 앱을 실행하면 저장소 접근 권한을 요청합니다
2. "허용" 버튼을 눌러 권한을 승인해주세요
3. 자동으로 기기의 모든 이미지를 스캔합니다

### 이미지 탐색
- **Previous**: 이전 이미지로 이동
- **Next**: 다음 이미지로 이동
- **새로고침**: 이미지 목록 재스캔
- **권한 요청**: 권한이 거부된 경우 다시 요청

### 표시되는 정보
- 현재 이미지 순서 (예: 1 / 25)
- 파일명
- 파일 크기 (KB/MB 단위)

## 🔧 주요 구현 특징

### 권한 관리
```java
// Android 버전별 권한 설정
private void setupPermissions() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        // Android 13+
        requiredPermissions = new String[]{
            Manifest.permission.READ_MEDIA_IMAGES
        };
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        // Android 6-12
        requiredPermissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE
        };
    }
}
```

### 메모리 최적화
```java
// 대용량 이미지 안전 로딩
private Bitmap loadOptimizedBitmap(String imagePath) {
    BitmapFactory.Options options = new BitmapFactory.Options();
    options.inJustDecodeBounds = true;
    BitmapFactory.decodeFile(imagePath, options);
    
    options.inSampleSize = calculateInSampleSize(options, 1000, 800);
    options.inJustDecodeBounds = false;
    options.inPreferredConfig = Bitmap.Config.RGB_565;
    
    return BitmapFactory.decodeFile(imagePath, options);
}
```

### MediaStore 쿼리
```java
// 효율적인 이미지 검색
cursor = getContentResolver().query(
    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
    projection,
    MediaStore.Images.ImageColumns.SIZE + " > 0", // 0바이트 파일 제외
    null,
    MediaStore.Images.ImageColumns.DATE_ADDED + " DESC" // 최신순 정렬
);
```

## 📁 프로젝트 구조

```
app/
├── src/main/
│   ├── java/com/example/usingcontent/
│   │   └── MainActivity.java          # 메인 액티비티
│   ├── res/
│   │   ├── layout/
│   │   │   └── activity_main.xml      # 메인 레이아웃
│   │   ├── drawable/                  # 버튼 스타일 리소스
│   │   │   ├── button_previous.xml
│   │   │   ├── button_next.xml
│   │   │   ├── button_refresh.xml
│   │   │   └── button_permission.xml
│   │   └── values/                    # 문자열, 색상 등
│   └── AndroidManifest.xml           # 앱 설정 및 권한
```

## ⚙️ 설정

### 권한 설정 (AndroidManifest.xml)
```xml
<!-- Android 13+ -->
<uses-permission android:name="android.permission.READ_MEDIA_IMAGES" />

<!-- Android 6-12 -->
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" 
    android:maxSdkVersion="32" />

<!-- 필요시 쓰기 권한 -->
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"
    android:maxSdkVersion="28" />
```

## 🐛 문제 해결

### 이미지가 표시되지 않는 경우
1. 앱 설정에서 저장소 권한이 허용되어 있는지 확인
2. 기기에 이미지 파일이 실제로 존재하는지 확인
3. "새로고침" 버튼을 눌러 이미지 목록 재스캔

### 앱이 크래시되는 경우
1. 기기의 여유 메모리 확인
2. 매우 큰 이미지 파일이 있는지 확인
3. Logcat에서 에러 메시지 확인

### 권한 요청이 안 되는 경우
1. Android 설정 > 앱 > UsingContent > 권한에서 수동 허용
2. 앱 데이터 초기화 후 재실행

## 🔄 업데이트 로그

### v1.0.0 (2024-XX-XX)
- 초기 릴리스
- 기본 이미지 뷰어 기능
- Android 버전별 권한 처리
- 메모리 최적화 구현

## 🤝 기여하기

1. 이 저장소를 Fork 합니다
2. 새로운 기능 브랜치를 생성합니다 (`git checkout -b feature/AmazingFeature`)
3. 변경사항을 커밋합니다 (`git commit -m 'Add some AmazingFeature'`)
4. 브랜치에 푸시합니다 (`git push origin feature/AmazingFeature`)
5. Pull Request를 생성합니다

## 📝 라이선스

이 프로젝트는 MIT 라이선스 하에 배포됩니다. 자세한 내용은 [LICENSE](LICENSE) 파일을 참고하세요.

## 👨‍💻 개발자

- **개발자**: [Your Name](https://github.com/yourusername)
- **이메일**: your.email@example.com

## 🙏 감사의 말

- Android 개발 커뮤니티
- Stack Overflow의 도움
- 오픈소스 라이브러리 기여자들

---

⭐️ 이 프로젝트가 도움이 되었다면 Star를 눌러주세요!

## 📞 지원

문제가 있거나 질문이 있으시면 [Issues](https://github.com/yourusername/UsingContent/issues)에 등록해주세요.
