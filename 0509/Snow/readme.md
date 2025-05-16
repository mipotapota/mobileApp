# ❄️ Snowfall View Android App

눈 내리는 애니메이션을 보여주는 Android 애플리케이션입니다. 사용자가 눈송이의 **개수**와 **크기**를 직접 조절할 수 있습니다.

## 📱 화면 미리보기
## ![스크린샷 2025-05-16 131806](https://github.com/user-attachments/assets/c12fc970-23b2-4fd8-af37-c2cc5b64efc1) ![스크린샷 2025-05-16 131727](https://github.com/user-attachments/assets/2375c880-738c-4b4e-bb1c-35a6f7d8b5bc) ![스크린샷 2025-05-16 131739](https://github.com/user-attachments/assets/4481e6b3-b039-40d0-af6a-ab2c1dbba535) ![스크린샷 2025-05-16 131752](https://github.com/user-attachments/assets/f53e58ef-2375-4e89-b137-8c0be7a5f0d0)
> 눈송이가 배경에 떨어지고, 하단 SeekBar를 통해 눈의 개수와 크기를 실시간으로 조절할 수 있습니다.

## 🔧 기능

- 눈송이 애니메이션 효과(`SnowfallView`)
- 눈송이 **개수** 조절 기능 (`SeekBar`)
- 눈송이 **크기** 조절 기능 (`SeekBar`)
- 실시간 UI 반영

## 🧩 구성 파일

### `MainActivity.java`

- UI 요소들과 상호작용
- SeekBar 변경 이벤트를 통해 눈송이의 개수 및 크기를 실시간으로 변경

### `SnowfallView.java`

- `View`를 상속하여 눈 내리는 애니메이션 구현
- 눈송이의 위치, 속도, 크기 등을 내부적으로 계산하여 그리기

### `activity_main.xml`

- 전체 레이아웃 구성
- `SnowfallView`와 함께 두 개의 `SeekBar`를 포함하는 `LinearLayout` 포함

```xml
<com.example.snow.SnowfallView
    android:id="@+id/snowfallView"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```

## ▶️ 실행 방법

1. Android Studio에서 프로젝트 열기
2. `MainActivity` 실행
3. 화면 하단의 슬라이더를 통해 눈의 개수/크기를 조절해보세요!

## 📦 의존성

- 최소 SDK: `API 21 (Lollipop)`
- 별도의 외부 라이브러리 없음 (100% 순수 Android)

## 📜 라이선스

이 프로젝트는 MIT 라이선스를 따릅니다. 자유롭게 사용하고 수정하세요.
