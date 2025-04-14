# 할 일 목록 앱

## ![스크린샷 2025-04-13 144639](https://github.com/user-attachments/assets/61c781aa-37bc-4316-a541-3d1d11a82cbc)![스크린샷 2025-04-13 144649](https://github.com/user-attachments/assets/3afc5cb3-7d08-43cf-87ce-71d564007034)![스크린샷 2025-04-13 144657](https://github.com/user-attachments/assets/a2af231f-9f48-4aed-88f5-f56c649d6556)![스크린샷 2025-04-13 144705](https://github.com/user-attachments/assets/034fb440-34c6-45d7-9d78-1b7f069ceb68)
# Schedule Android Application Readme

## 프로젝트 개요
Schedule은 간단한 할 일 관리(Todo List) 안드로이드 애플리케이션으로, 사용자가 할 일을 추가하고 완료 여부를 체크할 수 있는 기능을 제공합니다.

## 주요 기능
- 텍스트 입력을 통한 할 일 추가
- 체크박스를 통한 할 일 완료 상태 표시
- 완료된 할 일은 "완료: [할 일]" 형식으로 텍스트 변경

## 기술 스택
- Java
- Android SDK
- XML 레이아웃

## 프로젝트 구조
```
app/
├── src/main/
│   ├── java/com/example/schedule/
│   │   └── MainActivity.java
│   └── res/
│       └── layout/
│           └── activity_main.xml
```

## 화면 구성
- **메인 화면 (activity_main.xml)**
  - 상단 입력 필드(EditText): 할 일 입력
  - "추가" 버튼: 입력된 할 일을 목록에 추가
  - 할 일 목록 영역: 추가된 할 일들이 체크박스 형태로 표시

## 기능 설명
1. 사용자가 상단 입력 필드에 할 일을 입력합니다.
2. "추가" 버튼을 클릭하면 입력된 할 일이 체크박스 형태로 목록에 추가됩니다.
3. 할 일을 완료했을 때 해당 체크박스를 체크하면 텍스트가 "완료: [할 일]" 형식으로 변경됩니다.
4. 체크를 해제하면 원래 할 일 텍스트로 복원됩니다.

## 코드 설명
- `MainActivity.java`: 
  - `addTask()` 메소드를 통해 동적으로 체크박스 생성
  - 체크박스 상태 변경에 따른 텍스트 업데이트 로직
- `activity_main.xml`: 
  - 입력 필드, 버튼, 동적으로 할 일이 추가될 LinearLayout 정의

## 설치 및 실행 방법
1. 이 저장소를 클론합니다:
   ```
   git clone https://github.com/yourusername/schedule.git
   ```
2. Android Studio에서 프로젝트를 엽니다.
3. 빌드하고 실행합니다.

## 요구사항
- Android SDK 최소 버전: 프로젝트 설정에 따름
- Android Studio

## 향후 개선사항
- 할 일 삭제 기능 추가
- 할 일 데이터 영구 저장(SharedPreferences 또는 SQLite)
- 할 일 우선순위 설정 기능
- 날짜별 분류 기능

## 라이센스
(라이센스 정보 필요 시 추가)
