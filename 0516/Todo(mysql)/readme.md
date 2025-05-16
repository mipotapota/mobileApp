# 📋 MyTodoApp - Java + MySQL 기반 To-Do 리스트

**MyTodoApp**은 Android Studio(Java)를 이용해 제작한 디자인 중심의 할 일(To-Do) 앱입니다.  
할 일을 입력하고, 날짜를 선택해 저장하며, MySQL 데이터베이스와 연동하여 항목을 목록에 표시합니다.

![screenshot](screenshots/main_screen.png) <!-- 직접 캡처한 스크린샷 경로로 교체하세요 -->

---

## ✨ 주요 기능

- 할 일 제목과 날짜 입력
- 달력 선택(DatePicker)으로 날짜 지정
- FloatingActionButton을 통한 입력 화면 이동
- MySQL 데이터베이스에 직접 저장
- 목록(ListView) 갱신 및 자동 새로고침

---

## ⚙️ 사용 기술

| 항목               | 내용                                |
|------------------|-------------------------------------|
| 언어               | Java (Android SDK)                 |
| 데이터베이스         | MySQL (JDBC 연동)                  |
| JDBC 드라이버        | `mysql-connector-java-5.1.49.jar` |
| 디자인 요소          | Material Design + ListView + FAB |
| 의존성 관리         | 수동 `.jar` import 및 Gradle 설정 |

---

## 📁 프로젝트 구조

MyTodoApp/
├── app/
│ ├── src/
│ │ └── main/
│ │ ├── java/com/example/mytodoapp/
│ │ │ ├── MainActivity.java
│ │ │ ├── AddTaskActivity.java
│ │ │ ├── DBHelper.java
│ │ │ ├── TaskAdapter.java
│ │ │ └── Task.java
│ │ └── res/layout/
│ │ ├── activity_main.xml
│ │ ├── activity_add_task.xml
│ │ └── item_task.xml
│ └── libs/
│ └── mysql-connector-java-5.1.49.jar
