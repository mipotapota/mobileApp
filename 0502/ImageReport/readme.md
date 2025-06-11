# ViewPager Example App

본 프로젝트는 Android `ViewPager`를 사용하여 여러 이미지와 이미지에 대한 설명 텍스트를 좌우로 넘기며 볼 수 있는 간단한 앱 예제입니다.

---

## 주요 기능

- `ViewPager`를 이용한 이미지 슬라이드 구현
- 각 이미지에 대응하는 설명 텍스트 표시
- 커스텀 `PagerAdapter`로 이미지와 텍스트 동적 바인딩

---

## 구성 파일

- **MainActivity.java**  
  `ViewPager`와 어댑터를 연결하여 화면에 이미지와 텍스트를 출력합니다.

- **MyPagerAdapter.java**  
  `PagerAdapter`를 상속받아 이미지와 텍스트를 각 페이지에 바인딩하는 커스텀 어댑터입니다.

- **activity_main.xml**  
  `ViewPager`가 포함된 레이아웃 파일입니다.

- **item.xml**  
  각 페이지에 표시되는 이미지와 텍스트 레이아웃입니다.

---

## 실행 환경

- Android Studio
- 최소 SDK 버전 : 16 이상 권장
- androidx.viewpager.widget.ViewPager 사용

---

## 사용 방법

1. 프로젝트를 Android Studio로 열기
2. `res/drawable` 폴더에 슬라이드에 사용할 이미지(`s1.png`, `s2.png`, `s.png`) 추가
3. 앱 빌드 및 실행
4. 좌우로 화면을 스와이프하여 이미지와 설명 확인

---

## 참고

- 이미지 및 텍스트 배열은 `MainActivity`에서 직접 수정 가능합니다.
- 필요한 경우 아이템 레이아웃(`item.xml`)을 수정하여 디자인 변경 가능

---

## 라이선스

본 프로젝트는 MIT 라이선스를 따릅니다.

---

Made with ❤️ by [YourName]
