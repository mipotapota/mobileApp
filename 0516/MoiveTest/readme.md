# MovieTest Android App

영화 정보를 관리할 수 있는 간단한 Android 애플리케이션입니다.

## 📱 주요 기능

- **영화 목록 조회**: 저장된 모든 영화를 리스트로 확인
- **영화 추가**: 새로운 영화 정보 등록
- **영화 상세 조회**: 개별 영화의 자세한 정보 확인
- **영화 수정**: 기존 영화 정보 편집
- **영화 삭제**: 불필요한 영화 정보 삭제

## 🎬 영화 정보 항목

각 영화는 다음과 같은 정보로 구성됩니다:
- 제목 (Title)
- 연도 (Year)
- 감독 (Director)
- 평점 (Rating)
- 국가 (Country)

## 🏗️ 앱 구조

### 주요 클래스
- **MainActivity**: 영화 목록을 표시하는 메인 화면
- **AddEditMovieActivity**: 영화 추가/수정 화면
- **MovieDetailActivity**: 영화 상세 정보 화면
- **Movie**: 영화 데이터 모델 클래스
- **DatabaseHelper**: SQLite 데이터베이스 관리 클래스

### 화면 구성
- **메인 화면** (`activity_main.xml`): 영화 목록 ListView와 추가 버튼
- **추가/수정 화면** (`activity_add_edit_movie.xml`): 영화 정보 입력 폼
- **상세 화면** (`activity_movie_detail.xml`): 영화 정보 표시 및 수정/삭제 버튼

## 💾 데이터 저장

SQLite 데이터베이스를 사용하여 영화 정보를 로컬에 저장합니다.

### 데이터베이스 스키마
```sql
CREATE TABLE movies (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    title TEXT,
    year INTEGER,
    director TEXT,
    rating REAL,
    country TEXT
);
```

## 🚀 사용 방법

1. **영화 추가**
   - 메인 화면에서 "영화 추가" 버튼 클릭
   - 모든 필드를 입력하고 "저장" 버튼 클릭

2. **영화 조회**
   - 메인 화면의 목록에서 영화 제목을 클릭하여 상세 정보 확인

3. **영화 수정**
   - 상세 화면에서 "수정" 버튼 클릭
   - 정보를 수정하고 "저장" 버튼 클릭

4. **영화 삭제**
   - 상세 화면에서 "삭제" 버튼 클릭
   - 확인 다이얼로그에서 "삭제" 선택

## 📋 요구사항

- Android API Level: 최소 지원 버전에 맞게 설정 필요
- 권한: 특별한 권한 없이 동작

## 🛠️ 기술 스택

- **언어**: Java
- **데이터베이스**: SQLite
- **UI**: Android XML Layout
- **아키텍처**: 기본 Android Activity 구조

## 📂 파일 구조

```
src/main/java/com/example/movietest/
├── MainActivity.java
├── AddEditMovieActivity.java
├── MovieDetailActivity.java
├── Movie.java
└── DatabaseHelper.java

src/main/res/layout/
├── activity_main.xml
├── activity_add_edit_movie.xml
└── activity_movie_detail.xml
```

## 🎨 UI 특징

- 깔끔한 Material Design 색상 사용 (#4CAF50 메인 컬러)
- 직관적인 버튼 배치와 색상 구분
- 사용자 친화적인 한국어 인터페이스

## 📝 개발 노트

- 데이터 검증: 모든 필드 입력 필수
- 오류 처리: NumberFormatException 등 예외 상황 처리
- 사용자 피드백: Toast 메시지로 작업 결과 알림
- 데이터 새로고침: onResume()에서 자동 목록 갱신

## 🔄 향후 개선 사항

- 영화 검색 기능
- 카테고리별 분류
- 이미지 추가 기능
- 백업/복원 기능
- 더 나은 UI/UX 디자인


##### ![스크린샷 2025-05-28 215357](https://github.com/user-attachments/assets/7e143ed8-f3eb-4d0b-8bed-41634519831f)
