# 여론 조사를 할 수 있는 앱.

# OpinionPoll Android Application Readme

## 프로젝트 개요
OpinionPoll은 사용자가 현재 사용 중인 안드로이드 버전을 선택하고 해당 버전에 맞는 이미지를 표시하는 간단한 안드로이드 애플리케이션입니다.

## 주요 기능
- 라디오 버튼을 통한 안드로이드 버전 선택
- 버튼 클릭 시 선택한 버전에 해당하는 이미지 표시

## 기술 스택
- Java
- Android SDK
- XML 레이아웃

## 프로젝트 구조
```
app/
├── src/main/
│   ├── java/com/example/opinionpoll/
│   │   └── MainActivity.java
│   ├── res/
│   │   ├── layout/
│   │   │   └── activity_main.xml
│   │   └── drawable/
│   │       ├── image0.png
│   │       ├── image1.png
│   │       └── image2.png
```

## 화면 구성
- **메인 화면 (activity_main.xml)**
  - 상단 텍스트: "현재 사용중인 안드로이드 버전은?"
  - 라디오 버튼 그룹:
    - 2.3.3
    - 4.1
    - 4.4
  - "DISPLAY IMAGE" 버튼
  - 이미지 표시 영역

## 기능 설명
1. 사용자가 라디오 버튼으로 안드로이드 버전을 선택합니다.
2. "DISPLAY IMAGE" 버튼을 클릭합니다.
3. 선택한 버전에 해당하는 이미지가 화면에 표시됩니다:
   - 2.3.3 선택 시: image0.png 표시
   - 4.1 선택 시: image1.png 표시
   - 4.4 선택 시: image2.png 표시

## 설치 및 실행 방법
1. 이 저장소를 클론합니다:
   ```
   git clone https://github.com/yourusername/opinionpoll.git
   ```
2. Android Studio에서 프로젝트를 엽니다.
3. 빌드하고 실행합니다.

## 코드 설명
- `MainActivity.java`: 라디오 버튼 선택 상태를 감지하고 해당 이미지 리소스를 ImageView에 설정하는 로직 포함
- `activity_main.xml`: 사용자 인터페이스 레이아웃 정의

## 요구사항
- Android SDK 최소 버전: 프로젝트 설정에 따름
- Android Studio

## ![스크린샷 2025-04-13 142938](https://github.com/user-attachments/assets/f0d81efa-f884-4cb0-9a76-c6aa7f260b22)![스크린샷 2025-04-13 142948](https://github.com/user-attachments/assets/b6a13382-814e-488f-95f6-e3cc66565908)![스크린샷 2025-04-13 142955](https://github.com/user-attachments/assets/196f2513-2633-42cd-96aa-c5ac0586fe3d)![스크린샷 2025-04-13 143006](https://github.com/user-attachments/assets/3d91a9db-371d-442a-bf64-77276a8f1c0e)

## 라이센스
(라이센스 정보 필요 시 추가)
