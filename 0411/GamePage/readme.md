### 게임을 시작하는 시작 페이지를 작성.
#### 메인페이지는 하나의 이미지와 3개의 버튼으로 구성.
#### 각각의 버튼을 누르면 해당되는 페이즈로 이동.
#### 이동한 페이지에서 BACK키를 누르면 메인 페이지로 되돌아옴.

## ![스크린샷 2025-04-13 152506](https://github.com/user-attachments/assets/d3554fd2-b9ee-4f33-87c5-a57a4d22e1fb)![스크린샷 2025-04-13 153107](https://github.com/user-attachments/assets/e9a07e57-1ee5-4c6d-9949-21644d4aaa76)![스크린샷 2025-04-13 152524](https://github.com/user-attachments/assets/64969c2b-b095-43d5-b12f-6c669faf0d09)![스크린샷 2025-04-13 152532](https://github.com/user-attachments/assets/6ebb10a0-6e05-47dc-a13c-2af9ff7c923a)

# GamePage Android Application Readme

## 프로젝트 개요
GamePage는 간단한 게임 관련 Android 애플리케이션으로, 여러 화면을 통해 게임 소개, 설정, 시작 기능을 제공합니다.

## 주요 기능
- **시작 화면**: 3개의 버튼(소개, 설정, 시작)을 통해 각 기능으로 이동
- **소개 화면**: 게임에 대한 기본 정보 제공
- **설정 화면**: 난이도 및 플레이어 수 설정
- **게임 화면**: 실제 게임이 시작되는 화면

## 기술 스택
- Java
- Android SDK
- XML 레이아웃

## 프로젝트 구조
```
app/
├── src/main/
│   ├── java/com/example/gamepage/
│   │   ├── MainActivity.java
│   │   ├── MainActivity2.java
│   │   ├── MainActivity3.java
│   │   └── MainActivity4.java
│   ├── res/
│   │   ├── layout/
│   │   │   ├── activity_main.xml
│   │   │   ├── activity_main2.xml
│   │   │   ├── activity_main3.xml
│   │   │   └── activity_main4.xml
│   │   └── drawable/
│   │       ├── start.png
│   │       ├── img.png
│   │       └── mario.png
```

## 화면 설명
1. **메인 화면 (activity_main.xml)**
   - 배경 이미지: `start.png`
   - INTRODUCTION, SETTING, START 버튼 제공

2. **소개 화면 (activity_main2.xml)**
   - 배경 이미지: `img.png`
   - 게임에 대한 소개 텍스트 표시

3. **설정 화면 (activity_main3.xml)**
   - 난이도(Difficulties) 및 플레이어 수(Players) 조절 슬라이더 제공

4. **게임 화면 (activity_main4.xml)**
   - 배경 이미지: `mario.png`
   - 게임 실행 화면

## 설치 및 실행 방법
1. 이 저장소를 클론합니다:
   ```
   git clone https://github.com/yourusername/gamepage.git
   ```
2. Android Studio에서 프로젝트를 엽니다.
3. 빌드하고 실행합니다.

## 개발 참고 사항
- `EdgeToEdge.enable(this)` 사용하여 모던한 UI 경험 제공
- 각 화면 간 전환은 Intent를 통해 구현
- `SeekBar`를 사용하여 설정 값 조절

## 향후 개선 사항
- 실제 게임 기능 구현
- 사용자 설정 값 저장 및 적용
- 더 다양한 게임 옵션 추가

## 추가 정보
activity_main2.xml에 언급된 것처럼 일부 이미지 리소스는 외부에서 다운로드되었습니다.
