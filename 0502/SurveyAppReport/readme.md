# 호텔 고객 만족도 설문조사 앱

호텔 서비스에 대한 고객 만족도를 수집하고 분석하는 안드로이드 애플리케이션입니다.

## 📱 앱 소개

이 애플리케이션은 호텔 이용 고객을 대상으로 다양한 서비스 항목에 대한 만족도를 조사하고 결과를 시각적으로 보여주는 설문조사 앱입니다. 사용자는 1점(매우 불만족)부터 5점(매우 만족)까지 평가할 수 있습니다.

## ✨ 주요 기능

- 호텔 서비스 관련 12개 항목 설문조사
- 이전/다음 버튼을 통한 쉬운 설문 탐색
- 응답 결과 시각화 (막대 그래프)
- 한국어 지원

## 🛠️ 기술 스택

- Java
- Android SDK
- MPAndroidChart 라이브러리 (그래프 시각화)

## 📋 설문 항목

애플리케이션에서 다루는 12개의 설문 항목은 다음과 같습니다:

1. 호텔 체크인 과정 만족도
2. 객실 청결도 평가
3. 직원 서비스 친절도
4. 호텔 시설(수영장, 헬스장 등) 만족도
5. 호텔 식당 음식 품질 평가
6. 호텔 주변 환경 및 위치 편리성
7. 가격 대비 가치 평가
8. 와이파이 및 기술 설비 만족도
9. 소음 관리 및 수면 품질 평가
10. 재방문 및 추천 의향
11. 호텔 예약 과정 용이성
12. 특별 요청에 대한 호텔 대응

## 📱 화면 구성

### 1. 시작 화면
- 호텔 로고 표시
- 설문조사 소개 메시지
- '설문 시작하기' 버튼

### 2. 설문 화면
- 질문 텍스트
- 5개 응답 옵션 (1~5점)
- 이전/다음 버튼 네비게이션

### 3. 결과 화면
- 설문 결과 제목
- 모든 응답에 대한 막대 그래프 시각화

## 📥 설치 방법

1. 이 저장소를 클론하세요:
   ```
   git clone https://github.com/yourusername/hotel-survey-app.git
   ```

2. Android Studio에서 프로젝트를 엽니다.

3. 필요한 종속성을 설치합니다.
   ```
   dependencies {
       // ...
       implementation 'com.github.PhilJay:MPAndroidChart:v3.1.0'
   }
   ```

4. 프로젝트를 빌드하고 기기 또는 에뮬레이터에서 실행합니다.

## 🔍 사용 방법

1. 앱을 실행하고 시작 화면에서 '설문 시작하기' 버튼을 탭합니다.
2. 각 질문에 대해 1~5점 척도로 응답합니다.
3. '다음' 버튼을 눌러 다음 질문으로 이동하거나 '이전' 버튼을 눌러 이전 질문으로 돌아갑니다.
4. 모든 질문에 답변 후 결과 화면에서 응답 분석을 확인합니다.

## 🧩 프로젝트 구조

```
app/
├── src/
│   ├── main/
│   │   ├── java/com/example/hotelsurvey/
│   │   │   ├── MainActivity.java         # 시작 화면
│   │   │   ├── MainActivity2.java        # 설문 화면
│   │   │   └── MainActivity3.java        # 결과 화면
│   │   └── res/
│   │       ├── layout/
│   │       │   ├── activity_main.xml     # 시작 화면 레이아웃
│   │       │   ├── activity_main2.xml    # 설문 화면 레이아웃
│   │       │   ├── activity_main3.xml    # 결과 화면 레이아웃
│   │       │   └── item_survey_result.xml # 결과 항목 레이아웃
│   │       └── ...
└── ...
```

## 🚀 향후 개발 계획

- 설문 결과 서버 전송 기능
- 관리자 대시보드 개발
- 커스텀 테마 지원
- 다국어 지원 확대

## 📝 라이선스

이 프로젝트는 MIT 라이선스 하에 배포됩니다. 자세한 내용은 [LICENSE](LICENSE) 파일을 참조하세요.

## 👥 기여하기

1. 이 저장소를 포크합니다.
2. 새 브랜치를 생성합니다 (`git checkout -b feature/amazing-feature`)
3. 변경 사항을 커밋합니다 (`git commit -m 'Add some amazing feature'`)
4. 브랜치에 푸시합니다 (`git push origin feature/amazing-feature`)
5. Pull Request를 생성합니다.

---
© 2025 호텔 설문조사 앱. 모든 권리 보유.
